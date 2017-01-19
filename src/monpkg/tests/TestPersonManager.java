package monpkg.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

import org.junit.Before;
import org.junit.Test;

import monpkg.entities.Activity;
import monpkg.entities.Person;
import monpkg.services.PersonManager;

/**
 * The Class TestPersonManager.
 */
public class TestPersonManager {

	/** The person manager. */
	@EJB
	PersonManager personManager;

	/** The person. */
	Person person = new Person();

	/**
	 * Instantiates a new test person manager.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public TestPersonManager() throws Exception {
		EJBContainer.createEJBContainer().getContext().bind("inject", this);
		assertNotNull(personManager);
	}

	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		person.setName("NASSEH");
		person.setFirstName("Mohamed Amine");
		person.setEmail("mohamedamine.nasseh@gmail.com");
		person.setPassword("123");
		person.setWebSite("aminenasseh.com");
		person.setBirthday(new Date());
		personManager.savePerson(person);
	}

	/**
	 * Test create person.
	 */
	@Test
	public void testCreatePerson() {
		assertNotNull(personManager);
		person.setName("RHAZI");
		person.setFirstName("Amine");
		person.setEmail("amine.rhazi@gmail.com");
		person.setPassword("123");
		person.setWebSite("aminerhazi.com");
		person.setBirthday(new Date());
		personManager.savePerson(person);
	}

	/**
	 * Test find one person.
	 */
	@Test
	public void testFindOnePerson() {
		Person person = new Person();
		Person findPerson = new Person();
		person.setIdPerson(1);
		findPerson = personManager.findOnePerson(person);
		assertEquals("NASSEH", findPerson.getName());
	}

	/**
	 * Test find person.
	 */
	@Test
	public void testFindPerson() {
		assertNotNull(personManager.findPersons());
		assertEquals(1, personManager.findPersons().size());
	}

	/**
	 * Test update person.
	 */
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
	/**
	 * Test delete one person.
	 * 
	 * @expected delete person and his activities
	 */
	@Test
	public void testDeletePerson() {
		Person person = new Person();
		person.setIdPerson(1);
		Person findPerson = personManager.findOnePerson(person);
		personManager.deletePerson(findPerson);

	}

	/**
	 * Find activities person.
	 */
	@Test
	public void findActivitiesPerson() {
		Person person = new Person();
		person.setIdPerson(1);
		List<Activity> personActivities = personManager.findActivitiesPerson(person);
		assertEquals("Analyse des besoins", personActivities.get(0).getDescription());
	}

	/**
	 * Find by title.
	 */
	 @Test
	 public void findByTitle() {
	 List<Activity> personActivities = personManager.findByTitle("Développeur application Hybride");
	 assertEquals("Développeur application Hybride",
	 personActivities.get(0).getTitle());
	 }

}
