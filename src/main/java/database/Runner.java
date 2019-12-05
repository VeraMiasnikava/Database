package database;

import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {

        Connector connector = new Connector();
        Scanner in = new Scanner(System.in);
        if (connector.testDriver()) {
            if (connector.createConnection()) {
                do {
                    System.out.println("Выберите действие:" +
                            "1-запрос на выборку,  " +
                            "2-запрос на изменение,   " +
                            "quit-выход");
                    String input;

                    input = in.nextLine();
                    if (input.equals("1")) {
                        connector.createQuery();
                    }
                    if (input.equals("2")) {
                        connector.createUpdate();
                    }
                    if (input.equals("quit")) {
                        break;
                    }
                } while (true);
                connector.closeConnector();
                in.close();
                System.out.println("Работа программы завершена");
            }
        }


    }
}