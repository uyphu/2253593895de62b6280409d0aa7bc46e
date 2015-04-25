package com.customer.services;

import static com.customer.api.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.customer.entity.State;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.Query;

@Api(name = "stateendpoint", namespace = @ApiNamespace(ownerDomain = "customer.com", ownerName = "customer.com", packagePath = "entity"))
public class StateEndpoint {

	/**
	* Return a collection of states
	*
	* @param count The number of states
	* @return a list of States
	*/
	@ApiMethod(name = "listState")
	public CollectionResponse<State> listState(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("count") Integer count) {

		Query<State> query = ofy().load().type(State.class);
		if (count != null)
			query.limit(count);
		if (cursorString != null && cursorString != "") {
			query = query.startAt(Cursor.fromWebSafeString(cursorString));
		}

		List<State> records = new ArrayList<State>();
		QueryResultIterator<State> iterator = query.iterator();
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
		return CollectionResponse.<State> builder().setItems(records)
				.setNextPageToken(cursorString).build();
	}
	
	/**
	* This inserts a new <code>State</code> object.
	* @param state The object to be added.
	* @return The object to be added.
	*/
	@ApiMethod(name = "insertState")
	public State insertState(State state) throws ConflictException {
		// If if is not null, then check if it exists. If yes, throw an
		// Exception
		// that it is already present
		if (state.getId() != null) {
			if (findRecord(state.getId()) != null) {
				throw new ConflictException("Object already exists");
			}
		}
		// Since our @Id field is a Long, Objectify will generate a unique value
		// for us
		// when we use put
		ofy().save().entity(state).now();
		return state;
	}

	/**
	 * This updates an existing <code>State</code> object.
	 * 
	 * @param state
	 *            The object to be added.
	 * @return The object to be updated.
	 */
	@ApiMethod(name = "updateState")
	public State updateState(State state) throws NotFoundException {
		if (findRecord(state.getId()) == null) {
			throw new NotFoundException("State Record does not exist");
		}
		ofy().save().entity(state).now();
		return state;
	}

	/**
	 * This deletes an existing <code>State</code> object.
	 * 
	 * @param id
	 *            The id of the object to be deleted.
	 */
	@ApiMethod(name = "removeState")
	public void removeState(@Named("id") Long id) throws NotFoundException {
		State record = findRecord(id);
		if (record == null) {
			throw new NotFoundException("State Record does not exist");
		}
		ofy().delete().entity(record).now();
	}
	
	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getState")
	public State getState(@Named("id") Long id) {
		return findRecord(id);
	}
	
	// Private method to retrieve a <code>State</code> record
	private State findRecord(Long id) {
		return ofy().load().type(State.class).id(id).get();
	}
	
	/**
	 * Inits the data.
	 */
	@ApiMethod(name = "initData")
	public void initData() {
		State state;
		state = new State(1L,"AL","Alabama"); ofy().save().entity(state).now();
		state = new State(2L,"AK","Alaska"); ofy().save().entity(state).now();
		state = new State(3L,"AZ","Arizona"); ofy().save().entity(state).now();
		state = new State(4L,"AR","Arkansas"); ofy().save().entity(state).now();
		state = new State(5L,"CA","California"); ofy().save().entity(state).now();
		state = new State(6L,"CO","Colorado"); ofy().save().entity(state).now();
		state = new State(7L,"CT","Connecticut"); ofy().save().entity(state).now();
		state = new State(8L,"DE","Delaware"); ofy().save().entity(state).now();
		state = new State(9L,"FL","Florida"); ofy().save().entity(state).now();
		state = new State(10L,"GA","Georgia"); ofy().save().entity(state).now();
		state = new State(11L,"HI","Hawaii"); ofy().save().entity(state).now();
		state = new State(12L,"ID","Idaho"); ofy().save().entity(state).now();
		state = new State(13L,"IL","Illinois"); ofy().save().entity(state).now();
		state = new State(14L,"IN","Indiana"); ofy().save().entity(state).now();
		state = new State(15L,"IA","Iowa"); ofy().save().entity(state).now();
		state = new State(16L,"KS","Kansas"); ofy().save().entity(state).now();
		state = new State(17L,"KY","Kentucky"); ofy().save().entity(state).now();
		state = new State(18L,"LA","Louisiana"); ofy().save().entity(state).now();
		state = new State(19L,"ME","Maine"); ofy().save().entity(state).now();
		state = new State(20L,"MD","Maryland"); ofy().save().entity(state).now();
		state = new State(21L,"MA","Massachusetts"); ofy().save().entity(state).now();
		state = new State(22L,"MI","Michigan"); ofy().save().entity(state).now();
		state = new State(23L,"MN","Minnesota"); ofy().save().entity(state).now();
		state = new State(24L,"MS","Mississippi"); ofy().save().entity(state).now();
		state = new State(25L,"MO","Missouri"); ofy().save().entity(state).now();
		state = new State(26L,"MT","Montana"); ofy().save().entity(state).now();
		state = new State(27L,"NE","Nebraska"); ofy().save().entity(state).now();
		state = new State(28L,"NV","Nevada"); ofy().save().entity(state).now();
		state = new State(29L,"NH","New Hampshire"); ofy().save().entity(state).now();
		state = new State(30L,"NJ","New Jersey"); ofy().save().entity(state).now();
		state = new State(31L,"NM","New Mexico"); ofy().save().entity(state).now();
		state = new State(32L,"NY","New York"); ofy().save().entity(state).now();
		state = new State(33L,"NC","North Carolina"); ofy().save().entity(state).now();
		state = new State(34L,"ND","North Dakota"); ofy().save().entity(state).now();
		state = new State(35L,"OH","Ohio"); ofy().save().entity(state).now();
		state = new State(36L,"OK","Oklahoma"); ofy().save().entity(state).now();
		state = new State(37L,"OR","Oregon"); ofy().save().entity(state).now();
		state = new State(38L,"PA","Pennsylvania"); ofy().save().entity(state).now();
		state = new State(39L,"RI","Rhode Island"); ofy().save().entity(state).now();
		state = new State(40L,"SC","South Carolina"); ofy().save().entity(state).now();
		state = new State(41L,"SD","South Dakota"); ofy().save().entity(state).now();
		state = new State(42L,"TN","Tennessee"); ofy().save().entity(state).now();
		state = new State(43L,"TX","Texas"); ofy().save().entity(state).now();
		state = new State(44L,"UT","Utah"); ofy().save().entity(state).now();
		state = new State(45L,"VT","Vermont"); ofy().save().entity(state).now();
		state = new State(46L,"VA","Virginia"); ofy().save().entity(state).now();
		state = new State(47L,"WA","Washington"); ofy().save().entity(state).now();
		state = new State(48L,"WV","West Virginia"); ofy().save().entity(state).now();
		state = new State(49L,"WI","Wisconsin"); ofy().save().entity(state).now();
		state = new State(50L,"WY","Wyoming"); ofy().save().entity(state).now();

	}


}
