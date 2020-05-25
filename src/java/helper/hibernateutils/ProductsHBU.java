/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper.hibernateutils;

import static helper.hibernateutils.UsersHBU.getSessionFactory;
import java.util.Iterator;
import java.util.List;
import models.Products;
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
public class ProductsHBU {

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
    
    public static void insert(String name, int price, String description){
        Session sess = getSessionFactory().openSession();
        
        Transaction trn = sess.beginTransaction();
        
        
        try{
            sess.save(new Products(name, price, description));
            trn.commit();
        }catch(Exception e){
            trn.rollback();
        }
        
        if(sess.isOpen()) sess.close();
    }
    
    public static void update(int id,String name, int price, String description){
        Session sess = getSessionFactory().openSession();
        
        Transaction trn = sess.beginTransaction();
        
        
        try{
            Products temp = getProductById(id);
            temp.setName(name);
            temp.setPrice(price);
            temp.setDescription(description);
            
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
            Products temp = getProductById(id);
            sess.delete(temp);
            trn.commit();
        }catch(Exception e){
            trn.rollback();
        }
        
        if(sess.isOpen()) sess.close();
    }
    
    public static Products getProductById(int id){
        Products prod = null;
        Session sess = getSessionFactory().openSession();
        

        try{
            Query q = sess.createQuery("from Products u WHERE u.id = :id");
            q.setParameter("id", id);
            
            Iterator i = q.iterate();
            
            if(i.hasNext()){
                prod =(Products) i.next();
            }
        } catch(Exception e){
            prod = null;
        }
        
        if(sess.isOpen()) sess.close();
        
        return prod;
    }
    
    public static List getAllProduct(){
        List products = null;
        Session sess = getSessionFactory().openSession();
        

        try{
            Query q = sess.createQuery("from Products");
            
            products = q.list();
            
        } catch(Exception e){
            products = null;
        }
        
        if(sess.isOpen()) sess.close();
        
        return products;
    }
}
