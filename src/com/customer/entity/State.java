package com.customer.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * The Class State.
 */
@Entity
public class State {
	
	/** The id. */
	@Id
	private Long id;
    
	/** The Abbreviation. */
	private String abbreviation;
    
    /** The Name. */
    private String name;

	/**
	 * Instantiates a new state.
	 */
	public State() {
		
	}
	
	/**
	 * Instantiates a new state.
	 *
	 * @param id the id
	 * @param abbreviation the abbreviation
	 * @param name the name
	 */
	public State(Long id, String abbreviation, String name) {
		this.id = id;
		this.abbreviation = abbreviation;
		this.name = name;
	}
	
	/**
	 * Instantiates a new state.
	 *
	 * @param abbreviation the abbreviation
	 * @param name the name
	 */
	public State(String abbreviation, String name) {
		this.abbreviation = abbreviation;
		this.name = name;
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
	 * Gets the abbreviation.
	 *
	 * @return the abbreviation
	 */
	public final String getAbbreviation() {
		return this.abbreviation;
	}
	
	/**
	 * Sets the abbreviation.
	 *
	 * @param abbreviation the new abbreviation
	 */
	public final void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public final String getName() {
		return this.name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public final void setName(String name) {
		this.name = name;
	}
	
}
