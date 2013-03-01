package pandox.egito.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class GenericDAO<T, PK extends Serializable> implements IGenericDAO<T, PK> {

	@Resource(name = "sessionFactory")
	protected SessionFactory sessionFactory;

	public void setSession(SessionFactory session) {
		this.sessionFactory = session;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public Session getSession() {
		return getSessionFactory().openSession();
//		return getSessionFactory().getCurrentSession();
	}

	@Override
	public T findById(Class<T> clazz, PK id) {
		T t = (T) getSession().get(clazz, id);
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(Class<T> clazz) {
		List<T> list = (List<T>) getSession().createQuery(" FROM " + clazz.getCanonicalName()).list();
		return list;
	}

	@Override
	public boolean remove(T entity) {
		getSession().delete(entity);
		getSession().flush();
		return true;
	}

	@Override
	public PK save(T entity) {
		getSession().save(entity);
		getSession().flush();
		return null;

	}

	@Override
	public void update(T entity) {
		getSession().update(entity);
		getSession().flush();
	}

}
