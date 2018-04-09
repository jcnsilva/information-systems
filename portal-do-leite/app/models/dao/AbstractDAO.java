package models.dao;

import java.util.List;

import javax.persistence.Query;

public interface AbstractDAO<T>{
	
	public void flush();
	
	public void commitAndContinue();
	
	public void commitAndFinalize();
	
	public void refresh(T obj);
	
	public void merge(T obj);
	
	public void persist(T obj);
	
	public void remove(T obj);
	
	public <T> void removeElementById(Class<T> clazz, Long id);
	
	public <T> T getElementById(Class<T> clazz, Long id);
	
	public <T> List<T> getAllByClass(Class<T> clazz);
	
	public <T> List<T> getByAttributeName(Class<T> clazz, String attributeName, String attributeValue);
	
	public Query createQuery(String query);

}
