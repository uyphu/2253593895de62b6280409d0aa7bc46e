package com.customer.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * The Class UserLogin.
 */
@Entity
public class UserLogin {
	
	/** The id. */
	@Id
	private Long id;
	
	/** The User name. */
	@Index
	private String userName;
    
    /** The Password. */
    private String password;
    
    /** The status. */
    private String status;
	
	/**
	 * Instantiates a new user login.
	 */
	public UserLogin() {
		
	}
	
	/**
	 * Instantiates a new user login.
	 *
	 * @param id the id
	 * @param userName the user name
	 * @param password the password
	 */
	public UserLogin(Long id, String userName, String password) {
		this.id = id;
		this.userName = userName;
		this.password = password;
	}

	/**
	 * Instantiates a new user login.
	 *
	 * @param userName the user name
	 * @param password the password
	 */
	public UserLogin(String userName, String password) {
		this.userName = userName;
		this.password = password;
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
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public final String getUserName() {
		return this.userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName the new user name
	 */
	public final void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public final String getPassword() {
		return this.password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public final void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public final String getStatus() {
		return this.status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public final void setStatus(String status) {
		this.status = status;
	}
	
}
