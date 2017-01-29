package monpkg.controller;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Navigation {

	public String accueil() {
		return "accueil";
	}

	public String showPerson() {
		return "showPerson";
	}

}
