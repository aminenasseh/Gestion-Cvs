package monpkg.services;

import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import monpkg.entities.Activity;
import monpkg.entities.Person;

/**
 * The Class ActivityManager.
 */
@Stateful
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ActivityManager {

	/** 
	 * 
	 */
	@PersistenceContext(unitName = "myMySQLBase")
	EntityManager em;

	/** The auth person. */
	private Person authPerson = new Person();

	/**
	 * Find activities.
	 *
	 * @return a list of activities
	 */
	@SuppressWarnings("unchecked")
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
	 * Find one activity.
	 *
	 * @param title
	 *            the title
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
	 * Delete activity.
	 *
	 * @param title
	 *            removes the activities given as a parameter title
	 */
	public void deleteActivity(String title) {
		Activity activity = findOneActivity(title);
		if (activity != null) {
			em.remove(activity);
		}
	}

	/**
	 * Update activity.
	 *
	 * @param p
	 *            the p
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateActivity(Person person) {
		if (person != null) {
			authPerson = person;
			em.merge(authPerson);
			em.flush();
		} else {
			System.out.println("Erreur lors de la modification");
		}
	}

	/**
	 * Save activity.
	 *
	 * @param a
	 *            the a
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void saveActivity(Activity activity) {
		if (em.find(Activity.class, activity.getIdActivity()) == null) {
			em.persist(activity);
		} else {
			em.merge(activity);
		}
	}

	/**
	 * Login.
	 *
	 * @param email
	 *            the email
	 * @param password
	 *            the password
	 * @return get information concerning the person given as a parameter mail
	 *         and password
	 * @throws NoResultException
	 *             the no result exception
	 */
	public Person login(String email, String password) throws NoResultException {
		Query query = null;
		try {
			query = em.createQuery(
					"SELECT p FROM Person p WHERE p.email='" + email + "' AND p.password='" + password + "'");
		} catch (NoResultException e) {
			return null;
		}
		if (query != null) {
			try {
				authPerson = (Person) query.getSingleResult();
			} catch (Exception e) {
				return null;
			}
			return authPerson;
		}
		return null;
	}

	/**
	 * Gets the auth person.
	 *
	 * @return the auth person
	 */
	public Person getAuthPerson() {
		return authPerson;
	}

	/**
	 * Sets the auth person.
	 *
	 * @param authPerson
	 *            the new auth person
	 */
	public void setAuthPerson(Person authPerson) {
		this.authPerson = authPerson;
	}

}
