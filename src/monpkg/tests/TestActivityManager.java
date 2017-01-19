package monpkg.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
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

public class TestActivityManager {

	@EJB
	ActivityManager activityManager;

	@EJB
	PersonManager personManager;

	public TestActivityManager() throws Exception {
		EJBContainer.createEJBContainer().getContext().bind("inject", this);
	}

	Nature nature;

	@Before
	public void setUp() throws Exception {

	}

	// @Test
	// public void testSaveActivity() throws SQLException {
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

	@Test
	public void testFindOneActivity() throws SQLException {
		Activity findActivity = activityManager.findOneActivity("Développeur application Hybride");
		assertEquals("Développeur application Hybride", findActivity.getTitle());
	}

	@Test
	public void testFindActivity() throws SQLException {
		assertNotNull(activityManager.findActivities());
		assertEquals(3, activityManager.findActivities().size());
	}

	@Test
	public void testLogin() throws Exception {
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
