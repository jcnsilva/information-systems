package models.DAO;

/**
 * Created by Julio on 22/11/2014.
 */

import java.util.List;

import javax.management.RuntimeErrorException;
import javax.persistence.Query;

import play.db.jpa.JPA;


public class DAO {

    public boolean persist(Object e) {
        JPA.em().persist(e);
        return true;
    }

    public void flush() {
        JPA.em().flush();
    }

    public void merge(Object e) {
        JPA.em().merge(e);
    }
    
    public void refresh(Object e){
    	JPA.em().refresh(e);
    }

    public <T> T findByEntityId(Class<T> clazz, Long id) {
        return JPA.em().find(clazz, id);
    }

    public <T> List<T> findAllByClass(Class clazz) {
        String hql = "FROM " + clazz.getName();
        Query hqlQuery = JPA.em().createQuery(hql);
        return hqlQuery.getResultList();
    }

    public <T> void removeById(Class<T> classe, Long id) {
        JPA.em().remove(findByEntityId(classe, id));
    }

    public void remove(Object objeto) {
        JPA.em().remove(objeto);
    }
    
    public void clear(){
    	JPA.em().clear();
    }
    
    public void commitAndContinue(){
    	JPA.em().getTransaction().commit();
    	JPA.em().getTransaction().begin();
    }
    
    public void commitAndFinish(){
    	JPA.em().getTransaction().commit();
    }

    public <T> List<T> findByAttributeName(String className,
                                           String attributeName, String attributeValue) {
        String hql = "FROM " + className + " c" + " WHERE c." + attributeName
                + " = '" + attributeValue + "'";
        Query hqlQuery = JPA.em().createQuery(hql);
        return hqlQuery.getResultList();
    }
    
    public <T> List<T> findByMultiplesAttributes(String className, List<String> attributesNames, List<String> attributesValues) {
    	if(attributesNames == null || attributesNames.isEmpty() ||
    			attributesValues == null || attributesValues.isEmpty())
    		throw new RuntimeException("Can not create Query. Invalid parameter list");
    	
    	String hql = "FROM " + className + " c" + " WHERE c." +
    			attributesNames.get(0) + " = '" + attributesValues.get(0) + "'";
    	
    	if(attributesNames.size() == attributesValues.size()){
			for(int i = 1; i < attributesValues.size(); i++){
				hql += " AND c." + attributesNames.get(i) + " = '" + attributesValues.get(i) + "'";
			}
    	}
                
    	
    	Query hqlQuery = JPA.em().createQuery(hql);
        return hqlQuery.getResultList();
    }

    private Query createQuery(String query) {
        return JPA.em().createQuery(query);
    }
    
    
}
