package com.customer.services;

import static com.customer.api.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.customer.entity.Address;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.Query;

@Api(name = "addressendpoint", namespace = @ApiNamespace(ownerDomain = "customer.com", ownerName = "customer.com", packagePath = "entity"))
public class AddressEndpoint {

	/**
	* Return a collection of addresss
	*
	* @param count The number of addresss
	* @return a list of Addresss
	*/
	@ApiMethod(name = "listAddress")
	public CollectionResponse<Address> listAddress(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("count") Integer count) {

		Query<Address> query = ofy().load().type(Address.class);
		if (count != null)
			query.limit(count);
		if (cursorString != null && cursorString != "") {
			query = query.startAt(Cursor.fromWebSafeString(cursorString));
		}

		List<Address> records = new ArrayList<Address>();
		QueryResultIterator<Address> iterator = query.iterator();
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
		return CollectionResponse.<Address> builder().setItems(records)
				.setNextPageToken(cursorString).build();
	}
	
	/**
	* This inserts a new <code>Address</code> object.
	* @param address The object to be added.
	* @return The object to be added.
	*/
	@ApiMethod(name = "insertAddress")
	public Address insertAddress(Address address) throws ConflictException {
		// If if is not null, then check if it exists. If yes, throw an
		// Exception
		// that it is already present
		if (address.getId() != null) {
			if (findRecord(address.getId()) != null) {
				throw new ConflictException("Object already exists");
			}
		}
		// Since our @Id field is a Long, Objectify will generate a unique value
		// for us
		// when we use put
		ofy().save().entity(address).now();
		return address;
	}

	/**
	 * This updates an existing <code>Address</code> object.
	 * 
	 * @param address
	 *            The object to be added.
	 * @return The object to be updated.
	 */
	@ApiMethod(name = "updateAddress")
	public Address updateAddress(Address address) throws NotFoundException {
		if (findRecord(address.getId()) == null) {
			throw new NotFoundException("Address Record does not exist");
		}
		ofy().save().entity(address).now();
		return address;
	}

	/**
	 * This deletes an existing <code>Address</code> object.
	 * 
	 * @param id
	 *            The id of the object to be deleted.
	 */
	@ApiMethod(name = "removeAddress")
	public void removeAddress(@Named("id") Long id) throws NotFoundException {
		Address record = findRecord(id);
		if (record == null) {
			throw new NotFoundException("Address Record does not exist");
		}
		ofy().delete().entity(record).now();
	}

	// Private method to retrieve a <code>Address</code> record
	private Address findRecord(Long id) {
		return ofy().load().type(Address.class).id(id).get();
		// or return ofy().load().type(Address.class).filter("id",id).first.now();
	}

}
