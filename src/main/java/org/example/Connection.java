package org.example;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Connection {
    public static SessionFactory createSessionFactory() {
        return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }
}

