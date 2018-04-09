package models.dao;

import java.util.List;

import javax.persistence.Query;

import play.db.jpa.JPA;

public class ApplicationDAO implements AbstractDAO<Object>{

	@Override
	public void flush() {
		JPA.em().flush();		
	}

	@Override
	public void commitAndContinue() {
		JPA.em().getTransaction().commit();
		JPA.em().getTransaction().begin();
	}

	@Override
	public void commitAndFinalize() {
		JPA.em().getTransaction().commit();		
	}

	@Override
	public void refresh(Object obj) {
		JPA.em().refresh(obj);
		
	}

	@Override
	public void merge(Object obj) {
		JPA.em().merge(obj);
		
	}

	@Override
	public void persist(Object obj) {
		JPA.em().persist(obj);
		
	}

	@Override
	public void remove(Object obj) {
		JPA.em().remove(obj);
		
	}

	@Override
	public <T> void removeElementById(Class<T> clazz, Long id) {
		JPA.em().remove(getElementById(clazz, id));		
	}

	@Override
	public <T> T getElementById(Class<T> clazz, Long id) {
		return JPA.em().find(clazz, id);
	}

	@Override
	public <T> List<T> getAllByClass(Class<T> clazz) {
		String hql = "FROM " + clazz.getSimpleName(); 
		Query query = createQuery(hql);
		return query.getResultList();
	}

	@Override
	public <T> List<T> getByAttributeName(Class<T> clazz, String attributeName,
			String attributeValue) {
		String hql = "FROM " + clazz.getSimpleName() + " c" + " WHERE c." + attributeName
				+ " = '" + attributeValue + "'";
		Query hqlQuery = JPA.em().createQuery(hql);
		return hqlQuery.getResultList();
	}

	@Override
	public Query createQuery(String query) {
		return JPA.em().createQuery(query);
	}

}
