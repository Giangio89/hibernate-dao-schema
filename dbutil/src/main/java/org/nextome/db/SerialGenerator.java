package org.nextome.db;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentityGenerator;
import org.nextome.db.beans.DBBeanWithId;

public class SerialGenerator extends IdentityGenerator {
	
	@Override
	public Serializable generate(SessionImplementor session, Object obj) throws HibernateException {
		
		if (obj == null) throw new HibernateException(new NullPointerException());
		DBBeanWithId elem = (DBBeanWithId) obj;

		//id is null it means generate ID
		if (elem.getId() == 0) {
			Serializable id = super.generate(session, obj) ;
			return id;
		//id is not null so using assigned id.
		} else {
			return elem.getId();
		}
	}
}
