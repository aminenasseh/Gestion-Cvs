package monpkg.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

import org.junit.Before;
import org.junit.Test;

import monpkg.entities.Person;
import monpkg.services.ActivityManager;
import monpkg.services.PersonManager;

public class TestPersonManager {

	@EJB
	PersonManager personManager;

	@EJB
	ActivityManager activityManager;

	public TestPersonManager() throws Exception {
		EJBContainer.createEJBContainer().getContext().bind("inject", this);
		;
		assertNotNull(personManager);
	}

	@Before
	public void setUp() throws Exception {

	}

	// @Test
	// public void testCreatePerson() throws Exception {
	//
	// assertNotNull(personManager);
	// person.setName("RHAZI");
	// person.setFirstName("Amine");
	// person.setEmail("amine.rhazi@gmail.com");
	// person.setPassword("123");
	// person.setWebSite("aminerhazi.com");
	// person.setBirthday(new Date());
	// personManager.savePerson(person);
	// }

	@Test
	public void testFindOnePerson() throws SQLException {
		Person findPerson = personManager.findOnePerson(1);
		assertEquals("NASSEH", findPerson.getName());
	}

	@Test
	public void testFindPerson() throws SQLException {
		assertNotNull(personManager.findPersons());
		assertEquals(2, personManager.findPersons().size());
	}

	// @Test
	// public void testUpdatePerson() throws Exception {
	// Person findPerson = personManager.findOnePerson(1);
	// assertNotNull(findPerson);
	// findPerson.setEmail("amine@gmail.com");
	// personManager.savePerson(findPerson);
	// assertEquals("amine@gmail.com", findPerson.getEmail());
	//
	// }

	// @Test
	// public void findActivtiesPerson() throws SQLException {
	// Person findPerson = personManager.findOnePerson(1);
	// Person findPersonB = personManager.findOnePerson(1);
	// List<Activity> findPerson2 =
	// personManager.findActivtiesPerson(findPerson);
	// // assertNotNull(personManager.findActivtiesPerson(findPerson));
	// assertEquals("Analyse des besoins", findPerson2.toString());
	// }

}
