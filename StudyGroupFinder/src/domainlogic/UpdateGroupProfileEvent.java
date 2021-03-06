package domainlogic;

import database.Data;
import database.GroupData;

/** This Event is created when a mod or admin wants to update a group profile.
 * 
 * @author Michael Kirby
 *
 */
public class UpdateGroupProfileEvent implements Event {
	
	/** The {@link GroupData} object that this Event will use to complete its task. */
	private GroupData group;
	
	/** The {@link Status} object associated with the current state of this Event. */
	private Status status;
	
	/** The {@link StudyGroupSystem} that this Event uses. */
	private StudyGroupSystem system;
	
	/** Construct a UpdateGroupProfileEvent using a default status and null {@link GroupData} object */
	public UpdateGroupProfileEvent(StudyGroupSystem sgs) {
		group = null;
		status = new Status();
		system = sgs;
	}
	
	@Override
	public void execute() {
		if (status.getStatus() != StatusType.INVALID) {
			status = system.updateGroupData(group);
		}
	}

	@Override
	public void validate() {
		if (group.validate()) {
			status.setStatus(StatusType.VALID);
		} else {
			status.setStatus(StatusType.INVALID);
		}
	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public Data getData() {
		return group;
	}

	@Override
	public void setData(Data data) {
		group = (GroupData)data;
	}

}
