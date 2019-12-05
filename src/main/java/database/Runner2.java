package database;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Properties;

public class Runner2 {
  /*  public static void main(String[] args) {
  String url = "jdbc:mysql://localhost:3306/college";
    //jdbc:mysql://localhost:3306/college?serverTimezone=Europe/Minsk&amp;characterEncoding=cp1251
    String username = "root";
    String password = "";
    Properties p = new Properties();
        p.setProperty("user", username);
        p.setProperty("password", password);
        p.setProperty("useUnicode", "true");
        p.setProperty("characterEncoding", "utf-8");
        p.setProperty("serverTimezone", "Europe/Minsk");
      /*  jdbc:mysql. Это название протокола соединения, за которым следуют хост и
      порт подключения, на которых запущена база данных. В нашем случае это localhost с
      портом по умолчанию 3306 (если вы его не поменяли при установке).
      Следующая часть — test — имя базы данных
       */
  /*
    Statement statement;
    ResultSet result;
        try {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
    } catch (Exception e) {
        System.err.println("Не загружен драйвер mysql");
    }

    //Connection connection = DriverManager.getConnection(url, username, password)
    /*
            try (
    Connection connection = DriverManager.getConnection(url, p)) {
        System.out.println("Соединение с бд успешно установлено");

        statement = connection.createStatement();

        String query = "select id, name, surname from students";
        try {
            result = statement.executeQuery(query);
            while (result.next()) {
                int id = result.getInt(1);
                String name0 = result.getString(2);
                String name=new String (name0.getBytes(), "utf-8");
                String author = result.getString(3);
                System.out.printf("id: %d, name: %s, author: %s %n", id, name, author);

                 while (result.next()) {
                int id = result.getInt(1);
                String name0 = result.getString(2);
                String name = new String(name0.getBytes(), "utf-8");
                String surname = result.getString(3);
                System.out.printf("id: %d, name: %s, surname: %s %n", id, name, surname);
            }
   */

/*
            }
        } catch (SQLSyntaxErrorException e) {
            System.err.println("Ошибка синтаксиса в sql-запросе:");
            System.out.println(e);
        }

        query = "select id, name from disciplines";
        try {
            result = statement.executeQuery(query);
            while (result.next()) {
                int id = result.getInt(1);
                String name = result.getString(2);

                System.out.printf("id: %d, name: %s %n", id, name);

            }
        } catch (SQLSyntaxErrorException e) {
            System.err.println("Ошибка синтаксиса в sql-запросе");
        }
        catch (SQLException e) {
            System.err.println("Ошибка при работе с данными бд:");
            System.out.println(e);
        }
    } catch (Exception e) {
        System.err.println("не удалось установить соединение с базой данных");
        System.out.println(e);
    }




       /* finally {
            if (statement != null) {
                statement.close();
            }
            if(connection!=null){
                connection.close();
            }*/


}
