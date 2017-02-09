package monpkg.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import monpkg.entities.Activity;
import monpkg.entities.Nature;
import monpkg.entities.Person;
import monpkg.services.ActivityManager;
import monpkg.services.PersonManager;
import monpkg.validator.ActivityValidator;
import monpkg.validator.PersonValidator;

// eager=false permet d'instancier le bean sur demande uniquement.
@ManagedBean(name = "authentificated", eager = false)
@ViewScoped
public class AuthController {

	@EJB
	ActivityManager am;

	@EJB
	private PersonManager pm;

	Person authPerson = new Person();
	Activity activityAuthPerson;

	private List<Activity> activitiesPerson = new ArrayList<Activity>();

	public void setActivitiesPerson(List<Activity> activitiesPerson) {
		this.activitiesPerson = activitiesPerson;
	}

	PersonValidator validatePerson = new PersonValidator();
	ActivityValidator validateActivity = new ActivityValidator();

	List<Nature> natureActivity = new ArrayList<Nature>();

	@PostConstruct
	public void init() {
		System.out.println("Create " + this);
		// validatePerson.setName(null);
		// validatePerson.setFirstName(null);
		// validatePerson.setBirthday(null);
		// validatePerson.setEmail(null);
		// validatePerson.setWebSite(null);
		// validatePerson.setPassword(null);

		// id for anonymous user
		authPerson.setIdPerson(00);

		// validateActivity.setDescription(null);
		// validateActivity.setNature(null);
		// validateActivity.setWebAddress(null);
		// validateActivity.setTitle(null);
		// validateActivity.setYear(null);

		// natureActivity.put("--", "");
		natureActivity.add(Nature.FORMATIONS);
		natureActivity.add(Nature.EXPERIENCES_PROFESSIONNELLES);
		natureActivity.add(Nature.COMPETENCES);
		natureActivity.add(Nature.LANGUES);
		natureActivity.add(Nature.CENTRES_DINTERETS);
	}

	/* ********************************************** */

	public String authentificatedPerson() {
		if (am.login(authPerson.getEmail(), authPerson.getPassword()) != null) {
			authPerson = am.getAuthPerson();
			validatePerson.setName(authPerson.getName());
			validatePerson.setFirstName(authPerson.getFirstName());
			validatePerson.setBirthday(authPerson.getBirthday());
			validatePerson.setEmail(authPerson.getEmail());
			validatePerson.setWebSite(authPerson.getWebSite());
			return "accueilAuthPerson?faces-redirect=true";
		}
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Identifiants incorrects", "");
		FacesContext.getCurrentInstance().addMessage(null, fm);

		return "accueil?faces-redirect=true";
	}

	/* ********************************************** */

	public String logoutPerson() {
		System.out.println(" <=============== Déconnexion ================>");
		authPerson = am.logout();
		return "accueil?faces-redirect=true";
	}

	/* ********************************************** */

	public String updateAuthPerson() {
		authPerson.setName(validatePerson.getName());
		authPerson.setFirstName(validatePerson.getFirstName());
		authPerson.setBirthday(validatePerson.getBirthday());
		authPerson.setWebSite(validatePerson.getWebSite());
		authPerson.setEmail(validatePerson.getEmail());
		authPerson.setPassword(validatePerson.getPassword());
		// am.updateActivity(authPerson);
		pm.savePerson(authPerson);
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise à  jour effectuée", "");
		FacesContext.getCurrentInstance().addMessage(null, fm);

		return "accueilAuthPerson?faces-redirect=true";
	}

	/* ********************************************** */

	public String addActivity() {
		Activity activity = new Activity();
		if (validateActivity.getTitle() != null) {
			activity.setNature(validateActivity.getNature());
			activity.setTitle(validateActivity.getTitle());
			activity.setWebAddress(validateActivity.getWebAddress());
			activity.setYear(validateActivity.getYear());
			activity.setDescription(validateActivity.getDescription());
			activity.setIdActivity(validateActivity.getIdActivity());
			activity.setPerson(authPerson);
			System.out.println("ADD ACTivity OBJECT ========================= >" + activity);
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Votre nouvelle activité a été ajoutée avec succès", "");
			FacesContext.getCurrentInstance().addMessage(null, fm);

			am.saveActivity(activity);

			validateActivity.setTitle(null);
			validateActivity.setNature(null);
			validateActivity.setYear(null);
			validateActivity.setWebAddress(null);
			validateActivity.setDescription(null);

			return "accueilAuthPerson?faces-redirect=true";
		}
		return null;
	}

	/* ********************************************** */

	public void deleteActivity() {
		activitiesPerson.remove(activityAuthPerson);
		am.deleteOneActivity(activityAuthPerson.getTitle());
		activityAuthPerson = null;
	}

	/* ********************************************** */

	public void updateActivity() {
		am.saveActivity(activityAuthPerson);
		System.out.println("Modification activity " + activityAuthPerson.getTitle());
	}

	/* ********************************************** */

	public String ChargerActivity(Activity activity) {
		System.out.println("---------------------------------------------");
		this.activityAuthPerson = activity;
		this.validateActivity.setNature(this.activityAuthPerson.getNature());
		this.validateActivity.setTitle(this.activityAuthPerson.getTitle());
		this.validateActivity.setWebAddress(this.activityAuthPerson.getWebAddress());
		this.validateActivity.setDescription(this.activityAuthPerson.getDescription());
		this.validateActivity.setIdActivity(this.activityAuthPerson.getIdActivity());
		return "accueil?faces-redirect=true";
	}

	/* ********************************************** */

	public void afterEdit(String title) {
		RequestContext.getCurrentInstance().closeDialog(title);
	}

	/* ********************************************** */

	public void onUpdateActivity(ActionEvent ev) {
		FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Activité modifiée"));
	}

	/* ********************************************** */

	public void closeDialog() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}

	/* ********************************************** */

	public List<Activity> getActivitiesPerson() {
		if (authPerson != null) {
			return pm.findActivitiesPerson(authPerson);
		}
		return null;
	}

	/* ********************** GETTERS and SETTERS************************ */

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

	public List<Nature> getNatureActivity() {
		return natureActivity;
	}

	/* ****************************************************************************************************************/
	/***************/
	public void onRowEdit(RowEditEvent event) {
		Activity act = (Activity) event.getObject();
		System.out.println(act.getTitle());
		am.saveActivity(act);

		FacesMessage msg = new FacesMessage("Activité mise à jour", ((Activity) event.getObject()).getTitle());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Mise à jour annulée", ((Activity) event.getObject()).getTitle());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Champs modifié",
					"Old: " + oldValue + ", New:" + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

}
