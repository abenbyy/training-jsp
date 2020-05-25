/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper.hibernateutils;

import java.util.Iterator;
import models.Users;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author AE
 */
public class UsersHBU {

    private static final SessionFactory sessionFactory;
    
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static void insert(String email, String password, String name){
        Session sess = getSessionFactory().openSession();
        
        Transaction trn = sess.beginTransaction();
        
        
        try{
            sess.save(new Users(email, password, name));
            trn.commit();
        }catch(Exception e){
            trn.rollback();
        }
        
        if(sess.isOpen()) sess.close();
    }
    
    public static void update(int id,String email, String password, String name){
        Session sess = getSessionFactory().openSession();
        
        Transaction trn = sess.beginTransaction();
        
        
        try{
            Users temp = getUserById(id);
            temp.setEmail(email);
            temp.setPassword(password);
            temp.setName(name);
            
            sess.update(temp);
            trn.commit();
        }catch(Exception e){
            trn.rollback();
        }
        
        if(sess.isOpen()) sess.close();
    }
    
    public static void delete(int id){
         Session sess = getSessionFactory().openSession();
        
        Transaction trn = sess.beginTransaction();
        
        
        try{
            Users temp = getUserById(id);
            sess.delete(temp);
            trn.commit();
        }catch(Exception e){
            trn.rollback();
        }
        
        if(sess.isOpen()) sess.close();
    }
    
    public static Users getUserById(int id){
        Users user = null;
        Session sess = getSessionFactory().openSession();
        

        try{
            Query q = sess.createQuery("from Users u WHERE u.id = :id");
            q.setParameter("id", id);
            
            Iterator i = q.iterate();
            
            if(i.hasNext()){
                user =(Users) i.next();
            }
        } catch(Exception e){
            user = null;
        }
        
        if(sess.isOpen()) sess.close();
        
        return user;
    }
    
    public static Users auth(String email, String password){
        
        Users user = null;
        Session sess = getSessionFactory().openSession();
        

        try{
            Query q = sess.createQuery("from Users u WHERE u.email = :email AND u.password = :password");
            q.setParameter("email", email);
            q.setParameter("password", password);
            Iterator i = q.iterate();
            
            if(i.hasNext()){
                user =(Users) i.next();
            }
        } catch(Exception e){
            user = null;
        }
        
        if(sess.isOpen()) sess.close();
        
        return user;
    }
}
