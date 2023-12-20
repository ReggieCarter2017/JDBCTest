package org.jdbcproj;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = Connector.connect();
        dropTable(connection);
        createTable(connection);
        addBookToTable(connection, "War and Peace", "Leo Tolstoy");
        addBookToTable(connection, "Pygmalion", "Bernard Shaw");
        addBookToTable(connection, "The Bee Sting", "Paul Murray");
        addBookToTable(connection, "Birnam Wood", "Eleanor Catton");
        addBookToTable(connection, "The Country of the Blind", "Andrew Leland");
        addBookToTable(connection, "Cherry orchard", "Anton Chekhov");
        addBookToTable(connection, "Fear Is Just a Word", "Azam Ahmed");
        addBookToTable(connection, "Fire Weather", "John Vaillant");
        addBookToTable(connection, "Kitchen", "Banana Yoshimoto");
        addBookToTable(connection, "The idiot", "Fyodor Dostoevsky");

        searchByName("Kitchen", connection);
        searchByName("The idiot", connection);
        searchByName("Pygmalion", connection);
        searchByName("War and Peace", connection);

        connection.close();
    }

    private static void searchByName(String name, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from book where name = ?")) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                name = resultSet.getString("name");
                String author = resultSet.getString("author");
                System.out.println("id = " + id + ", name = " + name + ", author = " + author);
            }
        } catch (SQLException e) {
            System.out.println("Couldn't find this author: " + e.getMessage());
        }
    }


    public static void createTable(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    create table if not exists book (
                    id bigint auto_increment primary key,
                    name varchar(30),
                    author varchar(30)
                    )                                      
                    """);
        } catch (SQLException e) {
            System.out.println("Couldn't create this table: " + e.getMessage());
        }
    }

    public static void addBookToTable(Connection connection, String name, String author) {
        try (PreparedStatement statement = connection.prepareStatement("""
                insert into book (name, author)
                values (?, ?)
                """)) {
            statement.setString(1, name);
            statement.setString(2, author);
            statement.execute();

        } catch (SQLException e) {
            System.out.println("Couldn't add the book to the table: " + e.getMessage());
        }
    }

    private static void executeSelect(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select id, name from book");
            int counter = 0;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                System.out.println("[" + counter++ + "] id = " + id + ", name = " + name);
            }
        }
    }

    private static void insertData(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int updatedRows = statement.executeUpdate("""
        insert into users(id, name) 
        values(1, 'Igor'),
              (2, 'User #2'),
              (3, 'User #3'),
              (4, 'User #4')""");
            System.out.println(updatedRows);
        }
    }

    private static void executeUpdate(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int updatedRows = statement.executeUpdate("update book set name = 'unknown' where id > 2");
            System.out.println(updatedRows);
        }
    }

    private static void executeDelete(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int deletedRows = statement.executeUpdate("delete from book where id = 4");
            System.out.println(deletedRows);
        }
    }

    private static void dropTable(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    drop table if exists book;
                    """);
        } catch (SQLException e) {
            System.out.println("Couldn't delete the table: " + e.getMessage());
        }
    }
}
