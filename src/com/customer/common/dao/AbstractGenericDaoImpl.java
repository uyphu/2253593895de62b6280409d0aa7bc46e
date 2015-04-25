package com.customer.common.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.customer.entity.Address;
import com.customer.entity.Customer;
import com.customer.entity.Order;
import com.customer.entity.Person;
import com.customer.entity.State;
import com.customer.entity.UserLogin;
import com.customer.exception.DatabaseException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;


public class AbstractGenericDaoImpl implements GenericDao{

	static {
	    ObjectifyService.register(Person.class);
	    ObjectifyService.register(Address.class);
	    ObjectifyService.register(Customer.class);
		ObjectifyService.register(Order.class);
		ObjectifyService.register(State.class);
		ObjectifyService.register(UserLogin.class);
	}

	@Override
	public <T> void create(T t) {
	    ofy().save().entity(t).now();
	}

	@Override
	public <T> String createWithKey(T t) {
	    Key<T> key =  ofy().save().entity(t).now();
	    return key.getString();
	}

	@Override
	public <T> Long createWithID(T t) {
	    Key<T> key =  ofy().save().entity(t).now();
	    return key.getId();
	}

	@Override
	public <T> void update(Class<T> clazz, Long id, T t) throws DatabaseException {
	    if (id == null) {
	        throw new DatabaseException("ID cannot be null");
	    }
	    T tnew = ofy().load().type(clazz).id(id).get();
	    ofy().save().entity(tnew).now();
	}

	@Override
	public <T> void update(Class<T> clazz, String key, T t) throws DatabaseException {
	    if (key == null) {
	        throw new DatabaseException("ID cannot be null");
	    }
	    T tnew = ofy().load().type(clazz).id(key).get();
	    ofy().save().entity(tnew).now();

	}

	@Override
	public <T> T getById(Class<T> clazz, Long id) throws DatabaseException {
	    if (id == null) {
	        throw new DatabaseException("ID cannot be null");
	    }
	    return ofy().load().type(clazz).id(id).get();
	}
	
	@Override
	public <T> T getByKey(Class<T> clazz, String key) throws DatabaseException {
	    if (key == null) {
	        throw new DatabaseException("ID cannot be null");
	    }
	    return ofy().load().type(clazz).id(key).get();
	}

	@Override
	public <T> List<T> list(Class<T> clazz) {
	    List<T> list = ofy().load().type(clazz).list();
	    return list;
	}
	
	@Override
	public <T> void delete(Class<T> clazz, Long id) throws DatabaseException {
	    if (id == null) {
	        throw new DatabaseException("ID cannot be null");
	    }
	    T t = ofy().load().type(clazz).id(id).get();
	    if(t != null){
	        ofy().delete().entity(t).now();
	    }
	}

	@Override
	public <T> void deleteByKey(Class<T> clazz, String key) throws DatabaseException {
	    if (key == null) {
	        throw new DatabaseException("ID cannot be null");
	    }
	    T t = ofy().load().type(clazz).id(key).get();
	    if(t != null){
	        ofy().delete().entity(t).now();
	    }
	}
	
}
