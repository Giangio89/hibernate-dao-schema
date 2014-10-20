package org.schema.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory;  

	static {  

		try {
			// Create the SessionFactory from hibernate.cfg.xml  
			sessionFactory = new Configuration()
			.configure()
			.buildSessionFactory(new StandardServiceRegistryBuilder().build());  

		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed  
			System.err.println("Initial SessionFactory creation failed." + ex);  
			throw new ExceptionInInitializerError(ex);  
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;  
	}
	
	public static boolean transactionActive() {
		Session hibernateSession = HibernateUtil.getSession();
		return hibernateSession.getTransaction().isActive();
	}

	public static Session beginTransaction() {
		Session hibernateSession = HibernateUtil.getSession();
		hibernateSession.beginTransaction();
		return hibernateSession;
	}

	public static void commitTransaction() {
		HibernateUtil.getSession().getTransaction().commit();
	}

	public static void rollbackTransaction() {
		HibernateUtil.getSession().getTransaction().rollback();
	}

	public static void closeSession() {
		HibernateUtil.getSession().close();
	}

	public static Session getSession() {
		Session hibernateSession = sessionFactory.getCurrentSession();
		return hibernateSession;
	}
	
	public static Session openSession() {
		return sessionFactory.openSession();
	}

}
