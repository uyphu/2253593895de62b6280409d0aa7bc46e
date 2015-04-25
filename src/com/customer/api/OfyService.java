package com.customer.api;

import com.customer.entity.Address;
import com.customer.entity.Customer;
import com.customer.entity.Order;
import com.customer.entity.Person;
import com.customer.entity.State;
import com.customer.entity.UserLogin;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyService {

	static {
//		factory().register(Address.class);
//		factory().register(Person.class);
		ObjectifyService.register(Address.class);
		ObjectifyService.register(Person.class);
		ObjectifyService.register(Customer.class);
		ObjectifyService.register(Order.class);
		ObjectifyService.register(State.class);
		ObjectifyService.register(UserLogin.class);
	}

	public static Objectify ofy() {
		return ObjectifyService.ofy();
	}

	public static ObjectifyFactory factory() {
		return ObjectifyService.factory();
	}

}
