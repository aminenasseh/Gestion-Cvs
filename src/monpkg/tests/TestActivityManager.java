package monpkg.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

import org.junit.Before;
import org.junit.Test;

import monpkg.entities.Activity;
import monpkg.entities.Nature;
import monpkg.entities.Person;
import monpkg.services.ActivityManager;
import monpkg.services.PersonManager;

/**
 * The Class TestActivityManager.
 */
public class TestActivityManager {

	/** The activity manager. */
	@EJB
	ActivityManager activityManager;

	/** The person manager. */
	@EJB
	PersonManager personManager;

	/**
	 * Instantiates a new test activity manager.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public TestActivityManager() throws Exception {
		EJBContainer.createEJBContainer().getContext().bind("inject", this);
	}

	/** The nature. */
	Nature nature;

	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {

	}

	/**
	 * Test save activity.
	 */
	// @Test
	// public void testSaveActivity() {
	// Activity activity = new Activity();
	// activity.setYear(new Date());
	// activity.setNature(Nature.EXPERIENCES_PROFESSIONNELLES);
	// activity.setTitle("Développeur application Hybride");
	// activity.setDescription("Analyse des besoins");
	// activity.setWebAddress("https://github.com/aminenasseh/Gestion-Cvs");
	// Person person = new Person();
	// Person findPerson = new Person();
	// person.setIdPerson(1);
	// findPerson = personManager.findOnePerson(person);
	// activity.setPerson(findPerson);
	// activityManager.saveActivity(activity);
	// }

	/**
	 * Test find one activity.
	 */
	@Test
	public void testFindOneActivity() {
		Activity findActivity = activityManager.findOneActivity("Développeur application Hybride");
		assertEquals("Développeur application Hybride", findActivity.getTitle());
	}

	/**
	 * Test find activity.
	 */
	@Test
	public void testFindActivity() {
		assertNotNull(activityManager.findActivities());
		assertEquals(6, activityManager.findActivities().size());
	}

	/**
	 * Test login.
	 */
	@Test
	public void testLogin() {
		Person person = new Person();
		person.setIdPerson(1);
		Person findPerson = personManager.findOnePerson(person);
		assertNotNull(findPerson);
		Person authPerson = activityManager.login(findPerson.getEmail(), findPerson.getPassword());
		assertNotNull(authPerson);
		Activity activity = new Activity();
		activity.setYear(new Date());
		activity.setNature(Nature.EXPERIENCES_PROFESSIONNELLES);
		activity.setTitle("Développeur Android");
		activity.setDescription("Analyse des besoins, développement");
		activity.setWebAddress("https://github.com/aminenasseh/Gestion-Cvs");
		activity.setPerson(authPerson);
		activityManager.saveActivity(activity);

	}

}
