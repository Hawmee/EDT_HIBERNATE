package com.example.DAO;

import com.example.model.Salles;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class SalleDAO {
    public void saveSalle(Salles salle){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction= session.beginTransaction();
            session.merge(salle);
            transaction.commit();
        }catch (Exception e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Salles> getAllSalles(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("FROM Salles" , Salles.class).list();
        }
    }

    public Salles findSalleById(int codesal){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(Salles.class , codesal);
        }
    }

    public void deletSalle(int codesal){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Salles salle= session.get(Salles.class , codesal);
            if (salle != null){
                session.remove(salle);
            }
            transaction.commit();
        }catch (Exception e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
