package monpkg.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.*;

@Entity
@Table(name = "persons")
public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPerson;

	@Column
	@NotNull
	@Size(min = 5, max = 20)
	private String name;

	@Column
	@NotNull
	@Size(min = 5, max = 20)
	private String firstName;

	@Column
	@NotNull
	@Size(min = 20, max = 40)
	private String email;

	@Column
	@NotNull
	@Size(min = 5, max = 20)
	private String webSite;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	@Column
	@NotNull
	@Size(min = 3, max = 20)
	private String password;

	@OneToMany(mappedBy = "person", cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	@JoinTable(name = "person_activities")
	private Set<Activity> activities = new HashSet<Activity>();

	public Integer getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(Integer idPerson) {
		this.idPerson = idPerson;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Activity> getActivities() {
		return activities;
	}

	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
	}

}
