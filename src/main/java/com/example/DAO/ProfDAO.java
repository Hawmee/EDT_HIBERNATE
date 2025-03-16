package com.example.DAO;

import com.example.model.Profs;
import com.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ProfDAO {
    public void saveProf(Profs profs){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(profs);
            transaction.commit();
        }catch (Exception e){
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Profs> getAllProfs(){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Profs", Profs.class).list();
        }
    }


    public Profs findProfById(int codeprof){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(Profs.class , codeprof);
        }
    }

    public void  deleteProfs(int codeprof){
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Profs profs = session.get(Profs.class, codeprof);
            if (profs != null) {
                session.delete(profs);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Profs> findProf(Object criteria){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = null ;

            if (criteria instanceof Integer) {
                hql = "FROM Profs WHERE codeprof = :value";
            }else{
                hql="FROM Profs WHERE LOWER(nom) LIKE LOWER(:value) OR LOWER(prenoms) LIKE LOWER(:value)";
            }

            Query<Profs> query = session.createQuery(hql, Profs.class);

            // Set the parameter value for nom, prenom, or codeprof
            if (criteria instanceof Integer) {
                query.setParameter("value", criteria);
            } else {
                query.setParameter("value", "%" + criteria.toString() + "%");
            }

            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

}
