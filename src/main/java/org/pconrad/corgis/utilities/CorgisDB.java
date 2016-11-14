package org.pconrad.corgis.utilities;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import static com.mongodb.client.model.Filters.eq;


import corgis.airlines.domain.Graduate;

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

public class AirlineDB {

    private MongoCollection<Document> collection;
    private JSONParser parser = new JSONParser();
    
    /**
       Factory method for a MongoDB Collection.  

       TODO: Factor this out of the AirlineDB class, since it is 
       common code for dealing with MongoDB that has nothing specifically to do with
       the airline collection.

	 <br><br>
	 Notes on the <tt>mongoDBUri</tt> param: An example of this param is:<br><br> 
         <tt>mongodb://&lt;dbuser&gt;:&lt;dbpassword&gt;&#64;ds050559.mlab.com:50559/corgis</tt>. 
	 <br><br>
         The value should literally contain the substrings 
         <tt>&lt;dbuser&gt;</tt> and<tt>&lt;dbpassword&gt;</tt> as these are the places
         the the values for the <tt>mongoDBUser</tt> and <tt>mongoDBPassword</tt> parameters
         will be substituted in before connecting.<br><br>
         

         Note that this is the form in which the URI is provided by
         the MongoDB provider <a
         href="https://mlab.com">mlab.com</a>. <br><br>Also note that
         it is common for MongoDB clients to ignore the database name
         in the URI string (e.g. corgis, in this case) when a URI is
         used only to authenticate.



       @param mongoDBUri URI for the mongoDB database.   See notes above.
       @param mongoDBUser Username for connecting.  See notes above.
       @param mongoDBPassword Username for connected.  See notes above.
       @param dbName Name of mongoDB database.
       @param collectionName  The name of the MongoDB collection that will be accessed and returned.
       @return a reference to a collection
     */
    
    public static MongoCollection<Document> collectionFactory
	(String mongoDBUri,
	 String mongoDBUser,
	 String mongoDBPassword,
	 String dbName,
	 String collectionName) {
	
	String uriString = mongoDBUri
	    .replace("<dbuser>",mongoDBUser)
	    .replace("<dbpassword>",mongoDBPassword);
	    
	MongoClientURI connectionString = new MongoClientURI(uriString);
	MongoClient mongoClient = new MongoClient(connectionString);
	MongoDatabase database = mongoClient.getDatabase(dbName);
	MongoCollection<Document> collection =
	    database.getCollection(collectionName);
	// System.out.println("collection.count=" + collection.count());
	return collection;
    }

/**
       Constructor for AirlineDB.   AirlineDB is an abstraction of a 
       the the JSON document <tt>"airlines.json"</tt> available from
       the web page <a href="https://think.cs.vt.edu/corgis/json/airlines/airlines.html">https://think.cs.vt.edu/corgis/json/airlines/airlines.html</a>.
       <br><br>
       It is a somewhat leaky abstraction, in that it depends on a few
       implementation details; specifically:
       <ol>
        <li>
         There is a MongoDB database with the hard-coded name <tt>corgis</tt>
        </li>
        <li>
         There is, in that database, a collection with the hard-coded name
         <tt>airlines</tt>
        </li>
        <li>Each MongoDB document in that collection corresponds to one 
         element of the top-level array from <tt>airlines.json</tt>, that is
         to say, it is a JSON object that can be turned into a single instance
         of the Java class 
         <a href="https://think.cs.vt.edu/corgis/java/airlines/docs/corgis/airlines/domain/Airline.html">
	 <tt>corgis.airlines.domain.Airline</tt>
         </a> provided by the .jar file available on the page
	 <a href="https://think.cs.vt.edu/corgis/java/airlines/airlines.html">
	 https://think.cs.vt.edu/corgis/java/airlines/airlines.html
	 </a>.
	</ol>
	<br><br>It is necessary to include the <tt>.jar</tt> file from that 
         page in order to be able to refer to the <tt>corgis.airlines.domain.Airline</tt> class.  However, the methods of accessing the data on this page are used <em>in place of</em> the <tt>AirlineLibrary</tt> class and the <tt>airlines.db</tt> file from the Java page of the Corgis website.
	 <br><br>
	 Notes on the <tt>mongoDBUri</tt> param: An example of this param is:<br><br> 
         <tt>mongodb://&lt;dbuser&gt;:&lt;dbpassword&gt;&#64;ds050559.mlab.com:50559/corgis</tt>. 
	 <br><br>
         The value should literally contain the substrings 
         <tt>&lt;dbuser&gt;</tt> and<tt>&lt;dbpassword&gt;</tt> as these are the places
         the the values for the <tt>mongoDBUser</tt> and <tt>mongoDBPassword</tt> parameters
         will be substituted in before connecting.<br><br>
         

         Note that this is the form in which the URI is provided by
         the MongoDB provider <a
         href="https://mlab.com">mlab.com</a>. <br><br>Also note that
         it is common for MongoDB clients to ignore the database name
         in the URI string (e.g. corgis, in this case) when a URI is
         used only to authenticate.

       @param mongoDBUri URI for the mongoDB database.   See notes above.
       @param mongoDBUser Username for connecting.  See notes above.
       @param mongoDBPassword Username for connected.  See notes above.
     */
        
    public AirlineDB(String mongoDBUri,
		     String mongoDBUser,
		     String mongoDBPassword) {

	this.collection =
	    AirlineDB.collectionFactory
	    (mongoDBUri,
	     mongoDBUser,
	     mongoDBPassword,
	     "corgis",
	     "airlines");


    }    

    public Airline jsonToAirline(String json) {
	try {
	    JSONObject jsonObject = (JSONObject) parser.parse(json);
	    Airline a = new Airline(jsonObject);
	    return a;
	} catch (ParseException pe) {
	    pe.printStackTrace();
	    return null;			
	}
    }

    public AirlinePlus jsonToAirlinePlus(String json) {
	try {
	    JSONObject jsonObject = (JSONObject) parser.parse(json);
	    AirlinePlus a = new AirlinePlus(jsonObject);
	    return a;
	} catch (ParseException pe) {
	    pe.printStackTrace();
	    return null;			
	}
    }


    
    public ArrayList<AirlinePlus> findByAirportCode(String airportCode) {
	ArrayList<AirlinePlus> airlineList = new ArrayList<AirlinePlus>();
	// Block<Document> = lambda to "do something with a document"

	Block<Document> addAirlineRecord = (d) -> {
	    AirlinePlus a = jsonToAirlinePlus(d.toJson());
	    if (a!=null) {airlineList.add(a);}
	};
	collection
	    .find(eq("airport.code", airportCode))
	    .forEach( addAirlineRecord );
	return airlineList;
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

	AirlineDB airlineDB = 
	    new AirlineDB(envVars.get("MONGO_DB_URI"),
			  envVars.get("MONGO_DB_USER"),
			  envVars.get("MONGO_DB_PASSWORD"));
	
	String airportCode = "LAX";	
	if (args.length > 0) { airportCode = args[0]; }
	System.out.println("Printing records with airportCode=" + airportCode);

	ArrayList<AirlinePlus> airlines = airlineDB.findByAirportCode(airportCode);
	airlines.stream().forEach( (a) -> System.out.println(a) );
	
    }
    
}

