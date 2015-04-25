package com.customer.services;

import static com.customer.api.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.customer.common.dao.AbstractGenericDaoImpl;
import com.customer.common.dao.GenericDao;
import com.customer.entity.Address;
import com.customer.entity.Person;
import com.customer.exception.DatabaseException;
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

@Api(name = "personendpoint", namespace = @ApiNamespace(ownerDomain = "customer.com", ownerName = "customer.com", packagePath = "entity"))
public class PersonEndpoint {

	/**
	* Return a collection of persons
	*
	* @param count The number of persons
	* @return a list of Persons
	*/
	@ApiMethod(name = "listPerson")
	public CollectionResponse<Person> listPerson(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("count") Integer count) {

		Query<Person> query = ofy().load().type(Person.class);
		if (count != null)
			query.limit(count);
		if (cursorString != null && cursorString != "") {
			query = query.startAt(Cursor.fromWebSafeString(cursorString));
		}

		List<Person> records = new ArrayList<Person>();
		QueryResultIterator<Person> iterator = query.iterator();
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
		
		return CollectionResponse.<Person> builder().setItems(records)
				.setNextPageToken(cursorString).build();
	}
	
	/**
	* This inserts a new <code>Person</code> object.
	* @param person The object to be added.
	* @return The object to be added.
	*/
	@ApiMethod(name = "insertPerson")
	public Person insertPerson(Person person) throws ConflictException {
		// If if is not null, then check if it exists. If yes, throw an
		// Exception
		// that it is already present
		if (person.getId() != null) {
			
			if (findRecord(person.getId()) != null) {
				throw new ConflictException("Object already exists");
			}
		}
		// Since our @Id field is a Long, Objectify will generate a unique value
		// for us
		// when we use put
		GenericDao genericDao = new AbstractGenericDaoImpl();
		Address address;
		try {
			address = genericDao.getById(Address.class, person.getAddressId());
			if (address != null) {
				Key<Address> key = Key.create(address);
				person.setAddress(key);
			}
			genericDao.create(person);
			return person;
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			throw new ConflictException("Cannot create object", e.getCause());
		}
	}

	/**
	 * This updates an existing <code>Person</code> object.
	 * 
	 * @param person
	 *            The object to be added.
	 * @return The object to be updated.
	 */
	@ApiMethod(name = "updatePerson")
	public Person updatePerson(Person person) throws NotFoundException {
		if (findRecord(person.getId()) == null) {
			throw new NotFoundException("Person Record does not exist");
		}
		ofy().save().entity(person).now();
		return person;
	}

	/**
	 * This deletes an existing <code>Person</code> object.
	 * 
	 * @param id
	 *            The id of the object to be deleted.
	 */
	@ApiMethod(name = "removePerson")
	public void removePerson(@Named("id") Long id) throws NotFoundException {
		Person record = findRecord(id);
		if (record == null) {
			throw new NotFoundException("Person Record does not exist");
		}
		ofy().delete().entity(record).now();
	}

	// Private method to retrieve a <code>Person</code> record
	private Person findRecord(Long id) {
		Person person = ofy().load().type(Person.class).id(id).get();
		return person;
		// or return ofy().load().type(Person.class).filter("id",id).first.now();
	}

}
