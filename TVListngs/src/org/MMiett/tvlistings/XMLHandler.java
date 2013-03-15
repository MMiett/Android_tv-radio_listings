package org.MMiett.tvlistings;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Mikko Miettinen 
 * @studentID S13006616
 * @email <MMIETT200@caledonian.ac.uk><mik.miettinen@gmail.com>
 * based on sourcecode from http://mobile.tutsplus.com/tutorials/android/android-sdk-build-a-simple-sax-parser/
 */




public class XMLHandler extends DefaultHandler {

	String elementValue = null;
	Boolean elementOn = false;
	public static XMLGettersSetters data = null;

	public static XMLGettersSetters getXMLData() {
		return data;
	}

	public static void setXMLData(XMLGettersSetters data) {
		XMLHandler.data = data;
	}

	/** 
	 * This will be called when the tags of the XML starts.
	 **/
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		elementOn = true;
		//if we find the top level tag corresponding to 'channel' or 'programme'
		if (localName.equals("channel"))
		{
			data = new XMLGettersSetters();
		} else if (localName.equals("programme")) {

		}
	}

	/** 
	 * This will be called when the tags of the XML end.
	 **/
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		elementOn = false;

		/** 
		 * Sets the values after retrieving the values from the XML tags that we are interested in
		 * */ 
		 if (localName.equalsIgnoreCase("title"))
			data.setTitle(elementValue);
		else if (localName.equalsIgnoreCase("desc"))
			data.setDesc(elementValue);
		else if (localName.equalsIgnoreCase("end"))
			data.setEnd(elementValue);
		else if (localName.equalsIgnoreCase("start"))
			data.setStart(elementValue);
		}

	/** 
	 * This is called to get the tags value
	 **/
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if (elementOn) {
			elementValue = new String(ch, start, length);
			elementOn = false;
		}

	}

}
