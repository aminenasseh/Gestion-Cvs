package monpkg.validator;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ManagedBean
public class ActivityValidator {

	private String nature;

	@Size(min = 5, max = 100, message = "Titre d'une activite: min 5 car, max 50 car")
	private String title;

	@NotNull(message = "Entrer une date")
	private Date year;

	@Size(min = 5, max = 500, message = "Description d'une activite: min 5 car, max 50 car")
	private String description;

	public Date getYear() {
		return year;
	}

	public void setYear(Date year) {
		this.year = year;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
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
