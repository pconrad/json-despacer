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
import java.util.Map;

import org.bson.Document;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.pconrad.utilities.EnvVars;


import java.io.PrintWriter;

public class DespaceJSON {

    private PrintWriter pw = null;

    public DespaceJSON(String infile, String outfile) throws IOException {
       this.pw = new PrintWriter(outfile, "UTF-8");
       readJson(infile);
       this.pw.close();
    }
    

    public  void processObj(Object obj)  throws IOException  {
	if (obj instanceof JSONArray) {
	    processJSONArray((JSONArray) obj);
	} else if (obj instanceof JSONObject) {
	    processJSONObject((JSONObject) obj);
	} else if (obj instanceof JSONValue) {
	    processJSONValue((JSONValue) obj);
	} else {
	    processPlainObject(obj);
	}	
    }

    public  void processJSONArray(JSONArray jsonArray) throws IOException {
	pw.println("[");
	boolean first=true;
	for (Object o : jsonArray) {
	    if (first) {first = false; } else {pw.println(",");}
	    processObj(o);	    
	}
	pw.println("]");
    }

    public  void processJSONObject(JSONObject jsonObject)  throws IOException  {
	pw.print("{");
	boolean first=true;
	for ( Object ko : jsonObject.keySet()) {
	    String key = (String) ko;
	    if (first) {first = false; } else {pw.print(",");}
	    Object o = jsonObject.get(key);
	    pw.print("\"" + key.replaceAll(" ","_") + "\" : ");
	    processObj(o);
	}
	pw.print("}");			   
    }

    public  void processJSONValue(JSONValue jsonValue)  throws IOException  {
	pw.print(jsonValue);			   
    }

    public  void processPlainObject(Object o)  throws IOException  {
	if (o instanceof String) {
	    pw.print("\"" + o + "\"");
	} else {
	    pw.print(o);
	}
    }
    
    public  void readJson(String fileName)  throws IOException  {

	JSONParser parser = new JSONParser();

	try {

	    Object obj = parser.parse(new FileReader(fileName));
	    processObj(obj);
	    
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	
    }


    public static void main(String[] args) {

	if (args.length !=2) {
	    System.out.println("Usage: java jsonin jsonout");
	    System.out.println("Example: java graduates.json graduates-nospace.json");
	    System.exit(1);
	}

	String jsonInFileName = args[0];
	String jsonOutFileName = args[1];



	try{
	    DespaceJSON dj = new DespaceJSON(jsonInFileName,jsonOutFileName);
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }    
}

