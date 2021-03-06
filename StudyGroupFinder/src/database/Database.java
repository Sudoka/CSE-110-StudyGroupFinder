package database;

import java.util.ArrayList;
import java.util.Date;

import database.MySqlDatabase.InvalidDatabaseID;
import domainlogic.Status;
import domainlogic.User;

/** The database interface for the Study Group System. */
public interface Database {

	/**
	 * Adds a group to a database and returns the new group id
	 * @param groupname - the {@link String} of a unique groupname to add to database
	 * @return int for the id of the new group record
	 * <p> int will be -1 if the groupname already exists in database and no record
	 *     will be added</p>
	 * @see #addGroup
	 */
	public int createGroup(String groupname);
		
	/** Adds a Group to the database 
	 * Precondition: GroupData object is valid. All data object variables have been validated.
	 * Postcondition: The group will be added to the database
	 * @param gd the {@link GroupData} object to add.
	 * @return {@link Status} object that holds information on what happened.
	 */
	public Status addGroup(GroupData gd);
	
	/** Gets the specified GroupData from the database.
	 * Precondition: The group id is an integer.
	 * Postcondition: Gets the group data associated to the group id
	 * @param id the ID of the group to get.
	 * @return the c of the group.
	 */
	public GroupData getGroup(int id) throws InvalidDatabaseID;
	
	/** Adds the specified user to the specified group
	 * Preconditions: userid and groupid are integers.
	 * Postconditions: Adds the user associated to userid to the group associated with groupid
	 * @param userid the ID of the user to add.
	 * @param groupid the ID of the group to add to.
	 * @return {@link Status} object that holds information on what happened. 
	 * @throws InvalidDatabaseID -if requested id goes not exist in database
	 */
	public Status setMembershipUser(int userid, int groupid);
	
	/** Adds the specified user to the specified group as moderator
	 * Preconditions: userid and groupid are integers.
	 * Postconditions: Adds the user associated to userid to the group associated with groupid
	 * @param userid the ID of the user to add.
	 * @param groupid the ID of the group to add to.
	 * @return {@link Status} object that holds information on what happened. 
	 */
	public Status setMembershipMod(int userid, int groupid);
	
	/** Removes the specified user from the specified group.
	 * Preconditions: userid and groupid are integers
	 * Postconditions: Removes the user associated to userid to the group associated with groupid
	 * @param userid the ID of the user to remove.
	 * @param groupid the ID of the group to remove from.
	 * @return {@link Status} object that holds information on what happened.
	 */
	public Status setMembershipNone(int userid, int groupid);
	
	/** Gets user login info from database
	 * Preconditions: uname is an integer and pw is a String
	 * Postconditions: Logs-in the user associated with uname into the system
	 * @param uname the username of the user.
	 * @param pw the password of the user.
	 * @return a {@link User} object for the logged in, or failed, user.
	 */
	public User login(String uname, String pw);
	
	/**
	 * Adds a user to a database and returns the new user id
	 * @param username - the {@link String} of a unique username to add to database
	 * @return int for the id of the new user record
	 * <p> int will be -1 if the username already exists in database and no record
	 *     will be added</p>
	 * @see #addUser
	 */
	public int createUser(String username);
		
	/** Adds a User to the database 
	 * Preconditions: ud is a valid UserData object.
	 * Postconditions: Adds the user associated with ud to database
	 * @param ud the {@link UserData} object to add.
	 * @return {@link Status} object that holds information on what happened.
	 */
	public Status addUser(UserData ud);
	
	/** Updates a User in the database
	 * Preconditions: ud is a valid UserData object.
	 * Postconditions: Updates the user associated with ud in the database
	 * @param ud {@link UserData} object with updated information. 
	 * @return {@link Status} object that holds information on what happened.
	 */
	public Status updateUser(UserData ud);
	
	/** Delete a user from the database
	 * Precondition: The user id is an integer
	 * Postcondition: The user associated with the id no longer exists
	 * @param id the ID of the user to delete
	 * @return {@link Status} object that holds information on delete success 
	 */
	public Status deleteUser(int id);

	/** Deletes a Group
	 * Preconditions: groupID is an integer
	 * Postconditions: Deletes the group associated with groupID
	 * @param groupID an id for the group to delete
	 * @return {@link Status} object that holds information on what happened
	 */
	public Status deleteGroup(int groupID);
	
	/** Gets User Data Object for Specified ID
	 * Preconditions: id is an integer
	 * Postconditions: Gets the user data associated with the id
	 * @param id
	 * @return {@link UserData} object containing the user data associated with the user id.
	 * @throws InvalidDatabaseID 
	 */
	public UserData getUser(int id) throws InvalidDatabaseID;
	
	/** Updates Group Data in Database
	 * Preconditions: gd is a valid GroupData object
	 * Postconditions: Updates the group data for associated groupid in the database
	 * @param gd {@link GroupData} GroupData object
	 * @return {@link Status} object that holds information on what happened
	 * @throws InvalidDatabaseID -if requested id goes not exist in database
	 */
	public Status updateGroup(GroupData gd);
	
	/** Search for users and return multiple results
	 * @param criteria - a {@link SearchData} object containing search terms
	 * @return an {@link ArrayList} of {@link UserData} objects for matching users
	 */
	public ArrayList<UserData> searchUsers(SearchData criteria);
	
	/** Search for groups and return multiple results
	 * @param criteria - a {@link SearchData} object containing search terms
	 * @return an {@link ArrayList} of {@link GroupData} objects for matching groups
	 */
	public ArrayList<GroupData> searchGroups(SearchData criteria);
	
	public Status deleteInactiveUsers(Date d);
	
	public Status deleteInactiveGroups();
	
	/** Provide basic description of data implementation/connection
	 */
	public String toString();
}
