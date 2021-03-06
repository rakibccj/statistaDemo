/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rakib.dao;

import com.rakib.model.Role;
import com.rakib.model.UserForm;
import com.rakib.util.NewHibernateUtil;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import session.SessionUtils;

/**
 *
 * @author Rakib
 */
public class UserDao {

    public void SaveUser(UserForm user) {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
        } finally {
            session.flush();
            session.close();
        }
    }

    public UserForm loginUser(String email, String password) {
        UserForm user = null;
        Transaction tx = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(UserForm.class);
            criteria.add(Restrictions.eq("email", email));
            criteria.add(Restrictions.eq("password", password));
            List list = criteria.list();
            if ((list != null) && (list.size() > 0)) {
                user = (UserForm) criteria.list().get(0);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.flush();
            session.close();
        }
        return user;
    }

}
