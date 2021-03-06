package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import util.StringParser;

import database.UserData;
import domainlogic.Status;
import domainlogic.StatusType;
import domainlogic.UpdateUserProfileEvent;

/** A JDialog for updating the users profile. This is displayed when the user selects
 * My Profile.
 */
public class UpdateUserDialog extends JDialog implements ActionListener, PropertyChangeListener {

	/** The {@link GUIFrame} that is displaying this UpdateUserDialog */
	GUIFrame parent;
	
	/** The {@link UpdateUserProfileEvent} used by this UpdateUserDialog */
	UpdateUserProfileEvent event;
	
	/** The JTextField for the username. */
	JTextField unameField;
	
	/** The JTextField for the password */
	JTextField pwField;
	
	/** The JTextField for the courses*/
	JTextField courseField;
	
	/** Since this is an update, this is the UserData as it was originally for prepopulating. */
	UserData prepop;
	
	/** Constructs this UpdateUserDialog object using the {@link GUIFrame} that will display it.
	 * 
	 * @param parent the {@link GUIFrame} that will display this UpdateUserDialog.
	 */
	public UpdateUserDialog(GUIFrame parent, int user) {
		super(parent, true);
		
		//Give this Dialog a reference to its GUIFrame parent
		this.parent = parent;
		
		//Set the size, position, and title of this dialog
		setTitle("Update My Profile");
		setSize(400,225);
		setResizable(false);
		setLocationRelativeTo(null);
		
		event = new UpdateUserProfileEvent(parent.getSGS());
		
		unameField = new JTextField(25);
		pwField = new JTextField(25);
		courseField = new JTextField(25);

		prepop = parent.getSGS().getUser(user);
		unameField.setText(prepop.getUName());
		pwField.setText(prepop.getPW());
		courseField.setText(prepop.getCourses());
		
		//Create an array of the text and components to be displayed.
		JLabel msgString1 = new JLabel("Username: ");
		JLabel msgString2 = new JLabel("Password: ");
		JLabel msgString3 = new JLabel("My Courses: (seperated by comma)");
		msgString1.setForeground(parent.getTheme().headColor());
		msgString2.setForeground(parent.getTheme().headColor());
		msgString3.setForeground(parent.getTheme().headColor());
		Object[] array = {msgString1, unameField, msgString2, pwField, msgString3, courseField};

		//Create an array specifying the number of dialog buttons
		//and their text.
		JButton submit = new JButton("Submit");
		submit.setActionCommand("submit");
		JButton cancel = new JButton("Cancel");
		cancel.setActionCommand("cancel");
		submit.addActionListener(this);
		cancel.addActionListener(this);
		Object[] options = {submit, cancel};

		UIManager.put("OptionPane.background", new Color(0,0,0,0));
	    UIManager.put("Panel.background", new Color(0,0,0,0));
	    
		//Create the JOptionPane.
		JOptionPane optionPane = new JOptionPane(array,
		      JOptionPane.QUESTION_MESSAGE,
		      JOptionPane.YES_NO_OPTION,
		      parent.getTheme().getIcon(),
		      options,
		      options[0]);
		
		//Make this dialog display it.
		BGPanel panel = new BGPanel(parent.getTheme());
		panel.add(optionPane);
		setContentPane(panel);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("submit".equals(e.getActionCommand())) { //TODO this needs to update the User object for the session
			event.setData(new UserData(prepop.getId(),unameField.getText(),pwField.getText(), StringParser.unParseArray(prepop.getModOf()),StringParser.unParseArray(prepop.getUserOf()), courseField.getText()));
			event.validate();
			event.execute(); //TODO ADD courses also
			Status result = event.getStatus();
			if (result.getStatus() == StatusType.SUCCESS) {
				System.out.println("Worked");
			}
			StatusDialog sd = new StatusDialog(result, parent);
			sd.setVisible(true);
			setVisible(false);
			parent.getSGS().refreshLoggedUser();
			parent.getGUI().refreshLeft();
		} else if ("cancel".equals(e.getActionCommand())) {
			setVisible(false);
		}
	}

}
