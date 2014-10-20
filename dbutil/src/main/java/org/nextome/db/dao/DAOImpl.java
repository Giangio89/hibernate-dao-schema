
package org.nextome.db.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jboss.logging.Logger;
import org.nextome.hibernate.HibernateUtil;

public abstract class DAOImpl<T> implements DAO<T> {

	protected static final Logger log = Logger.getLogger(DAOImpl.class);

	public void delete(T entity) {

		try {
			getSession().delete(entity);

		} catch (HibernateException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Session begin(){

		if(!getSession().isOpen()) {
			log.warn("open session");
			HibernateUtil.openSession();
		}

		if(!HibernateUtil.transactionActive()) {
			return HibernateUtil.beginTransaction();

		} else return HibernateUtil.getSession();
	}

	public void commit(){
		try {
			HibernateUtil.commitTransaction();
			HibernateUtil.closeSession();
			log.info("commit transaction");

		} catch (HibernateException re) {
			log.error("delete failed", re);
			throw re;
		}

	}

	public List<T> findMany(Query query) {
		List<T> t;
		t = (List<T>) query.list();
		return t;
	}

	public T findOne(Query query) {
		T t;
		t = (T) query.uniqueResult();
		return t;
	}

	public T merge(T entity) {
		log.warn("merging instance");

		try {
			T result = (T) getSession().merge(entity);;
			log.warn("merge successful");
			return result;

		} catch (HibernateException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public void rollback(){
		if(HibernateUtil.transactionActive()) {
			HibernateUtil.rollbackTransaction();
		}
		HibernateUtil.closeSession();
	}

	public void save(T entity) {

		try {
			getSession().saveOrUpdate(entity);
			log.info("save successful");

		} catch (HibernateException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	protected List findAll(Class clazz) {
		log.info("finding multiple instances");
		try {
			Query query = getSession().createQuery("from " + clazz.getName());
			List results = query.list();
			log.info("find successful, result size: "
					+ results.size());
			return results;

		} catch (HibernateException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	protected Session getSession() {

		try {
			return HibernateUtil.getSession();

		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}
}
