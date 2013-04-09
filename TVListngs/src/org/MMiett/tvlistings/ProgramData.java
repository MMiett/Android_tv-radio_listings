package org.MMiett.tvlistings;
/**
 * @author Mikko Miettinen 
 * @studentID S1306616
 * @email <MMIETT200@caledonian.ac.uk><mik.miettinen@gmail.com>
 * ProgramnData.java is just a data structure that i use to store all that 
 * xml data from web service so i don't loose it too soon.
 */
public class ProgramData {
	private String Title;
	private String Desc;
	private String startTime;
	private String endTime;
	private int viewId;
	public ProgramData( String t, String d, String s, String e, int id){
		Title =t;
		Desc =d;
		startTime = s;
		endTime=e;
		viewId= id;
		
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDesc() {
		return Desc;
	}

	public void setDesc(String desc) {
		Desc = desc;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getViewId() {
		return viewId;
	}

	public void setViewId(int viewId) {
		this.viewId = viewId;
	}

}
