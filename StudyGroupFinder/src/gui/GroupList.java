package gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.GroupData;
import database.UserData;

public class GroupList extends JList {
	
	/** The parent {@link GUIFrame} of this JList */
	private GUIFrame parent;
	
	/** An ArrayList of GroupData for this List to display as groups. */
	private ArrayList<GroupData> groups;
	
	private ListSelectionListener ug;
	
	/** Construct a GroupList using its GUIFrame and an ArrayList of GroupData.
	 * 
	 * @param parent the GUIFrame of the program.
	 * @param groups an ArrayList of GroupData.
	 */
	public GroupList(GUIFrame parent, ArrayList<GroupData> groups) {
		this.parent = parent;
		this.groups = groups;
	}
	
	/** Construct a GroupList using its GUIFrame and an array of Objects that are ids.
	 * 
	 * @param parent the GUIFrame of the program.
	 * @param userids and array of objects of the ids of the groups for this list.
	 */
	public GroupList(GUIFrame parent, ListSelectionListener ug, Object[] groupids) {
		this.parent = parent;
		this.ug = ug;
		groups = new ArrayList<GroupData>();
		if (groupids.length != 0) {
			for (Object i : groupids) {
				groups.add(parent.getSGS().getGroup((Integer)i));
			}
		}
		Vector<Object> options =  new Vector<Object>();
		for (GroupData gd : groups) {
			options.add(gd.getName()+" - "+gd.getCourse());
		}
		this.setListData(options);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addListSelectionListener(ug);
	}
	
	public GroupData getSelectedData() {
		int temp = this.getSelectedIndex();
		if (temp >= 0) {
			return groups.get(temp);
		} else {
			return null;
		}
	}

}
