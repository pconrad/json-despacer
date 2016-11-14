package org.pconrad.corgis.graduates.model;

import corgis.graduates.domain.GradMajor;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import static com.mongodb.client.model.Filters.eq;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.bson.Document;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.pconrad.utilities.EnvVars;

import org.pconrad.corgis.utilities.CorgisDB;

public class GraduatesDB extends CorgisDB<GradMajorPlus> {


/**
   Model of a collection of JSON documents each corresponding to a GradMajorPlus object.

   @param mongoDBUri URI for the mongoDB database.   See notes above.
   @param mongoDBUser Username for connecting.  See notes above.
   @param mongoDBPassword Username for connected.  See notes above.
   @param dbName Name of mongoDB database.
   @param collectionName  The name of the MongoDB collection that will be accessed and returned.
*/
    
    public GraduatesDB(String mongoDBUri,
		       String mongoDBUser,
		       String mongoDBPassword,
		       String mongoDBName,
		       String collectionName) {
	super(mongoDBUri,
	      mongoDBUser,
	      mongoDBPassword,
	      mongoDBName,
	      collectionName);
    }    
    
    /**
       Must override this abstract method in order for CorgisDB to 
       work properly.   This is what creates an instance from a 
       JSONOBject

       @param jsonObject representation of a single GradMajor object 
       @return an instance of GradMajorPlus

     */
    public GradMajorPlus makeElement(JSONObject jsonObject) {
	return new GradMajorPlus(jsonObject);
    }

    /**
       Return the objects that match the query
       
       @param majorCode 
       @see <a href="https://think.cs.vt.edu/corgis/json/graduates/graduates.html">https://think.cs.vt.edu/corgis/json/graduates/graduates.html</a>
       @return list of matching objects

     */
    public ArrayList<GradMajorPlus> findByMajorCode(int majorCode) {
	
	ArrayList<GradMajorPlus> elementList = new ArrayList<GradMajorPlus>();
	Block<Document> addElem = (d) -> {
	    GradMajorPlus e = this.jsonToElement(d.toJson());
	    if (e!=null) {elementList.add(e);}
	};
	this.getCollection()
	    .find(eq("majorInformation.majorCode", majorCode))
	    .forEach( addElem );
	return elementList;
    }



    /**
       This main is just here for running a quick test of the 
       methods defined above.
       @param args command line arguments.  
     */
    
    public static void main(String[] args) {
	
	HashMap<String,String> envVars =
	    EnvVars.getNeededEnvVars(new String []{ "MONGO_DB_USER",
					    "MONGO_DB_PASSWORD",
					    "MONGO_DB_URI"});

	GraduatesDB graduatesDB = 
	    new GraduatesDB(envVars.get("MONGO_DB_URI"),
			   envVars.get("MONGO_DB_USER"),
			   envVars.get("MONGO_DB_PASSWORD"),
			   "corgis",
			   "graduates");

	// see https://think.cs.vt.edu/corgis/json/graduates/graduates.html
	int majorCode = 3419; 
	if (args.length > 0) {
	    try {
		majorCode = Integer.parseInt(args[0]);
	    } catch (NumberFormatException nfe) {
		System.err.println("Error: major code should be an int");
	    }
	}
	
	System.out.println("Printing records with majorCode=" + majorCode);

	ArrayList<GradMajorPlus> elements = graduatesDB.findByMajorCode(majorCode);
	elements.stream().forEach( (e) -> System.out.println(e) );
	
    }


    
}

