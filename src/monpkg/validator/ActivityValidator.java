package monpkg.validator;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import monpkg.entities.Nature;

@ManagedBean
public class ActivityValidator {

	private Integer idActivity;

	@Enumerated(EnumType.STRING)
	private Nature nature;

	@Size(min = 5, max = 100, message = "Titre d'une activite: min 5 car, max 50 car")
	private String title;

	@NotNull(message = "Entrer une date")
	private Date year;

	@Size(min = 5, max = 500, message = "Description d'une activite: min 5 car, max 50 car")
	private String description;

	@Size(min = 5, max = 200, message = "L'adresse web d'une activite: min 5 car, max 50 car")
	private String webAddress;

	public Integer getIdActivity() {
		return idActivity;
	}

	public void setIdActivity(Integer idActivity) {
		this.idActivity = idActivity;
	}

	public String getWebAddress() {
		return webAddress;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	public Date getYear() {
		return year;
	}

	public void setYear(Date year) {
		this.year = year;
	}

	public Nature getNature() {
		return nature;
	}

	public void setNature(Nature nature) {
		this.nature = nature;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
