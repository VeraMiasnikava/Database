package database;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

import lombok.extern.log4j.Log4j;

@Log4j
class Connector implements AutoCloseable {

    private Properties p = new Properties();
    private Connection connection;
    private Statement statement;
    private ResultSet result;

    boolean registerDriver() {
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
        String password;
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

    void inputQuery() {
        String query;
        Scanner in = new Scanner(System.in);
        System.out.println("Введите запрос, в конце должна стоять ;");
        StringBuilder temp = new StringBuilder();
        while (in.hasNext()) {
            temp.append(in.nextLine());
            if (temp.length() > 0 && temp.charAt(temp.length() - 1) == ';') break;
        }
        query = temp.toString();
        if (query.length() > 6) {
            String subquery = query.substring(0, 6);
            if (subquery.equalsIgnoreCase("select")) {
                this.createSelectQuery(query);
            } else {
                this.createUpdateQuery(query);
            }
        } else {
            log.error("Ошибка синтаксиса в sql-запросе");
        }
    }

    private void createSelectQuery(String query) {
        try {
            statement = connection.createStatement();
            //String query = "select id, name, surname from students";
            result = statement.executeQuery(query);
            int columns = result.getMetaData().getColumnCount();
            for (int i = 1; i <= columns; i++) {
                System.out.print(result.getMetaData().getColumnLabel(i) + "\t|\t");
            }
            System.out.println();
            System.out.println("-------------------------------------------");
            while (result.next()) {
                for (int i = 1; i <= columns; i++) {
                    System.out.print(result.getString(i) + "\t|\t");
                }
                System.out.println();
            }
        } catch (SQLSyntaxErrorException e) {
            log.error("Ошибка синтаксиса в sql-запросе:");
            log.error(e);
        } catch (SQLException e) {
            log.error("Ошибка при работе с бд:");
            log.error(e);
        }
    }

    private void createUpdateQuery(String query) {
        try {
            /* String query = "INSERT INTO college.students (id, name, surname)VALUES (9, 'Ira', 'Vatnay');";*/
            statement = connection.createStatement();
            System.out.println("Количество измененных строк=" + statement.executeUpdate(query));
        } catch (SQLSyntaxErrorException e) {
            log.error("Ошибка синтаксиса в sql-запросе:");
            log.error(e);
        } catch (SQLException e) {
            log.error("Ошибка при работе с бд:");
            log.error(e);
        }
    }

    @Override
    public void close() throws Exception {

        if (result != null) {
            result.close();
            System.out.println("result close");
        }
        if (statement != null) {
            statement.close();
            System.out.println("statement close");
        }
        if (connection != null) {
            connection.close();
            System.out.println("connection close");
        }
    }

}
