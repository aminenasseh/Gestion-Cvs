package monpkg.services;

import java.util.List;

import javax.annotation.Resource;
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
public class ActivityManager {

	@Resource(name = "myDS")
	private DataSource ds;

	@PersistenceContext(unitName = "myMySQLBase")
	EntityManager em;

	private Person authPerson = new Person();

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
	 * @param Person
	 *            a save a new activities given as a parameter person
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateActivity(Person p) {
		if (p != null) {
			authPerson = p;
			em.merge(authPerson);
			em.flush();
		} else {
			System.out.println("Erreur lors de la modification");
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

	/**
	 * 
	 * @param email
	 * @param password
	 * @return get information concerning the person given as a parameter mail
	 *         and password
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
	 * 
	 * @return a Person disconnected
	 */
	public Person logout() {
		authPerson.setIdPerson(0);
		authPerson.setName(null);
		authPerson.setFirstName(null);
		authPerson.setBirthday(null);
		authPerson.setEmail(null);
		authPerson.setWebSite(null);
		authPerson.setPassword(null);
		return authPerson;
	}

	public Person getAuthPerson() {
		return authPerson;
	}

	public void setAuthPerson(Person authPerson) {
		this.authPerson = authPerson;
	}

}
