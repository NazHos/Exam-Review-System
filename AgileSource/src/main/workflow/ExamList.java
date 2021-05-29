package main.workflow;

import java.util.*;
import main.*;

// Contains list of exams a user has access to, and methods for manipulating it, exams are currently stored as their ID
// populated by db pull -to do

public class ExamList {
	
	public ArrayList<Exam> exList;
	public databaseManager dbm = new databaseManager();

	public ExamList() {
		dbm.connect();
		this.exList = dbm.getExams(-1, -1, -1); // all exams?
	}
	
	public ArrayList<Exam> getExList() {
		return exList;
	}

	public void setExList(ArrayList<Exam> exList) {
		this.exList = exList;
	}
	
	public void addToList(Exam e) {
		exList.add(e);
	}
	
	public void addExamDB(Exam e) {
		dbm.addNewExam(e);
	}
}
