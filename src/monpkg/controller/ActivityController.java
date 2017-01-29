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

@ManagedBean(name = "authentificated", eager = false)
@SessionScoped
@ApplicationException
public class ActivityController {

	@EJB
	ActivityManager am;

	Person authPerson = new Person();
	Person person = new Person();

	@PostConstruct
	public void init() {

	}

	public String accueilAuthentificatedPerson() {
		if (am.login(authPerson.getEmail(), authPerson.getPassword()) != null) {
			authPerson = am.getAuthPerson();
			person.setName(authPerson.getName());
			person.setFirstName(authPerson.getFirstName());
			person.setBirthday(authPerson.getBirthday());
			person.setEmail(authPerson.getEmail());
			person.setWebSite(authPerson.getWebSite());

			return "accueilAuthPerson?faces-redirect=true";
		}
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Identifiants incorrects", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);

		return "accueil?faces-redirect=true";
	}

}
