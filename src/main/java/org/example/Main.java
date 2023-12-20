package org.example;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Main {
    public static void main(String[] args) {
        final SessionFactory sessionFactory = Connection.createSessionFactory();

        HibernateHandler.generateBooks(sessionFactory);
        HibernateHandler.searchByName(sessionFactory, "Book #1");
        HibernateHandler.updateBook(sessionFactory, 2, "Josh", "Paul Allen");
        HibernateHandler.searchByName(sessionFactory, "Book #4");
    }
}