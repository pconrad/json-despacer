package org.pconrad.corgis.utilities;

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

public abstract class CorgisDB<E> {

    private MongoCollection<Document> collection;

    public MongoCollection<Document> getCollection() {
	return this.collection;
    }

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
       CorgisDB is an abstraction of a 
       the the JSON documents available from
       the web page <a href="https://think.cs.vt.edu/corgis">https://think.cs.vt.edu/corgis/json</a>.
       To instantiate a CorgisDB there should be a MongoDB database containing a collection, where
       each MongoDB document in that collection corresponds to one 
       element of the top-level array in one of the Corgis JSON datasets.

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
     */
        
    public CorgisDB(String mongoDBUri,
		     String mongoDBUser,
		    String mongoDBPassword,
		    String mongoDBName,
		    String collectionName) {

	this.collection =
	   CorgisDB.collectionFactory
	    (mongoDBUri,
	     mongoDBUser,
	     mongoDBPassword,
	     mongoDBName,
	     collectionName);


    }    

    public E jsonToElement(String json) {
	try {
	    JSONObject jsonObject = (JSONObject) parser.parse(json);
	    E a = this.makeElement(jsonObject);
	    return a;
	} catch (ParseException pe) {
	    pe.printStackTrace();
	    return null;			
	}
    }

    public abstract E makeElement(JSONObject jsonObject);
}

