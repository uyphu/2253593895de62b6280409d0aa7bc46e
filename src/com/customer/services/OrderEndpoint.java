package com.customer.services;

import static com.customer.api.OfyService.ofy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.customer.entity.Customer;
import com.customer.entity.Order;
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

@Api(name = "orderendpoint", namespace = @ApiNamespace(ownerDomain = "customer.com", ownerName = "customer.com", packagePath = "entity"))
public class OrderEndpoint {

	/**
	* Return a collection of orders
	*
	* @param count The number of orders
	* @return a list of Orders
	*/
	@ApiMethod(name = "listOrder")
	public CollectionResponse<Order> listOrder(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("count") Integer count,
			@Nullable @Named("customerId") Long customerId) {

		Query<Order> query = ofy().load().type(Order.class);
		if (customerId != null ) {
			Key<Customer> key =  Key.create(Customer.class, customerId);
			query = ofy().load().type(Order.class).filter("customerKey", key);
		}
		if (count != null)
			query.limit(count);
		if (cursorString != null && cursorString != "") {
			query = query.startAt(Cursor.fromWebSafeString(cursorString));
		}

		List<Order> records = new ArrayList<Order>();
		QueryResultIterator<Order> iterator = query.iterator();
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
//		if (cursorString != null && cursorString != "") {
//			Cursor cursor = iterator.getCursor();
//			if (cursor != null) {
//				cursorString = cursor.toWebSafeString();
//			}
//		}
		Cursor cursor = iterator.getCursor();
		if (cursor != null) {
			cursorString = cursor.toWebSafeString();
		}
		return CollectionResponse.<Order> builder().setItems(records)
				.setNextPageToken(cursorString).build();
	}
	
	/**
	* This inserts a new <code>Order</code> object.
	* @param order The object to be added.
	* @return The object to be added.
	*/
	@ApiMethod(name = "insertOrder")
	public Order insertOrder(Order order) throws ConflictException {
		// If if is not null, then check if it exists. If yes, throw an
		// Exception
		// that it is already present
		if (order.getId() != null) {
			if (order.getId() == 0) {
				order.setId(null);
				order.setDate(Calendar.getInstance().getTime());
			} else {
				if (findRecord(order.getId()) != null) {
					throw new ConflictException("Object already exists");
				}
			}
		}
		Key<Customer> key = Key.create(Customer.class, order.getCustomerId());
		if (key != null) {
			order.setCustomerKey(key);
		}
		// Since our @Id field is a Long, Objectify will generate a unique value
		// for us
		// when we use put
		ofy().save().entity(order).now();
		return order;
	}

	/**
	 * This updates an existing <code>Order</code> object.
	 * 
	 * @param order
	 *            The object to be added.
	 * @return The object to be updated.
	 */
	@ApiMethod(name = "updateOrder")
	public Order updateOrder(Order order) throws NotFoundException {
		if (findRecord(order.getId()) == null) {
			throw new NotFoundException("Order Record does not exist");
		}
		Key<Customer> key = Key.create(Customer.class, order.getCustomerId());
		if (key != null) {
			order.setCustomerKey(key);
		}
		ofy().save().entity(order).now();
		return order;
	}

	/**
	 * This deletes an existing <code>Order</code> object.
	 * 
	 * @param id
	 *            The id of the object to be deleted.
	 */
	@ApiMethod(name = "removeOrder")
	public void removeOrder(@Named("id") Long id) throws NotFoundException {
		Order record = findRecord(id);
		if (record == null) {
			throw new NotFoundException("Order Record does not exist");
		}
		ofy().delete().entity(record).now();
	}
	
	@ApiMethod(name = "getOrder")
	public Order getOrder(@Named("id") Long id) {
		return findRecord(id);
	}

	// Private method to retrieve a <code>Order</code> record
	/**
	 * Find record.
	 *
	 * @param id the id
	 * @return the order
	 */
	private Order findRecord(Long id) {
		return ofy().load().type(Order.class).id(id).get();
		// or return ofy().load().type(Order.class).filter("id",id).first.now();
	}
	
	@ApiMethod(name = "initData")
	public void initData() {
		Order order;
		Key<Customer> key;
		order = new Order("IPhone 4", 500.0, 2, Calendar.getInstance().getTime(), 1L);
		key = Key.create(Customer.class, order.getCustomerId());
		order.setCustomerKey(key);
		ofy().save().entity(order).now();
		order = new Order("IPhone 4", 500.0, 2, Calendar.getInstance().getTime(), 2L);
		key = Key.create(Customer.class, order.getCustomerId());
		order.setCustomerKey(key);
		ofy().save().entity(order).now();
		order = new Order("IPhone 4", 500.0, 2, Calendar.getInstance().getTime(), 3L);
		key = Key.create(Customer.class, order.getCustomerId());
		order.setCustomerKey(key);
		ofy().save().entity(order).now();
		order = new Order("IPhone 4", 500.0, 2, Calendar.getInstance().getTime(), 4L);
		key = Key.create(Customer.class, order.getCustomerId());
		order.setCustomerKey(key);
		ofy().save().entity(order).now();
		order = new Order("IPhone 4", 500.0, 2, Calendar.getInstance().getTime(), 5L);
		key = Key.create(Customer.class, order.getCustomerId());
		order.setCustomerKey(key);
		ofy().save().entity(order).now();
		order = new Order("IPhone 4", 500.0, 2, Calendar.getInstance().getTime(), 6L);
		key = Key.create(Customer.class, order.getCustomerId());
		order.setCustomerKey(key);
		ofy().save().entity(order).now();
		order = new Order("IPhone 5S", 600.0, 2, Calendar.getInstance().getTime(), 1L);
		key = Key.create(Customer.class, order.getCustomerId());
		order.setCustomerKey(key);
		ofy().save().entity(order).now();
		order = new Order("IPhone 5S", 600.0, 2, Calendar.getInstance().getTime(), 3L);
		key = Key.create(Customer.class, order.getCustomerId());
		order.setCustomerKey(key);
		ofy().save().entity(order).now();
		order = new Order("IPhone 5S", 600.0, 2, Calendar.getInstance().getTime(), 4L);
		key = Key.create(Customer.class, order.getCustomerId());
		order.setCustomerKey(key);
		ofy().save().entity(order).now();
		order = new Order("IPhone 5S", 600.0, 2, Calendar.getInstance().getTime(), 5L);
		key = Key.create(Customer.class, order.getCustomerId());
		order.setCustomerKey(key);
		ofy().save().entity(order).now();
		
		order = new Order("Mac Book Pro", 2000.0, 1, Calendar.getInstance().getTime(), 1L);
		key = Key.create(Customer.class, order.getCustomerId());
		order.setCustomerKey(key);
		ofy().save().entity(order).now();
		order = new Order("Mac Book Pro", 2000.0, 4, Calendar.getInstance().getTime(), 3L);
		key = Key.create(Customer.class, order.getCustomerId());
		order.setCustomerKey(key);
		ofy().save().entity(order).now();
		order = new Order("Mac Book Pro", 2000.0, 5, Calendar.getInstance().getTime(), 5L);
		key = Key.create(Customer.class, order.getCustomerId());
		order.setCustomerKey(key);
		ofy().save().entity(order).now();
		order = new Order("Mac Book Pro", 2000.0, 2, Calendar.getInstance().getTime(), 6L);
		key = Key.create(Customer.class, order.getCustomerId());
		order.setCustomerKey(key);
		ofy().save().entity(order).now();
		
		order = new Order("Iphone 6", 650.0, 2, Calendar.getInstance().getTime(), 1L);
		key = Key.create(Customer.class, order.getCustomerId());
		order.setCustomerKey(key);
		ofy().save().entity(order).now();
		order = new Order("Iphone 6", 650.0, 4, Calendar.getInstance().getTime(), 2L);
		key = Key.create(Customer.class, order.getCustomerId());
		order.setCustomerKey(key);
		ofy().save().entity(order).now();
		order = new Order("Iphone 6", 650.0, 6, Calendar.getInstance().getTime(), 3L);
		key = Key.create(Customer.class, order.getCustomerId());
		order.setCustomerKey(key);
		ofy().save().entity(order).now();
		order = new Order("Iphone 6", 650.0, 3, Calendar.getInstance().getTime(), 4L);
		key = Key.create(Customer.class, order.getCustomerId());
		order.setCustomerKey(key);
		ofy().save().entity(order).now();
	}

}
