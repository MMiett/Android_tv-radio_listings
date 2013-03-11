package org.MMiett.tvlistings;



import java.util.ArrayList;

import android.util.Log;

/**
 *  This class contains all getter and setter methods to set and retrieve data.
 *  
 **/
public class XMLGettersSetters {

	private ArrayList<String> title = new ArrayList<String>();
	private ArrayList<String> desc = new ArrayList<String>();
	private ArrayList<String> flags = new ArrayList<String>();
	private ArrayList<String> start = new ArrayList<String>();
	private ArrayList<String> end = new ArrayList<String>();


	public ArrayList<String> getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc.add(desc);
		Log.i("This is the company:", desc);
	}

	public ArrayList<String> getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start.add(start);
		Log.i("This is the price:", start);
	}

	public ArrayList<String> getEnd() {
		return end;
	}

	public void setEnd(String End) {
		this.end.add(End);
		Log.i("This is the year:", End);
	}

	public ArrayList<String> getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title.add(title);
		Log.i("This is the title:", title);
	}

	public ArrayList<String> getFlags() {
		return flags;
	}

	public void setFlags(String Flags) {
		this.flags.add(Flags);
		Log.i("This is the artist:", Flags);
	}



}
