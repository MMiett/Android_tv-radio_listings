package org.MMiett.tvlistings;


import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.os.Bundle;
import android.app.Activity;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.View;
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
	
	
	XMLGettersSetters data; // processed xml data class
	private Spinner channelChooser;
	private Button dateButton,prevButton,nextButton; //day choosing buttons
    private String tvListingURL = "http://bleb.org/tv/data/listings/0/bbc1_hd.xml";//rss.php?ch=bbc1_scotland&day=0";
    private String tvListingBaseURL = "http://bleb.org/tv/data/listings/";
    Time today;
    String cDate = "";
    int iCurrentChannel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		cDate = Integer.toString(today.monthDay) +"/" + Integer.toString(today.month) +"/"+ Integer.toString(today.year); 
		

		super.onCreate(savedInstanceState);
		 setContentView(R.layout.main);
		
		 channelChooser = (Spinner)findViewById(R.id.ChannelSpinner);
		 iCurrentChannel = channelChooser.getSelectedItemPosition();
		 channelChooser.setOnItemSelectedListener(new OnItemSelectedListener() {
			    @Override
			    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			    	if (iCurrentChannel != position){
			    		wipeDataViews();
			    		//lets load the default channel at app start
						data = LoadList(tvListingBaseURL+"0/"+channelChooser.getItemAtPosition(position)+".xml").data;
						placeData();
			    }
			    iCurrentChannel = position;
			    
			    }

			    @Override
			    public void onNothingSelected(AdapterView<?> parentView) {
			        return;
			    }

			});
		 dateButton = (Button)findViewById(R.id.date_button);
		 dateButton.setEnabled(false);
		 dateButton.setText(cDate);
		 prevButton = (Button)findViewById(R.id.prev_button);
		 nextButton = (Button)findViewById(R.id.next_button);
			 /**
			  *  lets attach onClickListeners
			  */	
			
			
			//lets load the default channel at app start
			data = LoadList(tvListingBaseURL+"0/"+channelChooser.getItemAtPosition(0)+".xml").data;
			placeData();


	
	}
	
	/**
	 * method to quickly wipe the unwanted views from context layot element
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
			title[i].setText("Title = "+data.getTitle().get(i));
			
			end[i] = new TextView(this);
			end[i].setText("end = "+data.getEnd().get(i));
			
			start[i] = new TextView(this);
			start[i].setText("start = "+data.getStart().get(i));
			
			desc[i] = new TextView(this);
			desc[i].setText("desc = "+data.getDesc().get(i));
			
			/**
			 * setting up container style and other parameters
			 **/
			container[i]= new LinearLayout(this);
			container[i].setOrientation(1); //1 = vertical and 0 = horizontal
			container[i].setPadding(10, 0, 0, 30);
			container[i].setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
			
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
			
		} catch (Exception e) {
			System.out.println(e);
			XMLHandler myXMLHandler = new XMLHandler();
			return myXMLHandler;
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

}
