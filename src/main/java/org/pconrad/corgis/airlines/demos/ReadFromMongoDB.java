package org.pconrad.corgis.airlines.demos;

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


public class ReadFromMongoDB {
    
    public static void printRecordsForAirport(String airportCode, MongoCollection<Document> collection) {
	Block<Document> printBlock = (d) -> System.out.println(d.toJson());
	collection.find(eq("airport.code", airportCode)).forEach( printBlock );
    }



    /**
       return a HashMap with values of all the environment variables
       listed; print error message for each missing one, and exit if any
       of them is not defined.
    */

    public static HashMap<String,String> getNeededEnvVars(String [] neededEnvVars) {
	HashMap<String,String> envVars = new HashMap<String,String>();
	

	for (String k:neededEnvVars) {
	    String v = System.getenv(k);
	    envVars.put(k,v);
	}

	boolean error=false;
	for (String k:neededEnvVars) {
	    if (envVars.get(k)==null) {
		error = true;
		System.err.println("Error: Must define env variable " + k);
	    }
	}
	if (error) { System.exit(1); }

	return envVars;
    }

    public static void main(String[] args) {

	
	HashMap<String,String> envVars =
	    getNeededEnvVars(new String []{ "MONGO_DB_USER",
					    "MONGO_DB_PASSWORD",
					    "MONGO_DB_URI"});

	String uriString = envVars.get("MONGO_DB_URI")
	    .replace("<dbuser>",envVars.get("MONGO_DB_USER"))
	    .replace("<dbpassword>",envVars.get("MONGO_DB_PASSWORD"));

	MongoClientURI connectionString = new MongoClientURI(uriString);
	MongoClient mongoClient = new MongoClient(connectionString);
	MongoDatabase database = mongoClient.getDatabase("corgis");
	MongoCollection<Document> collection = database.getCollection("airlines");
	System.out.println("collection.count=" + collection.count());

	if (args.length > 0) {
	    String airportCode = args[0];
	    System.out.println("Printing records with airportCode=" + airportCode);
	    printRecordsForAirport(airportCode,collection);
	}
	
    }



    
}

