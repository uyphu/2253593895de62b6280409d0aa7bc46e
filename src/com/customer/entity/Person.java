package com.customer.entity;

import com.customer.common.dao.AbstractGenericDaoImpl;
import com.customer.common.dao.GenericDao;
import com.customer.exception.DatabaseException;
import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.OnLoad;
import com.googlecode.objectify.annotation.Parent;

/**
 * The Class Person.
 */
@Entity
public class Person {
	
	/** The id. */
	@Id 
	private Long id;
    
    /** The name. */
    private String name;
    
    @IgnoreSave
    private Long addressId;

    /** The address. */
    @Load
    @Parent
    private Key<Address> address;
    
    /** The address obj. */
    @IgnoreSave
    @Ignore
    private Address addressObj;
    
	/**
	 * Load address.
	 */
	@OnLoad
    private void loadAddress() {
    	if (address != null) {
    		GenericDao dao = new AbstractGenericDaoImpl();
    		try {
				addressObj = dao.getById(Address.class, address.getId());
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }

	/**
	 * Instantiates a new person.
	 */
	public Person() {
		
	}

	/**
	 * Instantiates a new person.
	 *
	 * @param id the id
	 * @param name the name
	 * @param addressId the address id
	 */
	public Person(Long id, String name, Long addressId) {
		this.id = id;
		this.name = name;
		this.addressId = addressId;
	}
	
	/**
	 * Instantiates a new person.
	 *
	 * @param name the name
	 * @param street the street
	 * @param city the city
	 */
	public Person(String name, Long addressId) {
		this.name = name;
		this.addressId = addressId;
	}
	
	/**
	 * Instantiates a new person.
	 *
	 * @param name the name
	 */
	public Person(String name) {
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
	
	public final Long getAddressId() {
		return this.addressId;
	}

	/**
	 * Sets the address id.
	 *
	 * @param addressId the new address id
	 */
	public final void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE) 
	public final Key<Address> getAddress() {
		return this.address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public final void setAddress(Key<Address> address) {
		this.address = address;
	}
	
	/**
	 * Gets the address obj.
	 *
	 * @return the address obj
	 */
	public final Address getAddressObj() {
		return this.addressObj;
	}

	/**
	 * Sets the address obj.
	 *
	 * @param addressObj the new address obj
	 */
	public final void setAddressObj(Address addressObj) {
		this.addressObj = addressObj;
	}

}
