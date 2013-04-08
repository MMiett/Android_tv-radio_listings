package org.MMiett.tvlistings;


import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * @author Mikko Miettinen 
 * @studentID S1306616
 * @email <MMIETT200@caledonian.ac.uk><mik.miettinen@gmail.com>
 * SaxParser parts are based on example found here http://mobile.tutsplus.com/tutorials/android/android-sdk-build-a-simple-sax-parser/ 
 *  
 */
public class MainActivity extends Activity {
	final Context alertContext = this;
	
	ConnectivityManager connectivityManager; //for connectivity related checks
	XMLGettersSetters data; // processed xml data class
	private Spinner channelChooser;
	private Button dateButton,prevButton,nextButton; //day choosing buttons
    //private String tvListingURL = "http://bleb.org/tv/data/listings/0/bbc1_hd.xml";
	//rss.php?ch=bbc1_scotland&day=0";
    private String tvListingBaseURL = "http://bleb.org/tv/data/listings/";
    Time today = new Time(Time.getCurrentTimezone());
	
    String cDate = ""; //string container for date
    int dayIndexOffset=0; //integer to keep track day index
    int dayIndexOffMin = -1; //minimum limit for day offset index
    int dayIndexOffMax = 5; //maximum limit for day offset index
    int iCurrentChannel;//integer to keep track of current channel index
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//initializing connectivity manager so we can check the connectivity and active networking
		connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		if(activeNetworkInfo != null && activeNetworkInfo.isConnected()) //we have internet access 
		{

		today.setToNow(); //time variable to get date
		cDate = Integer.toString(today.monthDay + dayIndexOffset) +"/" + Integer.toString(today.month) +"/"+ Integer.toString(today.year); 
		

		super.onCreate(savedInstanceState);
		 setContentView(R.layout.main);
		
		 channelChooser = (Spinner)findViewById(R.id.ChannelSpinner);
		 /**
		  * sets on item selected listener to channel spinner and current channel position in it
		  */
		 iCurrentChannel = channelChooser.getSelectedItemPosition();
		 channelChooser.setOnItemSelectedListener(new OnItemSelectedListener() {
			    @Override
			    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			    	if (iCurrentChannel != position){
			    		wipeDataViews();
			    		//lets load the default channel at app start
						data = LoadList(tvListingBaseURL+ dayIndexOffset +"/"+channelChooser.getItemAtPosition(position)+".xml").data;
						placeData();
			    }
			    iCurrentChannel = position;
			    
			    }

			    @Override
			    public void onNothingSelected(AdapterView<?> parentView) {
			        return;
			    }

			}); //initialize buttons and disable the datebutton to make it unclickable
		 dateButton = (Button)findViewById(R.id.date_button);
		 dateButton.setEnabled(false);
		 dateButton.setText(cDate);
		 prevButton = (Button)findViewById(R.id.prev_button);
		 nextButton = (Button)findViewById(R.id.next_button);
			 /**
			  *  lets attach onClickListeners
			  */	
			prevButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dayIndexOffset--;
					if(dayIndexOffset == dayIndexOffMin){
						prevButton.setEnabled(false);
						} 
					else{
						prevButton.setEnabled(true);
						nextButton.setEnabled(true);
						}
					cDate = Integer.toString(today.monthDay + dayIndexOffset) +"/" + Integer.toString(today.month) +"/"+ Integer.toString(today.year); 
					dateButton.setText(cDate);
					wipeDataViews();
					data = LoadList(tvListingBaseURL+ dayIndexOffset+"/"+channelChooser.getItemAtPosition( iCurrentChannel)+".xml").data;
					placeData();
					 
				}
			});
			nextButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dayIndexOffset++;
					if(dayIndexOffset == dayIndexOffMax){
						nextButton.setEnabled(false);
						}
					else{
						nextButton.setEnabled(true);
						prevButton.setEnabled(true);
					}
					cDate = Integer.toString(today.monthDay + dayIndexOffset) +"/" + Integer.toString(today.month) +"/"+ Integer.toString(today.year); 
					dateButton.setText(cDate);
					wipeDataViews();
					data = LoadList(tvListingBaseURL+ dayIndexOffset+"/"+channelChooser.getItemAtPosition( iCurrentChannel)+".xml").data;
					placeData();
					
				}
			});
			//lets load the default channel at app start
			data = LoadList(tvListingBaseURL+ dayIndexOffset+"/"+channelChooser.getItemAtPosition( iCurrentChannel)+".xml").data;
			placeData();
		}
		else{ //we don't have internet access so lets throw alert for  the user and close the activity
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(alertContext);
	 
				// set title
				alertDialogBuilder.setTitle("No internet connection available");
			
			alertDialogBuilder
			.setMessage("This application needs internet connection to work, please connect to internet and try again if you want to proceed")
			.setCancelable(false)
			.setPositiveButton("Close",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, close
					// current activity
					MainActivity.this.finish();
				}
			  });
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		}
	
	}
	
	/**
	 * method to quickly wipe the unwanted views from contextLayout element
	 */
	public void wipeDataViews(){
		 View layout = findViewById(R.id.contextLayout);
		 ((ViewGroup)layout).removeAllViewsInLayout();
	}
	
	/**
	 * method to place programme data to context layout nicely
	 */
	public void placeData() {
		 View layout = findViewById(R.id.contextLayout);
		 /** 
		 * Create TextView Arrays to add the retrieved data to.
		 **/
	 	LinearLayout container[];
		TextView title[];		
		TextView end[];
		TextView start[];
		TextView desc[];
		/**
		 * array of linearlayouts for containing info of each 
		 * programme, using for example as many as there is titles
		 */
		container= new LinearLayout[data.getTitle().size()]; 
		
		
		/** 
		 * Makes the TextView arrays correct sized, based on how many xml items there were
		 **/
		title = new TextView[data.getTitle().size()];
		start = new TextView[data.getStart().size()];
		end = new TextView[data.getEnd().size()];			
		desc = new TextView[data.getDesc().size()];
		


		/** 
		 * Run a for loop to set All the TextViews with text to corresponding container until 
		 * the size of the array is reached.
		 * 
		 **/
		for (int i = 0; i < data.getTitle().size(); i++) {
			
			title[i] = new TextView(this);
			title[i].setText(data.getTitle().get(i));
			
			end[i] = new TextView(this);
			end[i].setText("ending time = "+data.getEnd().get(i));
			
			start[i] = new TextView(this);
			start[i].setText("starting time = "+data.getStart().get(i));
			
			desc[i] = new TextView(this);
			desc[i].setText("Description = "+data.getDesc().get(i));
			
			/**
			 * setting up container style and other parameters
			 **/
			container[i]= new LinearLayout(this);
			container[i].setOrientation(1); //1 = vertical and 0 = horizontal
			container[i].setPadding(10, 10, 10, 10);
			//container[i].setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
			container[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_borders));
	
			/**
			 * adding one container to contextlayout and after that 
			 * adding corresponding textviews to said container
			 **/
			((ViewGroup) layout).addView(container[i]);
			((ViewGroup) container[i]).addView(title[i]);
			((ViewGroup) container[i]).addView(start[i]);
			((ViewGroup) container[i]).addView(end[i]);				
			((ViewGroup) container[i]).addView(desc[i]);

		
		}
		
	}
	
	public XMLHandler LoadList(String buildUrl){
		try {
			
			/**
			 * Create a new instance of the SAX parser
			 **/
			SAXParserFactory saxPF = SAXParserFactory.newInstance();
			SAXParser saxP = saxPF.newSAXParser();
			XMLReader xmlR = saxP.getXMLReader();

			URL url = new URL(buildUrl); // URL where to get the XML
			
			/** 
			 * Create the Handler to handle each of the XML tags. 
			 **/
			XMLHandler myXMLHandler = new XMLHandler();
			xmlR.setContentHandler(myXMLHandler);
			xmlR.parse(new InputSource(url.openStream()));
			return myXMLHandler;
			
		} catch (Exception e) {//something didn't work out, lets inform user and close
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(alertContext);
			 
			// set title
			alertDialogBuilder.setTitle("Problems to connect webservice");
		
		alertDialogBuilder
		.setMessage("Connection to webservice could not be established, please try again later.")
		.setCancelable(false)
		.setPositiveButton("Close",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, close
				// current activity
				MainActivity.this.finish();
			}
		  });
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
			System.out.println(e);
			XMLHandler myXMLHandler = new XMLHandler();
			return myXMLHandler;
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch (item.getItemId())
    	{
    		case R.id.menu_about:  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(alertContext);
			 
			// set title
			alertDialogBuilder.setTitle("About application");
		
			alertDialogBuilder
			.setMessage("This app was created for Introduction to mobile device programming coursework at Glasgow Caledonian university by " 
					+ getResources().getString(R.string.author) +" \n" +"SID : S1306616")
			.setCancelable(false)
			.setPositiveButton("Close",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {

				}
			  });
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
	
			// show it
			alertDialog.show();
    		break;				
    	}
    	return true;
    }
	

}
