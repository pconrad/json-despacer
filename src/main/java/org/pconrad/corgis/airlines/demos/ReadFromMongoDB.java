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

import org.pconrad.utilities.EnvVars;

public class ReadFromMongoDB {
    
    public static void printRecordsForAirport(String airportCode, MongoCollection<Document> collection) {
	Block<Document> printBlock = (d) -> System.out.println(d.toJson());
	collection.find(eq("airport.code", airportCode)).forEach( printBlock );
    }




    public static void main(String[] args) {

	
	HashMap<String,String> envVars =
	    EnvVars.getNeededEnvVars(new String []{ "MONGO_DB_USER",
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

