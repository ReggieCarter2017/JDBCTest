package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class HibernateHandler {
    public static void searchByName(SessionFactory sessionFactory, String name) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from Book where name= :name");
            query.setParameter("name", name);
            List list = query.list();
            list.forEach(System.out::println);
        }
    }

    public static void updateBook(SessionFactory sessionFactory, int id, String name, String author) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Book book = session.get(Book.class, id);
            book.setName(name);
            book.setAuthor(author);
            session.merge(book);
            session.getTransaction().commit();
        }
    }

    public static void generateBooks(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            List<Book> books = LongStream.rangeClosed(1, 10).mapToObj(it -> new Book(it))
                    .peek(it -> it.setName("Book #" + it.getId()))
                    .peek(it -> it.setAuthor("Author #" + it.getId()))
                    .peek(it -> session.save(it)).collect(Collectors.toList());
            session.getTransaction().commit();
        }

    }
}
