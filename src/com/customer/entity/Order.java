package com.customer.entity;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Date;

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
 * The Class Order.
 */
@Entity
public class Order {

	/** The id. */
	@Id
	private Long id;
    
    /** The Product. */
    private String product;
    
    /** The Price. */
    private Double price;
    
    /** The Quantity. */
    private Integer quantity;
    
    /** The date. */
    private Date date;
    
    /** The customer id. */
    @Ignore
    @IgnoreSave
    private Long customerId;
    
    /** The customer. */
    @Load
    @Parent
    private Key<Customer> customerKey;
    
    /** The customer. */
    @Ignore
    @IgnoreSave
    private Customer customer;
    
    /**
     * De key.
     */
    @OnLoad 
    public void deKey() {
    	if (customerKey != null) {
    		customer = ofy().load().type(Customer.class).id(customerKey.getId()).get();
    	}
    }

    /**
     * Clone.
     *
     * @return the order
     */
    public Order Clone()
    {
        return (Order)this.Clone();
    }
    

    /**
     * Instantiates a new order.
     */
    public Order() {
    	
	}

	/**
	 * Instantiates a new order.
	 *
	 * @param id the id
	 * @param product the product
	 * @param price the price
	 * @param quantity the quantity
	 * @param date the date
	 * @param customerId the customer id
	 */
	public Order(Long id, String product, Double price, Integer quantity, Date date, Long customerId) {
		this.id = id;
		this.product = product;
		this.price = price;
		this.quantity = quantity;
		this.date = date;
		this.customerId = customerId;
	}

	
	/**
	 * Instantiates a new order.
	 *
	 * @param product the product
	 * @param price the price
	 * @param quantity the quantity
	 * @param date the date
	 * @param customerId the customer id
	 */
	public Order(String product, Double price, Integer quantity, Date date, Long customerId) {
		this.product = product;
		this.price = price;
		this.quantity = quantity;
		this.date = date;
		this.customerId = customerId;
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
	 * Gets the product.
	 *
	 * @return the product
	 */
	public final String getProduct() {
		return this.product;
	}

	/**
	 * Sets the product.
	 *
	 * @param product the new product
	 */
	public final void setProduct(String product) {
		this.product = product;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public final Double getPrice() {
		return this.price;
	}

	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public final void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public final Integer getQuantity() {
		return this.quantity;
	}

	/**
	 * Sets the quantity.
	 *
	 * @param quantity the new quantity
	 */
	public final void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public final Date getDate() {
		return this.date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public final void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Gets the customer id.
	 *
	 * @return the customer id
	 */
	public final Long getCustomerId() {
		return this.customerId;
	}

	/**
	 * Sets the customer id.
	 *
	 * @param customerId the new customer id
	 */
	public final void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	/**
	 * Gets the customer key.
	 *
	 * @return the customer key
	 */
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE) 
	public final Key<Customer> getCustomerKey() {
		return this.customerKey;
	}

	/**
	 * Sets the customer key.
	 *
	 * @param customerKey the new customer key
	 */
	public final void setCustomerKey(Key<Customer> customerKey) {
		this.customerKey = customerKey;
	}

	/**
	 * Gets the customer.
	 *
	 * @return the customer
	 */
	public final Customer getCustomer() {
		return this.customer;
	}

	/**
	 * Sets the customer.
	 *
	 * @param customer the new customer
	 */
	public final void setCustomer(Customer customer) {
		this.customer = customer;
	}

	
}
