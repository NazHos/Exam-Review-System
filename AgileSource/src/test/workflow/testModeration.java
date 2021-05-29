package test.workflow;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Exam;
import main.databaseManager;
import main.workflow.ExamList;
import main.workflow.ExamModeration;
import main.workflow.WorkflowExamSelection;

class testModeration {

	ExamModeration window;
	databaseManager db = new databaseManager();
	
	@BeforeEach
	void setup() {
		db.connect();
		ArrayList<Exam> exams = db.getExams(2030, -1, -1);
		ExamList el = new ExamList();
		el.setExList(exams);
		
		window = new ExamModeration(exams.get(0), el, db.getUser(999999));
	}
	
	@Test
	void testUpdateComments() {
		assertTrue(window.updateComments());		
	}

}
