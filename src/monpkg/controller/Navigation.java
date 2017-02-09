package monpkg.controller;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Navigation {

	public String show() {
		return "showPerson";
	}

	public String savePerson() {
		return "savePerson?faces-redirect=true";
	}

	public String showPersons() {
		return "showPersons?faces-redirect=true";
	}

	public String accueil() {
		return "accueil?faces-redirect=true";
	}
	
	public String accueilAuth() {
		return "accueilAuthPerson?faces-redirect=true";
	}

}
