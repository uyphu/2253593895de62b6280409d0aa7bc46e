package com.customer.entity;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;
import java.util.logging.Logger;

import com.customer.common.AppConstants;
import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.AlsoLoad;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.OnLoad;
import com.googlecode.objectify.annotation.Parent;

/**
 * The Class Customer.
 */
@Entity
public class Customer {
	
	@Ignore
	@IgnoreSave
	private static final Logger log =Logger.getLogger(Customer.class.getName());
	
	/** The id. */
	@Id
	private Long id;
    
    /** The First name. */
    private String firstName;
    
    /** The Last name. */
    private String lastName;
    
    /** The Email. */
    private String email;
    
    /** The Address. */
    private String address;
    
    /** The City. */
    private String city;
    
    /** The State. */
    @Load
    @Parent
    private Key<State> stateKey;
    
    /** The state id. */
    @Ignore
    @IgnoreSave
    private Long stateId;
    
    /** The state. */
    @Ignore
    @IgnoreSave
    private State state;
    
    /** The Zip. */
    private Integer zip;
    
    /** The Gender. */
    private Gender gender;
    
    /** The Orders. */
    @Ignore
    @IgnoreSave
    private List<Order> orders;
    
    /**
     * De key.
     */
    @OnLoad
    private void deKey() {
    	if (stateKey != null) {
    		state = ofy().load().type(State.class).id(stateKey.getId()).get();
		}
    }
    
    /**
     * Import gender.
     *
     * @param genderStr the gender str
     */
    public void importGender(@AlsoLoad("sex") String genderStr)
    {
        if (Gender.Female.equals(genderStr))
            this.gender = Gender.Female;
        else
            this.gender = Gender.valueOf(genderStr);
    }
	
	/**
	 * Instantiates a new customer.
	 */
	public Customer() {
		
	}

	/**
	 * Instantiates a new customer.
	 *
	 * @param id the id
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param email the email
	 * @param address the address
	 * @param city the city
	 * @param stateId the state id
	 * @param zip the zip
	 * @param gender the gender
	 */
	public Customer(Long id, String firstName, String lastName, String email, String address, String city, 
			Long stateId, Integer zip, String gender) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.city = city;
		this.stateId = stateId;
		this.zip = zip;
		importGender(gender);
	}
	
	/**
	 * Instantiates a new customer.
	 *
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param email the email
	 * @param address the address
	 * @param city the city
	 * @param stateId the state id
	 * @param zip the zip
	 * @param gender the gender
	 */
	public Customer(String firstName, String lastName, String email, String address, String city, 
			Long stateId, Integer zip, String gender) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.city = city;
		this.stateId = stateId;
		this.zip = zip;
		importGender(gender);
	}
	
	/**
	 * Instantiates a new customer.
	 *
	 * @param firstName the first name
	 */
	public Customer(String firstName) {
		this.firstName = firstName;
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
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public final String getFirstName() {
		return this.firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public final void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public final String getLastName() {
		return this.lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public final void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public final String getEmail() {
		return this.email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public final void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public final String getAddress() {
		return this.address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public final void setAddress(String address) {
		this.address = address;
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

	/**
	 * Gets the state id.
	 *
	 * @return the state id
	 */
	public final Long getStateId() {
		return this.stateId;
	}

	/**
	 * Sets the state id.
	 *
	 * @param stateId the new state id
	 */
	public final void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	/**
	 * Gets the zip.
	 *
	 * @return the zip
	 */
	public final Integer getZip() {
		return this.zip;
	}

	/**
	 * Sets the zip.
	 *
	 * @param zip the new zip
	 */
	public final void setZip(Integer zip) {
		this.zip = zip;
	}

	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public final Gender getGender() {
		return this.gender;
	}

	/**
	 * Sets the gender.
	 *
	 * @param gender the new gender
	 */
	public final void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * Gets the orders.
	 *
	 * @return the orders
	 */
	public final List<Order> getOrders() {
		this.orders = ofy().load().type(Order.class).ancestor(this).limit(AppConstants.MAX_RECORDS).list();
		return this.orders;
	}

	/**
	 * Sets the orders.
	 *
	 * @param orders the new orders
	 */
	public final void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public final State getState() {
		return this.state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public final void setState(State state) {
		this.state = state;
	}

	/**
	 * Sets the state key.
	 *
	 * @param stateKey the new state key
	 */
	public final void setStateKey(Key<State> stateKey) {
		this.stateKey = stateKey;
	}

	/**
	 * Gets the state key.
	 *
	 * @return the state key
	 */
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE) 
	public final Key<State> getStateKey() {
		return this.stateKey;
	}
	
}
