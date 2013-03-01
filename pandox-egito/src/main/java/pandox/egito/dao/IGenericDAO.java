package pandox.egito.dao;

import java.io.Serializable;
import java.util.List;


public interface IGenericDAO<T, PK extends Serializable> {

	PK save(T entity);
	
	void update(T entity);
	
	boolean remove(T entity);
	
	T findById(Class<T> clazz, PK id);
	
	List<T> findAll(Class<T> clazz);
}
