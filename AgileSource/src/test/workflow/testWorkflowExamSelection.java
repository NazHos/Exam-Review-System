package test.workflow;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.User;
import main.workflow.WorkflowExamSelection;

class testWorkflowExamSelection extends WorkflowExamSelection {

	@Test
	void testListLoading() {
		assertTrue(this.list.getModel().getSize() > 0, "List is empty when it should contain exam data");
	}
	
	@Test
	void testLabelDetailsLoading()
	{
		User u = this.db.getUser(999999);
		assertTrue(this.lblStaffDetails.getText().equals(u.getRole().toString() + " - " + u.getName()), "User details label incorrect data");
	}
}
