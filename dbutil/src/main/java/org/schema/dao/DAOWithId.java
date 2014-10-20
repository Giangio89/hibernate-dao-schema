package org.schema.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;

public abstract class DAOWithId<T> extends DAOImpl<T> {
	
	public abstract void delete(int id);
	
	public abstract int findLastID();

	public abstract T findByID(int id);
	
	protected void delete(Class clazz, int id){
		log.debug("deleting instance");
		try {
			T entity = (T) getSession().get(clazz, id);
			getSession().delete(entity);
			log.debug("delete successful");
		} catch (HibernateException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	protected T findByID(Class clazz, int id) {
    	log.debug("getting instance with id: " + id);
		try {
			T instance = (T) getSession().get(clazz, id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (HibernateException re) {
			log.error("delete failed", re);
			throw re;
		}
    }
 
    protected int findLastID(Class clazz){
		
    	Query q = getSession().createQuery("SELECT MAX(id) FROM " + clazz.getName());
		return (Integer) q.uniqueResult();
	}
}
