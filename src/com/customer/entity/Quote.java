package com.customer.entity;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

// TODO: Auto-generated Javadoc
/**
 * The Class Quote.
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Quote {

	/** The id. */
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	/** The message. */
	@Persistent
	private String message;
	
	/** The author. */
	@Persistent
	private String author;
	
	/**
	 * Instantiates a new quote.
	 */
	public Quote() {
		
	}
	
	/**
	 * Instantiates a new quote.
	 *
	 * @param id the id
	 * @param message the message
	 * @param author the author
	 */
	public Quote(Long id, String message, String author) {
		super();
		this.id = id;
		this.message = message;
		this.author = author;
	}
	
	/**
	 * Instantiates a new quote.
	 *
	 * @param message the message
	 * @param author the author
	 */
	public Quote(String message, String author) {
		this.message = message;
		this.author = author;
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
	 * Gets the message.
	 *
	 * @return the message
	 */
	public final String getMessage() {
		return this.message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public final void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the author.
	 *
	 * @return the author
	 */
	public final String getAuthor() {
		return this.author;
	}

	/**
	 * Sets the author.
	 *
	 * @param author the new author
	 */
	public final void setAuthor(String author) {
		this.author = author;
	}
	
}
