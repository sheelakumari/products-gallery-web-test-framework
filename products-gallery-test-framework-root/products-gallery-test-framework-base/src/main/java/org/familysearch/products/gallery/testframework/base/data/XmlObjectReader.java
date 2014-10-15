package org.familysearch.products.gallery.testframework.base.data;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XmlObjectReader {

	public static Logger logger = org.apache.log4j.Logger.getLogger( XmlObjectReader.class );
	
	
	
	XStream xstream = new XStream( new DomDriver() );	
	boolean bUseAlternateData;
	File alternateDataDir;
	String sourcePath;
	
	
	private XmlObjectReader() {
		this.bUseAlternateData = false;
	}

	private XmlObjectReader(File alternateDataDir) {
		this.bUseAlternateData = true;
		this.alternateDataDir = alternateDataDir;
	}
	
	
	public static XmlObjectReader getResourceReader() {
		return new XmlObjectReader();
	}
	
	
	public static XmlObjectReader getFileReader(File rootDir) {
		return new XmlObjectReader(rootDir);
	}
	
	public XmlObjectReader associatedWith( String canonicalName ) {

		// Changes . to / and adds Data.xml to the file name.
		
		this.sourcePath = canonicalName.replaceAll("\\.","/") + "Data.xml";
		
		logger.info( "The associated test data path is: " + this.sourcePath );
		
		return this;
	}
	
	public XmlObjectReader associatedWith( Class testClass ) {
		// Does an association with the class name.  Puts all the data into the 
		// testdata directory.
		
		// TODO: Should this path be changed somehow.
		this.sourcePath = "testdata/" + testClass.getSimpleName() + "Data.xml";
		
		logger.info( "The associated test data path is: " + this.sourcePath );
		
		return this;
	}
	
	
	public XmlObjectReader forSource(String sourcePath) {
		this.sourcePath = sourcePath;
		return this;
	}
	
	public XmlObjectReader andUseAlias(String name, Class type) {
		xstream.alias(name, type);
		return this;
	}
	
	public XmlObjectReader andAlias(String name, Class type) {
		return andUseAlias(name, type);
	}
	
	
	
	public Object andRetrieveContent() {
		Object contentObject = null;
		
		if ( bUseAlternateData ) {

			// Read as a file on the file system.
			File inputFile = new File( alternateDataDir, sourcePath );
			
			try {

				contentObject = (Object) xstream.fromXML( new InputStreamReader(
	                      new FileInputStream(inputFile), "UTF8") );
								
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}		
			
		}
		else {
			// Read as a resource.
			contentObject = (Object) xstream.fromXML( this.getClass().getClassLoader().getResourceAsStream( sourcePath ));
		}
	
		// There is a chance that this object will be null.
		return contentObject;
	}
	
	
	
}
