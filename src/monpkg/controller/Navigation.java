package monpkg.controller;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Navigation {

	public String showPerson() {
		return "showPerson";
	}

	public String editPerson() {
		return "editPerson?faces-redirect=true";
	}

	public String showPersons() {
		return "showPersons?faces-redirect=true";
	}

	public String accueil() {
		return "accueil?faces-redirect=true";
	}

}
