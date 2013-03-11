package org.MMiett.tvlistings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.MMiett.tvlistings.*;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;




import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;

/**
 * @author Mikko Miettinen
 *
 */
public class MainActivity extends Activity {
	
	private TextView infoContainer;
	XMLGettersSetters data;
	private LinearLayout lines;
	
	

	private TextView errorText;
	private Button dateButton,prevButton,nextButton;
	private String result;
    private String tvListingURL = "http://bleb.org/tv/data/listings/0/4music.xml";//rss.php?ch=bbc1_scotland&day=0";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.main);
		 View layout = findViewById(R.id.layout);
		 /**  
			 * Create TextView Arrays to add the retrieved data to.
			 **/
			TextView title[];
			
			TextView end[];
			
			TextView start[];
			
			TextView desc[];
			
			TextView flags[];
			
			
			
			try {
				
				/**
				 * Create a new instance of the SAX parser
				 **/
				SAXParserFactory saxPF = SAXParserFactory.newInstance();
				SAXParser saxP = saxPF.newSAXParser();
				XMLReader xmlR = saxP.getXMLReader();

				
				URL url = new URL(tvListingURL); // URL of the XML
				
				/** 
				 * Create the Handler to handle each of the XML tags. 
				 **/
				XMLHandler myXMLHandler = new XMLHandler();
				xmlR.setContentHandler(myXMLHandler);
				xmlR.parse(new InputSource(url.openStream()));
				
			} catch (Exception e) {
				System.out.println(e);
			}

			data = XMLHandler.data;
			
			/** 
			 * Makes the TextView length the size of the TextView arrays by getting the size of the 
			 **/
			title = new TextView[data.getTitle().size()];
			end = new TextView[data.getEnd().size()];
			start = new TextView[data.getStart().size()];
			desc = new TextView[data.getDesc().size()];
			flags = new TextView[data.getFlags().size()];

			

			/** 
			 * Run a for loop to set All the TextViews with text until 
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
				
				flags[i] = new TextView(this);
				flags[i].setText("flags = "+data.getFlags().get(i));
				


				((ViewGroup) layout).addView(title[i]);
				((ViewGroup) layout).addView(end[i]);
				((ViewGroup) layout).addView(start[i]);
				((ViewGroup) layout).addView(desc[i]);
				((ViewGroup) layout).addView(flags[i]);
			
			}

			//setContentView(layout);
	
	       //get a reference to the layout
	   /* LayoutInflater inflater = getLayoutInflater();
	    LinearLayout mainLayout = (LinearLayout) inflater.inflate(R.layout.main,null);
	    try
	    {
	            //create an instance of the DefaultHandler class 
	            //**ALTER THIS FOR YOUR CLASS NAME**
	        XMLHandler handler = new XMLHandler(getApplicationContext());
	            //get the string list by calling the public method
	        ArrayList<TextView> newViews = handler.getData();
	            //convert to an array
	        Object[] products = newViews.toArray();
	            //loop through the items, creating a View item for each
	        for(int i=0; i<products.length; i++)
	        {
	            //add the next View in the list
	            mainLayout.addView((TextView)products[i]);
	        }
	    }
	    catch(Exception pce) { Log.e("AndroidTestsActivity", "PCE "+pce.getMessage()); }

	    setContentView(mainLayout);
		//LayoutInflater inflater = getLayoutInflater();
		//lines = (LinearLayout)findViewById(R.id.ListingContext);
		//Lists = (ListView)findViewById(R.id.ListingContex);
		infoContainer = (TextView)findViewById(R.id.temporalTV);
		
		
	        try
	        {
	        	result =  tvListingString(tvListingURL);
	        	infoContainer.setText(result);
	        }
	        catch(IOException ae)
	        {
	        	infoContainer.setText("Error");
	        	errorText.setText(ae.toString());
	        }
	       
		
	/*	 String[] informations = new String[] { "one", "two", "three" };
		    TextView informationView;

		    for (int i = 0; i < informations.length; i++) {
		        View line = new View(this);
		        line.setLayoutParams(new LayoutParams(1, LayoutParams.FILL_PARENT));
		        line.setBackgroundColor(0xAA345556);
		        informationView = new TextView(this);
		        informationView.setText(informations[i]);
		        infoContainer.addView(informationView, 0);
		        infoContainer.addView(line, 1);
		    }*/
		
		
	
		
		
	}
	
	 private static String tvListingString(String urlString)throws IOException
	    {
		 	String result = "";
	    	InputStream anInStream = null;
	    	int response = -1;
	    	URL url = new URL(urlString);
	    	URLConnection conn = url.openConnection();
	    	if (!(conn instanceof HttpURLConnection))
	    			throw new IOException("Not an HTTP connection");
	    	try
	    	{
	    		HttpURLConnection httpConn = (HttpURLConnection) conn;
	    		httpConn.setAllowUserInteraction(false);
	    		httpConn.setInstanceFollowRedirects(true);
	    		httpConn.setRequestMethod("GET");
	    		httpConn.connect();
	    		response = httpConn.getResponseCode();
	    		if (response == HttpURLConnection.HTTP_OK)
	    		{
	    			anInStream = httpConn.getInputStream();
	    			InputStreamReader in= new InputStreamReader(anInStream);
	    			BufferedReader bin= new BufferedReader(in);
	    			//result = bin.readLine();
	    			// Input is over multiple lines
	    			String line = new String();
	    			while (( (line = bin.readLine())) != null)
	    			{
	    				result = result + "\n" + line;
	    			}
	    		}
	    	}
	    	catch (Exception ex)
	    	{
	    			throw new IOException("Error connecting");
	    	}
	    	return result;
	    }

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

}
