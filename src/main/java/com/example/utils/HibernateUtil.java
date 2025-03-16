package com.example.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    public static final SessionFactory sessionFactory = buildSessionFactory();

    public static SessionFactory buildSessionFactory(){
        try{
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }catch (Throwable ex){
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
