package monpkg.services;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import monpkg.entities.Activity;
import monpkg.entities.Person;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProfilManager {

	@Resource(name = "myDS")
	private DataSource ds;
	
	@PersistenceContext(unitName = "myMySQLBase")
	EntityManager em;
	
	/**
	 * 
	 * @return a list of activities
	 */
	public List<Activity> findActivities() {
		Query query = em.createQuery("SELECT a FROM Activity a"); // ???
		return (List<Activity>) query.getResultList();
	}
	
	/**
	 * 
	 * @param activity
	 * @return information concerning the activity given as a parameter
	 */
	public Activity findOneActivity(Activity activity) {
		return em.find(Activity.class, activity.getIdActivity());
	}
	
	/**
	 * 
	 * @param activity, removes the activities given as a parameter
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void deleteActivity(Activity activity) {
		Activity a = em.find(Activity.class, activity.getIdActivity());
		if (a != null) {
			em.remove(a);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void saveActivity(Activity a) {
		if (a.getIdActivity() == null) {
			em.persist(a);
		} else {
			a = em.merge(a);
		}
	}

	// @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void createActivity(Activity activity) {
		if (em.find(Activity.class, activity.getIdActivity()) == null) {
			em.persist(activity);
		} else {
			em.merge(activity);
		}
	}
}


