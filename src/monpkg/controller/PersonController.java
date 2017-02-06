package monpkg.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import monpkg.entities.Activity;
import monpkg.entities.Person;
import monpkg.services.ActivityManager;
import monpkg.services.PersonManager;

// @ManagedBean : création des beans à la demande
// @SessionScoped : L'instance du bean est associée à la session
@ManagedBean(name = "person", eager = false)
@SessionScoped
public class PersonController {

	@EJB
	private PersonManager pm;

	@EJB
	private ActivityManager am;
	
	// private List<Person> persons;

	@PostConstruct
	public void init() {
		System.out.println("Create " + this);
		if (pm.findPersons().size() == 0) {
			Person p1 = new Person();
			p1.setName("OBAMA");
			p1.setFirstName("barack");
			p1.setEmail("obama@gmail.com");
			p1.setPassword("123");
			p1.setBirthday(new Date());
			p1.setWebSite("obamacare.com");
			pm.savePerson(p1);
		}
	}

	/* ********************************************************************* */
	private Person thePerson = new Person();

	/**
	 * Getter thePerson
	 */
	public Person getThePerson() {
		return thePerson;
	}

	/* ********************************************************************* */
	private Activity activity = new Activity();

	public Activity getActivity() {
		return activity;
	}

	/* ********************************************************************* */
	private List<Activity> theActivities;

	public List<Activity> getTheActivities() {
		return theActivities;
	}

	public void setTheActivities(List<Activity> theActivities) {
		this.theActivities = theActivities;
	}
	/* ********************************************************************* */

	private String title;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/* ********************************************************************* */

	/**
	 * List of persons
	 */
	public List<Person> getPersons() {
		return pm.findPersons();
	}

	/**
	 * find one person
	 */
	public String show(Person person) {
		thePerson = pm.findOne(person);
		return "showPerson?faces-redirect=true";
	}

	/**
	 * save persons
	 */
	public String save() {
		pm.savePerson(thePerson);
		return "showPerson";
	}

	/**
	 * update persons
	 */
	public String newPerson() {
		thePerson = new Person();
		return "editPerson";

	}

	/* * ************************************************************************* */

	/**
	 * list activities for all persons
	 */
	public List<Activity> getActivities() {
		if (thePerson != null) {
			return am.findActivities();
		}
		return null;
	}
	
	/**
	 * list activities for one persons
	 */
	public List<Activity> getActivitiesPerson() {
		if (thePerson != null) {
			return pm.findActivitiesPerson(thePerson);
		}
		return null;

	}

	/**
	 * list activities by titles
	 */
	public String showActivity(String title) {
		theActivities = pm.findActivityByTitle(title);
		title = "";
		return "search?faces-redirect=true";
	}

}
