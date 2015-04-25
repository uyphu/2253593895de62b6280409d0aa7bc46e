package com.customer.services;

import static com.customer.api.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.customer.entity.Customer;
import com.customer.entity.Gender;
import com.customer.entity.State;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

@Api(name = "customerendpoint", namespace = @ApiNamespace(ownerDomain = "customer.com", ownerName = "customer.com", packagePath = "entity"))
public class CustomerEndpoint {

	/**
	* Return a collection of customers
	*
	* @param count The number of customers
	* @return a list of Customers
	*/
	@ApiMethod(name = "listCustomer")
	public CollectionResponse<Customer> listCustomer(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("count") Integer count) {

		Cursor cursor = null;
		Query<Customer> query = ofy().load().type(Customer.class);
		if (count != null)
			query.limit(count);
		if (cursorString != null && cursorString != "") {
			query = query.startAt(Cursor.fromWebSafeString(cursorString));
		}
		
		List<Customer> records = new ArrayList<Customer>();
		QueryResultIterator<Customer> iterator = query.iterator();
		int num = 0;
		while (iterator.hasNext()) {
			records.add(iterator.next());
			if (count != null) {
				num++;
				if (num == count)
					break;
			}
		}

		// Find the next cursor
		cursor = iterator.getCursor();
		if (cursor != null) {
			cursorString = cursor.toWebSafeString();
		}
//		if (cursorString != null && cursorString != "") {
//			Cursor cursor = iterator.getCursor();
//			if (cursor != null) {
//				cursorString = cursor.toWebSafeString();
//			}
//		}
		return CollectionResponse.<Customer> builder().setItems(records)
				.setNextPageToken(cursorString).build();
	}
	
	/**
	* This inserts a new <code>Customer</code> object.
	* @param customer The object to be added.
	* @return The object to be added.
	*/
	@ApiMethod(name = "insertCustomer")
	public Customer insertCustomer(Customer customer) throws ConflictException {
		// If if is not null, then check if it exists. If yes, throw an
		// Exception
		// that it is already present
		if (customer.getId() != null) {
			if (findRecord(customer.getId()) != null) {
				throw new ConflictException("Object already exists");
			}
		}
		// Since our @Id field is a Long, Objectify will generate a unique value
		// for us
		// when we use put
		ofy().save().entity(customer).now();
		return customer;
	}

	/**
	 * This updates an existing <code>Customer</code> object.
	 * 
	 * @param customer
	 *            The object to be added.
	 * @return The object to be updated.
	 */
	@ApiMethod(name = "updateCustomer")
	public Customer updateCustomer(Customer customer) throws NotFoundException {
		if (findRecord(customer.getId()) == null) {
			throw new NotFoundException("Customer Record does not exist");
		}
		ofy().save().entity(customer).now();
		return customer;
	}

	/**
	 * This deletes an existing <code>Customer</code> object.
	 * 
	 * @param id
	 *            The id of the object to be deleted.
	 */
	@ApiMethod(name = "removeCustomer")
	public void removeCustomer(@Named("id") Long id) throws NotFoundException {
		Customer record = findRecord(id);
		if (record == null) {
			throw new NotFoundException("Customer Record does not exist");
		}
		ofy().delete().entity(record).now();
	}
	
	@ApiMethod(name = "getCustomer")
	public Customer getCustomer(@Named("id") Long id) {
		return findRecord(id);
	}
	
	@ApiMethod(name = "getCustomersSumary")
	public CollectionResponse<Customer> getCustomersSumary(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("count") Integer count) {

		Query<Customer> query = ofy().load().type(Customer.class);
		if (count != null)
			query.limit(count);
		if (cursorString != null && cursorString != "") {
			query = query.startAt(Cursor.fromWebSafeString(cursorString));
		}

		List<Customer> records = new ArrayList<Customer>();
		QueryResultIterator<Customer> iterator = query.iterator();
		int num = 0;
		while (iterator.hasNext()) {
			records.add(iterator.next());
			if (count != null) {
				num++;
				if (num == count)
					break;
			}
		}

		// Find the next cursor
		Cursor cursor = iterator.getCursor();
		if (cursor != null) {
			cursorString = cursor.toWebSafeString();
		}
		return CollectionResponse.<Customer> builder().setItems(records)
				.setNextPageToken(cursorString).build();
	} 
	

	// Private method to retrieve a <code>Customer</code> record
	private Customer findRecord(Long id) {
		return ofy().load().type(Customer.class).id(id).get();
	}
	
	@ApiMethod(name = "initData")
	public void initData() {
		Customer customer;
		Key<State> key;
		customer = new Customer(1L, "Phu", "Le", "phule@yahoo.com", "17/2 KP ABC", "Binh Duong", 1L, 12345, Gender.Male.toString());
		key = Key.create(State.class, customer.getStateId());
		customer.setStateKey(key);
		ofy().save().entity(customer).now();
		customer = new Customer(2L, "Nam", "Le", "phule01@yahoo.com", "11/2 KP ABC", "Binh Duong", 2L, 12345, Gender.Male.toString());
		key = Key.create(State.class, customer.getStateId());
		customer.setStateKey(key);
		ofy().save().entity(customer).now();
		customer = new Customer(3L, "Tuan", "Lee", "phule02@yahoo.com", "17/2 KP ABC", "Kansas", 3L, 12345, Gender.Male.toString());
		key = Key.create(State.class, customer.getStateId());
		customer.setStateKey(key);
		ofy().save().entity(customer).now();
		customer = new Customer(4L, "Khai", "Lam", "phule03@yahoo.com", "14/2 KP ABC", "Binh Duong", 1L, 12345, Gender.Male.toString());
		key = Key.create(State.class, customer.getStateId());
		customer.setStateKey(key);
		ofy().save().entity(customer).now();
		customer = new Customer(5L, "Thuan", "Le", "phule04@yahoo.com", "12 KP ABC", "Washington", 3L, 12345, Gender.Female.toString());
		key = Key.create(State.class, customer.getStateId());
		customer.setStateKey(key);
		ofy().save().entity(customer).now();
		customer = new Customer(6L, "Huong", "Ly", "phule05@yahoo.com", "17/2 KP ABC", "Binh Duong", 4L, 12345, Gender.Female.toString());
		key = Key.create(State.class, customer.getStateId());
		customer.setStateKey(key);
		ofy().save().entity(customer).now();
		
	}

}
