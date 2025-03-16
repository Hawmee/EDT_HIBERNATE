package com.example.DAO;

import com.example.model.Occupations;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OccupationDAO {
    public void saveOcc(Occupations occ){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(occ);
            transaction.commit();
        }catch(Exception e){
            if(transaction!=null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Occupations> getAllOcc(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("FROM Occupations " , Occupations.class).list();
        }
    }

    public Occupations findOccById(int occId){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(Occupations.class , occId);
        }
    }

    public void delOcc(int occId){
        Transaction transaction=null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Occupations occ = session.get(Occupations.class , occId);
            if(occ!=null){
                session.remove(occ);
            }
            transaction.commit();
        }catch(Exception e){
            if(transaction!=null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
