package monpkg.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import monpkg.entities.Activity;
import monpkg.entities.Person;
import monpkg.services.ActivityManager;
import monpkg.services.PersonManager;
import monpkg.validator.PersonValidator;

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

	PersonValidator validatePerson = new PersonValidator();

	public PersonValidator getValidatePerson() {
		return validatePerson;
	}

	public void setValidatePerson(PersonValidator validatePerson) {
		this.validatePerson = validatePerson;
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
	private List<Person> resultSearch;

	public List<Person> getResultSearch() {
		return resultSearch;
	}

	public void setResultSearch(List<Person> resultSearch) {
		this.resultSearch = resultSearch;
	}
	/* ********************************************************************* */

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
		if (validatePerson.getName() != null) {
			thePerson.setName(validatePerson.getName());
			thePerson.setFirstName(validatePerson.getFirstName());
			thePerson.setBirthday(validatePerson.getBirthday());
			thePerson.setWebSite(validatePerson.getWebSite());
			thePerson.setEmail(validatePerson.getEmail());
			thePerson.setPassword(validatePerson.getPassword());
			pm.savePerson(thePerson);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Inscription OK", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			validatePerson.setName(null);
			validatePerson.setFirstName(null);
			validatePerson.setBirthday(null);
			validatePerson.setEmail(null);
			validatePerson.setWebSite(null);
			validatePerson.setPassword(null);
			return "accueil?faces-redirect=true";
		}
		return "accueil?faces-redirect=true";
	}

	/**
	 * update persons
	 */
	public String newPerson() {
		thePerson = new Person();
		return "savePerson";

	}

	/* ******************************************* */

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
	public String showResultSearch() {
		setResultSearch(pm.search(title));
		return "search?faces-redirect=true";
	}

}
