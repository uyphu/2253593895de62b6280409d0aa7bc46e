package com.customer.services;

import static com.customer.api.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.customer.common.AppConstants;
import com.customer.entity.UserLogin;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.Query;

@Api(name = "userloginendpoint", namespace = @ApiNamespace(ownerDomain = "customer.com", ownerName = "customer.com", packagePath = "entity"))
public class UserLoginEndpoint {

	/**
	* Return a collection of userLogins
	*
	* @param count The number of userLogins
	* @return a list of UserLogins
	*/
	@ApiMethod(name = "listUserLogin")
	public CollectionResponse<UserLogin> listUserLogin(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("count") Integer count) {
		
		Query<UserLogin> query = ofy().load().type(UserLogin.class);
		if (count != null)
			query.limit(count);
		if (cursorString != null && cursorString != "") {
			query = query.startAt(Cursor.fromWebSafeString(cursorString));
		}

		List<UserLogin> records = new ArrayList<UserLogin>();
		QueryResultIterator<UserLogin> iterator = query.iterator();
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
		return CollectionResponse.<UserLogin> builder().setItems(records)
				.setNextPageToken(cursorString).build();
	}
	
	/**
	* This inserts a new <code>UserLogin</code> object.
	* @param userLogin The object to be added.
	* @return The object to be added.
	*/
	@ApiMethod(name = "insertUserLogin")
	public UserLogin insertUserLogin(UserLogin userLogin) throws ConflictException {
		// If if is not null, then check if it exists. If yes, throw an
		// Exception
		// that it is already present
		if (userLogin.getId() != null) {
			if (findRecord(userLogin.getId()) != null) {
				throw new ConflictException("Object already exists");
			}
		}
		// Since our @Id field is a Long, Objectify will generate a unique value
		// for us
		// when we use put
		ofy().save().entity(userLogin).now();
		//initData();
		return userLogin;
	}

	/**
	 * This updates an existing <code>UserLogin</code> object.
	 * 
	 * @param userLogin
	 *            The object to be added.
	 * @return The object to be updated.
	 */
	@ApiMethod(name = "updateUserLogin")
	public UserLogin updateUserLogin(UserLogin userLogin) throws NotFoundException {
		if (findRecord(userLogin.getId()) == null) {
			throw new NotFoundException("UserLogin Record does not exist");
		}
		ofy().save().entity(userLogin).now();
		return userLogin;
	}

	/**
	 * This deletes an existing <code>UserLogin</code> object.
	 * 
	 * @param id
	 *            The id of the object to be deleted.
	 */
	@ApiMethod(name = "removeUserLogin")
	public void removeUserLogin(@Named("id") Long id) throws NotFoundException {
		UserLogin record = findRecord(id);
		if (record == null) {
			throw new NotFoundException("UserLogin Record does not exist");
		}
		ofy().delete().entity(record).now();
	}
	
	/**
	 * Gets the user login.
	 *
	 * @param id the id
	 * @return the user login
	 */
	@ApiMethod(name = "getUserLogin")
	public UserLogin getUserLogin(@Named("id") Long id) {
		return findRecord(id);
	}
	
	/**
	 * Login.
	 *
	 * @param userLogin the user login
	 * @return the user login
	 */
	@ApiMethod(name = "login")
	public UserLogin login(UserLogin userLogin) {
		List<UserLogin> list = ofy().load().type(UserLogin.class).filter("userName = ", userLogin.getUserName()).list();
		if (list != null && list.size() > 0) {
			for (UserLogin user : list) {
				if (user.getPassword().equals(userLogin.getPassword())) {
					return user;
				}
			}
		} 
		return null;
	}
	
	/**
	 * Logout.
	 *
	 * @param userLogin the user login
	 * @return the user login
	 */
	@ApiMethod(name = "logout")
	public UserLogin logout(UserLogin userLogin) {
		//FIXME Phu gonna do sth here
		userLogin.setStatus(AppConstants.OFFLINE_STATUS);
		return userLogin;
	}
	
	// Private method to retrieve a <code>UserLogin</code> record
	/**
	 * Find record.
	 *
	 * @param id the id
	 * @return the user login
	 */
	private UserLogin findRecord(Long id) {
		return ofy().load().type(UserLogin.class).id(id).get();
		// or return ofy().load().type(UserLogin.class).filter("id",id).first.now();
	}
	
	/**
	 * Inits the data.
	 */
	@ApiMethod(name = "initData")
	public void initData() {
		UserLogin userLogin;
		userLogin = new UserLogin(1L, "admin@yahoo.com","123456"); ofy().save().entity(userLogin).now();
		userLogin = new UserLogin(2L, "super@yahoo.com","123456"); ofy().save().entity(userLogin).now();
		userLogin = new UserLogin(3L, "test@yahoo.com","123456"); ofy().save().entity(userLogin).now();
		userLogin = new UserLogin(4L, "mod@yahoo.com","123456"); ofy().save().entity(userLogin).now();
		userLogin = new UserLogin(5L, "uyphu@yahoo.com","123456"); ofy().save().entity(userLogin).now();
		
	}

}
