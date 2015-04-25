package com.customer.entity;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.IgnoreSave;

/**
 * The Class Person.
 */
@Entity
public class Address {
	
	/** The id. */
    @Id 
    private Long id;
    
    /** The street. */
    private String street;
    
    /** The city. */
    private String city;
    
    /** The persons. */
    @Ignore
    @IgnoreSave
    private List<Person> persons;
    
    
//    @OnLoad
//    public void loadPersons() {
//		if (id != null) {
//			persons = ofy().load().type(Person.class).ancestor(this).limit(1000).list();
//		}
//    }

	/**
	 * Instantiates a new person.
	 */
	public Address() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new person.
	 *
	 * @param id the id
	 * @param street the street
	 * @param city the city
	 */
	public Address(Long id, String street, String city) {
		this.id = id;
		this.street = street;
		this.city = city;
	}
	
	/**
	 * Instantiates a new person.
	 *
	 * @param street the street
	 * @param city the city
	 */
	public Address(String street, String city) {
		this.street = street;
		this.city = city;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public final Long getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public final void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the street.
	 *
	 * @return the street
	 */
	public final String getStreet() {
		return this.street;
	}

	/**
	 * Sets the street.
	 *
	 * @param street the new street
	 */
	public final void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public final String getCity() {
		return this.city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public final void setCity(String city) {
		this.city = city;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons = ofy().load().type(Person.class).ancestor(this).limit(1000).list();
	}
	
	

}
