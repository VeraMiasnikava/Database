package database;

import lombok.extern.log4j.Log4j;

import java.util.Scanner;

@Log4j
public class Runner {
    public static void main(String[] args) {


        try (Scanner in = new Scanner(System.in)) {
            try (Connector connector = new Connector()) {
                if (connector.registerDriver()) {
                    if (connector.createConnection()) {
                        do {
                            System.out.println("Выберите действие:" +
                                    "1-ввод запроса,  " +
                                    "quit-выход");
                            String input = in.nextLine();
                            if (input.equals("1")) {
                                connector.inputQuery();
                            }
                            if (input.equals("quit")) {
                                break;
                            }
                        } while (true);
                    }
                }
            } catch (Exception e) {
                log.error("Ошибка закрытия ресурсов:");
                log.error(e);
            }
            System.out.println("Работа программы завершена");
        }
    }

}