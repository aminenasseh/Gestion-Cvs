package monpkg.services;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.ApplicationException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import monpkg.entities.Activity;
import monpkg.entities.Person;

@Stateful
@TransactionManagement(TransactionManagementType.CONTAINER)
@ApplicationException(rollback = true)
public class ActivityManager {

	@Resource(name = "myDS")
	private DataSource ds;

	@PersistenceContext(unitName = "myMySQLBase")
	EntityManager em;

	/**
	 * 
	 * @return a list of activities
	 */
	public List<Activity> findActivities() {
		Query query = null;
		try {
			query = em.createQuery("SELECT a FROM Activity a");
		} catch (NoResultException e) {
			return null;
		}
		if (query != null) {
			return query.getResultList();
		}
		return null;
	}

	/**
	 * 
	 * @param title
	 * @return information concerning the activity given as a parameter title
	 */
	public Activity findOneActivity(String title) {
		Query query = null;
		try {
			query = em.createQuery("SELECT a FROM Activity a WHERE a.title ='" + title + "'");
		} catch (NoResultException e) {
			return null;
		}
		if (query != null) {
			return (Activity) query.getSingleResult();
		}
		return null;
	}

	/**
	 * 
	 * @param title
	 *            removes the activities given as a parameter title
	 */
	public void deleteActivity(String title) {
		Activity a = findOneActivity(title);
		if (a != null) {
			em.remove(a);
		}
	}

	/**
	 * 
	 * @param Activity
	 *            a save a new activities given as a parameter activity
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void saveActivity(Activity a) {
		if (em.find(Activity.class, a.getIdActivity()) == null) {
			em.persist(a);
		} else {
			em.merge(a);
		}
	}

}
