package org.pconrad.corgis.utilities;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

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

public class UploadToMongoDB {
    
    public static void uploadJson(String fileName, MongoCollection<Document> collection) {

	JSONParser parser = new JSONParser();

	try {

	    Object obj = parser.parse(new FileReader(fileName));

	    for (Object o : (JSONArray) obj) {
		JSONObject jsonObject = (JSONObject) o;
		Document d =  Document.parse(jsonObject.toJSONString());
		collection.insertOne(d);
	    }

	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	
    }


    public static void main(String[] args) {

	if (args.length !=3) {
	    System.out.println("Usage: java UploadToMongoDB dbname collection jsonfile");
	    System.out.println("Example: java UploadToMongoDB corgis airlines airlines.json");
	    System.exit(1);
	}

	String dbName = args[0];
	String collectionName = args[1];
	String jsonFileName = args[2];
	
	HashMap<String,String> envVars =
	    EnvVars.getNeededEnvVars(new String []{ "MONGO_DB_USER",
					    "MONGO_DB_PASSWORD",
					    "MONGO_DB_URI"});

	String uriString = envVars.get("MONGO_DB_URI")
	    .replace("<dbuser>",envVars.get("MONGO_DB_USER"))
	    .replace("<dbpassword>",envVars.get("MONGO_DB_PASSWORD"));

	MongoClientURI connectionString = new MongoClientURI(uriString);
	MongoClient mongoClient = new MongoClient(connectionString);
	MongoDatabase database = mongoClient.getDatabase(dbName);
	MongoCollection<Document> collection = database.getCollection(collectionName);

	System.out.println("Before upload collection.count=" + collection.count());	
	uploadJson(jsonFileName,collection);
	System.out.println("After upload collection.count=" + collection.count());		
    }



    
}

