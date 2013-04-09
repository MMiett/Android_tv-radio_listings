package org.MMiett.tvlistings;



import java.util.ArrayList;

import android.util.Log;

/**
 * @author Mikko Miettinen 
 * @studentID S1306616
 * @email <MMIETT200@caledonian.ac.uk><mik.miettinen@gmail.com>
 * based on sourcecode and tutorial from http://mobile.tutsplus.com/tutorials/android/android-sdk-build-a-simple-sax-parser/
 */


/**
 *  This class contains all getter and setter methods to set and retrieve data.
 *  
 **/
public class XMLGettersSetters {
	
	//what tags you are searching/interested in the xml document
	private ArrayList<String> title = new ArrayList<String>();
	private ArrayList<String> desc = new ArrayList<String>();
	
	private ArrayList<String> start = new ArrayList<String>();
	private ArrayList<String> end = new ArrayList<String>();


	public ArrayList<String> getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc.add(desc);
		Log.i("This is the description:", desc);
	}

	public ArrayList<String> getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start.add(start);
		Log.i("This is the start time:", start);
	}

	public ArrayList<String> getEnd() {
		return end;
	}

	public void setEnd(String End) {
		this.end.add(End);
		Log.i("This is the end time:", End);
	}

	public ArrayList<String> getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title.add(title);
		Log.i("This is the title:", title);
	}




}
