package monpkg.controller;

import javax.annotation.PostConstruct;
import javax.ejb.ApplicationException;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import monpkg.entities.Person;
import monpkg.services.ActivityManager;
import monpkg.validator.ActivityValidator;
import monpkg.validator.PersonValidator;

@ManagedBean(name = "authentificated", eager = false)
@SessionScoped
@ApplicationException
public class ActivityController {

	@EJB
	ActivityManager am;

	Person authPerson = new Person();
	PersonValidator validatePerson = new PersonValidator();
	ActivityValidator validateActivity = new ActivityValidator();

	@PostConstruct
	public void init() {
		System.out.println("Create " + this);
		validatePerson.setName(null);
		validatePerson.setFirstName(null);
		validatePerson.setBirthday(null);
		validatePerson.setEmail(null);
		validatePerson.setWebAddress(null);
		validatePerson.setPassword(null);

		// id for anonymous user
		authPerson.setIdPerson(00);

		validateActivity.setNature(null);
		validateActivity.setTitle(null);
		validateActivity.setYear(null);
		validateActivity.setDescription(null);
	}
	
	/* ********************************************************************************************************** */

	public String authentificatedPerson() {
		if (am.login(authPerson.getEmail(), authPerson.getPassword()) != null) {
			authPerson = am.getAuthPerson();
			validatePerson.setName(authPerson.getName());
			validatePerson.setFirstName(authPerson.getFirstName());
			validatePerson.setBirthday(authPerson.getBirthday());
			validatePerson.setEmail(authPerson.getEmail());
			validatePerson.setWebAddress(authPerson.getWebSite());

			return "accueilAuthPerson?faces-redirect=true";
		}
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Identifiants incorrects", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);

		return "accueil?faces-redirect=true";
	}
	
	/* ********************************************************************************************************** */

	public String logoutPerson() {
		System.out.println("test");
		authPerson = am.logout();
		return "accueil?faces-redirect=true";
	}
	
	/* ********************************************************************************************************** */
	
	public String updateAuthPerson() {
		authPerson.setName(validatePerson.getName());
		authPerson.setFirstName(validatePerson.getFirstName());
		authPerson.setBirthday(validatePerson.getBirthday());
		authPerson.setWebSite(validatePerson.getWebAddress());
		authPerson.setEmail(validatePerson.getEmail());
		authPerson.setPassword(validatePerson.getPassword());
		am.updateActivity(authPerson);
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise à jour effectuée", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);

		return "accueilAuthPerson?faces-redirect=true";
	}
	
	/* ********************************************************************************************************** */
	
	/* ********************************************************************************************************** */

	// Update person
	// New Activity

	
	/* ****************************************** GETTERS AND SETTERS ****************************************** */
	
	public Person getAuthPerson() {
		return authPerson;
	}

	public void setAuthPerson(Person authPerson) {
		this.authPerson = authPerson;
	}

	public PersonValidator getValidatePerson() {
		return validatePerson;
	}

	public void setValidatePerson(PersonValidator validatePerson) {
		this.validatePerson = validatePerson;
	}

	public ActivityValidator getValidateActivity() {
		return validateActivity;
	}

	public void setValidateActivity(ActivityValidator validateActivity) {
		this.validateActivity = validateActivity;
	}

}
