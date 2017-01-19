package monpkg.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

import org.junit.Before;
import org.junit.Test;

import monpkg.entities.Activity;
import monpkg.entities.Person;
import monpkg.services.PersonManager;

public class TestPersonManager {

	@EJB
	PersonManager personManager;

	Person person = new Person();

	public TestPersonManager() throws Exception {
		EJBContainer.createEJBContainer().getContext().bind("inject", this);
		assertNotNull(personManager);
	}

	@Before
	public void setUp() throws Exception {
		// assertNotNull(personManager);
		// person.setName("NASSEH");
		// person.setFirstName("Mohamed Amine");
		// person.setEmail("mohamedamine.nasseh@gmail.com");
		// person.setPassword("123");
		// person.setWebSite("aminenasseh.com");
		// person.setBirthday(new Date());
		// personManager.savePerson(person);
	}

	/*
	 * @Test public void testCreatePerson() throws Exception {
	 * 
	 * assertNotNull(personManager); person.setName("RHAZI");
	 * person.setFirstName("Amine"); person.setEmail("amine.rhazi@gmail.com");
	 * person.setPassword("123"); person.setWebSite("aminerhazi.com");
	 * person.setBirthday(new Date()); personManager.savePerson(person); }
	 */

	@Test
	public void testFindOnePerson() throws SQLException {
		Person person = new Person();
		Person findPerson = new Person();
		person.setIdPerson(1);
		findPerson = personManager.findOnePerson(person);
		assertEquals("NASSEH", findPerson.getName());
	}

	@Test
	public void testFindPerson() throws SQLException {
		assertNotNull(personManager.findPersons());
		assertEquals(1, personManager.findPersons().size());
	}

	@Test
	public void testUpdatePerson() {
		Person person = new Person();
		person.setIdPerson(1);
		person = personManager.findOnePerson(person);
		person.setEmail("amine.nasseh@gmail.com");
		personManager.savePerson(person);
		Person personUp = personManager.findOnePerson(person);
		assertTrue(personUp.getEmail().equals("amine.nasseh@gmail.com"));
	}

	@Test
	public void findActivtiesPerson() throws SQLException {
		Person person = new Person();
		person.setIdPerson(1);
		List<Activity> personActivities = personManager.findActivtiesPerson(person);
		assertEquals("Analyse des besoins", personActivities.get(0).getDescription());
	}

	@Test
	 public void findByTitle() throws SQLException {
	 List<Activity> personActivities = personManager.findByTitle("Développeur application Hybride");
	 assertEquals("Développeur application Hybride",
	 personActivities.get(0).getTitle());
	 }

}
