package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Exam {

	public enum Stage
	{
		NEW,
		INTERNAL_MODERATION,
		VETTING_COMMITTEE,
		EXTERNAL_MODERATION,
		AWAITING_SIGNATURE,
		COMPLETE
	}
	
	public static int stageToInt(Stage stage)
	{
		switch(stage)
		{
		case NEW:
			return 0;
		case INTERNAL_MODERATION:
			return 1;
		case VETTING_COMMITTEE:
			return 2;
		case EXTERNAL_MODERATION:
			return 3;
		case AWAITING_SIGNATURE:
			return 4;
		case COMPLETE:
			return 5;
		}
		return -1;
	}
	
	int examID;
	String moduleName;
	String signedDate;
	int year;
	int semester;
	User staff;
	User internalMod;
	User externalMod;
	Stage stage;
	ArrayList<Exam_Document> exams = new ArrayList<Exam_Document>();
	ArrayList<Exam_Document> resits = new ArrayList<Exam_Document>();
	String lastUpdateDate;
	
	public Exam() {}
	
	public Exam(String moduleName, int year, int semester, User staff, User internalMod, User externalMod, Stage stage, String lastUpdate, int examID)
	{
		setModuleName(moduleName);
		setYear(year);
		setSemester(semester);
		setStaff(staff);
		setInternalMod(internalMod);
		setExternalMod(externalMod);
		setStage(stage);
		setLastUpdate(lastUpdate);
		setExamID(examID);
	}
	
	
	public void setLastUpdate(String date)
	{
		lastUpdateDate = date;
	}
	
	public String getLastUpdate()
	{
		return lastUpdateDate;
	}
	public String getCurrentDeadline()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try{
		   c.setTime(sdf.parse(getLastUpdate()));
		}catch(ParseException e){
			e.printStackTrace();
		 }
		databaseManager db = new databaseManager();
		db.connect();
		c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(db.getSetting("deadline")));
		db.close();		
		String newDate = sdf.format(c.getTime());
		return newDate;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getSignedDate() {
		return signedDate;
	}
	public void setSignedDate(String signedDate) {
		this.signedDate = signedDate;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public User getStaff() {
		return staff;
	}
	public void setStaff(User staff) {
		this.staff = staff;
	}
	public User getInternalMod() {
		return internalMod;
	}
	public void setInternalMod(User internalMod) {
		this.internalMod = internalMod;
	}
	public User getExternalMod() {
		return externalMod;
	}
	public void setExternalMod(User externalMod) {
		this.externalMod = externalMod;
	}
	public Stage getStage() {
		return stage;
	}
	public int getStageInt() {
		return stageToInt(getStage());
	}
		
	public void setStageInt(int stage) {
		switch(stage)
		{
		case 0:
			this.stage = Stage.NEW;
			break;
		case 1:
			this.stage = Stage.INTERNAL_MODERATION;
			break;
		case 2:
			this.stage = Stage.VETTING_COMMITTEE;
			break;
		case 3:
			this.stage = Stage.EXTERNAL_MODERATION;
			break;
		case 4:
			this.stage = Stage.AWAITING_SIGNATURE;
			break;
		case 5:
			this.stage = Stage.COMPLETE;
			break;
		}
	}
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	public ArrayList<Exam_Document> getExams() {
		return exams;
	}
	public void setExams(ArrayList<Exam_Document> exams) {
		this.exams = exams;
	}
	public ArrayList<Exam_Document> getResits() {
		return resits;
	}
	public void setResits(ArrayList<Exam_Document> resits) {
		this.resits = resits;
	}

	public int getExamID() {
		return examID;
	}

	public void setExamID(int examID) {
		this.examID = examID;
	}
}
