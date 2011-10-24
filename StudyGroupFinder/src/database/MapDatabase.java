package database;
import domainlogic.Status;
import domainlogic.StatusType;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import util.StringParser;

import domainlogic.User;
import domainlogic.User.Logged;

public class MapDatabase implements Database {

	private Map<Integer, Vector<String>> users;
	private Map<Integer, Vector<String>> groups;
	private int userIdCounter;
	private int groupIdCounter;
	
	public MapDatabase() {
		users = new HashMap<Integer, Vector<String>>();
		groups = new HashMap<Integer, Vector<String>>();
		userIdCounter =0;
		populateUsers();
		populateGroups();
	}
	
	/* Helper Method to Populate Dummy User database */
	private void populateUsers() {
		// User Name	//PW	//Moderator of group	// user ID
		//ID uname Password   List of groups to which the user is mod, unique user id
		int uniqueId = getUniqueUserId();
		String userIdString = Integer.toString(uniqueId);
		users.put(uniqueId, addData("Mike","pw","1~",userIdString));
		
		uniqueId = getUniqueUserId();
		userIdString = Integer.toString(uniqueId);
		users.put(uniqueId, addData("Bob","pw","2~", userIdString));
	}
	
	
	/* Helper Method to Populate Dummy Groups database */
	private void populateGroups() {
		//ID  //Name  //Class studied  //List of mod users // List of Users // Group ID
		int uniqueGroupId = getUniqueGroupId();
		String groupIdString = Integer.toString(uniqueGroupId);
		groups.put(uniqueGroupId, addData("The Group","CSE 110","1~", "1~", groupIdString));
		
		uniqueGroupId = getUniqueGroupId();
		groupIdString = Integer.toString(uniqueGroupId);
		groups.put(uniqueGroupId, addData("Bobs Group","CSE 101","1~", "1~", groupIdString));
	}
	
	/* Helper Method For Populating database */
	private Vector<String> addData(String... info) {
		Vector<String> temp = new Vector<String>();
		for (String i : info) {
			temp.add(i);
		}
		return temp;
	}
	/* Create a unique user id for key of hash map*/
	private int getUniqueUserId(){
		this.userIdCounter += 1;
		return userIdCounter;
	}
	
	/* Create a unique group id for key of hash map*/
	private int getUniqueGroupId(){
		this.groupIdCounter += 1;
		return groupIdCounter;
	}
	
	/**
	 * Adds a group to the database
	 * @return Status Object
	 */
	public Status addGroup(GroupData gd) {
		Status tempStatus = new Status();
		//get UserID
		int uID = gd.getMods().get(0);
		//Generate unique Group ID and convert it to a string
		int uniqueGroupId = getUniqueGroupId();
		String groupIdString = Integer.toString(uniqueGroupId);
		groups.put(uniqueGroupId, addData(gd.getName(), gd.getCourse(), StringParser.unParseArray(gd.getMods()) ,"1~", groupIdString));
		//Update user profile
		UserData tempUD = getUser(uID);
		tempUD.setMod(uID);
		updateUser(tempUD);
		tempStatus.setStatus(StatusType.SUCCESS);
		return tempStatus;
	}

	/**
	 * Gets Group Data from Database
	 * @param group id
	 * @return GroupData Object
	 */
	public GroupData getGroup(int id) {
		if (groups.containsKey(id)) {
			Vector<String> temp = groups.get(id);
			GroupData found = new GroupData(id, temp.get(0), temp.get(1), temp.get(2), temp.get(3));
			return found;
		}
		return null;
	}

	@Override
	public Status addUserToGroup(int userid, int groupid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status removeUserFromGroup(int userid, int groupid) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * User Login
	 * @param String User Name
	 * @param String Password
	 * @return User Object
	 */
	public User login(String uname, String pw) {
		// TODO This is really bad but will work for now
		User user = new User(Logged.LOGGEDOFF, null);
		for(int i=1; i<=users.size();i++) {
			if (users.get(i).get(0).equals(uname) && users.get(i).get(1).equals(pw)) {
				user.setUserData(new UserData(i,users.get(i).get(0),users.get(i).get(1),users.get(i).get(2)));
				user.setStatus(Logged.USER);
				return user;
			}
		}
		user.setStatus(Logged.INVALID);
		return user;
	}

	/**
	 * Add User to Databalse
	 * @param UserData Object
	 * @return Status Object
	 */
	public Status addUser(UserData ud) {
		Status tempStatus = new Status(StatusType.UNSUCCESSFUL);
		int uniqueID = getUniqueUserId();
		String uniqueStringID = Integer.toString(uniqueID);
		users.put(uniqueID, addData(ud.getUName(), ud.getPW(), "~", uniqueStringID));
		tempStatus.setStatus(StatusType.SUCCESS);
		return tempStatus;
	}

	/**
	 * Update user in database
	 * @param UserData Object
	 * @return Status Object
	 */
	public Status updateUser(UserData ud) {
		Status tempStatus = new Status(StatusType.UNSUCCESSFUL);
		String idString = Integer.toString(ud.getId());
		users.put(ud.getId(), addData(ud.getUName(), ud.getPW(),StringParser.unParseArray(ud.getModOf()), idString));
		tempStatus.setStatus(StatusType.SUCCESS);
		return tempStatus;
	}

	public UserData getUser(int id){
		Vector<String> temp = users.get(id);
		UserData tempUD = new UserData(id,temp.get(0), temp.get(1), temp.get(2));
		return tempUD;
	}
	
	
	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub

	}
	
	private void arrayListToString() {
		// TODO Auto-generated method stub

	}

}
