package database;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;
import lombok.extern.log4j.Log4j;

@Log4j
public class Connector {

    private Properties p = new Properties();
    private Connection connection;
    private Statement statement;
    private ResultSet result;

    boolean testDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            log.info("Драйвер для работы с бд успешно загружен");
            return true;
        } catch (Exception e) {
            log.error("Не загружен драйвер mysql");
            return false;
        }
    }

    boolean createConnection() {
        String url;
        String port;
        String databaseName;
        String username;
        String password ;
        Scanner in = new Scanner(System.in);
        try {
            System.out.println("Введите логин");
            username = in.nextLine();
           System.out.println("Введите пароль");
            password = in.nextLine();
            System.out.println("Введите порт");
            port = in.nextLine();
            System.out.println("Введите url-адрес");
            url = in.nextLine();
            System.out.println("Введите имя бд");
            databaseName = in.nextLine();
            if (username.isEmpty() || port.isEmpty() || url.isEmpty() || databaseName.isEmpty()) {
                throw new EmptyInputException("введено пустое поле");
            }
            // "jdbc:mysql://localhost:3306/college";
            String fullUrl = "jdbc:mysql://" + url + ":" + port + "/" + databaseName;
            System.out.println("full url=" + fullUrl);
            p.setProperty("user", username);
            p.setProperty("password", password);
            p.setProperty("useUnicode", "true");
            p.setProperty("characterEncoding", "utf-8");
            p.setProperty("serverTimezone", "Europe/Minsk");
            try {
                connection = DriverManager.getConnection(fullUrl, p);
                log.info("Соединение с бд успешно установлено");
                return true;
            } catch (SQLException e) {
                log.error("Соединение с бд не установлено");
                return false;
            }
        } catch (EmptyInputException e) {
            log.error(e.getMessage());
            log.error("Соединение с бд не установлено");
            return false;
        }
    }

    void createQuery() {
        try {
            statement = connection.createStatement();
            //String query = "select id, name, surname from students";
            String query;
            Scanner in = new Scanner(System.in);
            System.out.println("Введите запрос на выборку");
            query = in.nextLine();
            if (query.isEmpty()) {
                throw new EmptyInputException("введен пустой запрос");
            }
            result = statement.executeQuery(query);
        } catch (EmptyInputException e) {
            log.error(e.getMessage());
        } catch (SQLSyntaxErrorException e) {
            log.error("Ошибка синтаксиса в sql-запросе:");
            log.error(e);
        } catch (SQLException e) {
            log.error("Ошибка при работе с бд:");
            log.error(e);
        }
    }

    void createUpdate() {
        try {
           /* String query = "INSERT INTO college.students (id, name, surname)VALUES (7, 'Igor', 'Prinov');";*/
            statement = connection.createStatement();
            String query;
            Scanner in = new Scanner(System.in);
            System.out.println("Введите запрос на изменение");
            query = in.nextLine();
            if (query.isEmpty()) {
                throw new EmptyInputException("введен пустой запрос");
            }
            statement.executeUpdate(query);
        } catch (EmptyInputException e) {
            log.error(e.getMessage());
        }catch (SQLSyntaxErrorException e) {
            log.error("Ошибка синтаксиса в sql-запросе:");
            log.error(e);
        } catch (SQLException e) {
            log.error("Ошибка при работе с бд:");
            log.error(e);
        }
    }

    void closeConnector() {
        try {
            if (result != null) {
                result.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            log.error("Ошибка при закрытии соединения:");
            log.error(e);
        }
    }


}
