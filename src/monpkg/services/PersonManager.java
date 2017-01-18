package monpkg.services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import monpkg.entities.Person;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PersonManager {

	@PostConstruct()
	public void debut() {
		System.out.println("Starting " + this);
	}

	@PreDestroy
	public void fin() {
		System.out.println("Stopping " + this);
	}

	@PersistenceContext(unitName = "myMySQLBase")
	EntityManager em;

	public List<Person> findPersons() {
		return em.createQuery("SELECT p FROM Person p", Person.class).getResultList();
	}

	public Person findOnePerson(Person person) {
		return em.find(Person.class, person.getIdPerson());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void savePerson(Person p) {
		if (p.getIdPerson() == null) {
			em.persist(p);
		} else {
			p = em.merge(p);
		}
	}

	// @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void deletePerson(Person person) {
		Person p = em.find(Person.class, person.getIdPerson());
		if (p != null) {
			em.remove(p);
		}
	}

	// @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void createPerson(Person person) {
		if (em.find(Person.class, person.getIdPerson()) == null) {
			em.persist(person);
		} else {
			em.merge(person);
		}
	}

}
