package org.schema.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public interface DAO<T> {
	
	public abstract List findAll();
	public abstract List findBySite(int sito);
	public void delete(T entity);

	public Session begin();

	public void commit();

	public List<T> findMany(Query query);

	public T findOne(Query query);

	public T merge(T entity);

	public void rollback();

	public void save(T entity);

}
