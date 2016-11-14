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


import java.io.StringWriter;

public class RespaceJSON {

    private StringWriter sw = null;

    public RespaceJSON(String input) throws IOException {
       this.sw = new StringWriter();
       readJson(input);
    }

    public String result() {
	return sw.toString();
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
	sw.write("[");
	boolean first=true;
	for (Object o : jsonArray) {
	    if (first) {first = false; } else {sw.write(",");}
	    processObj(o);	    
	}
	sw.write("]\n");
    }

    public  void processJSONObject(JSONObject jsonObject)  throws IOException  {
	sw.write("{");
	boolean first=true;
	for ( Object ko : jsonObject.keySet()) {
	    String key = (String) ko;
	    if (first) {first = false; } else {sw.write(",");}
	    Object o = jsonObject.get(key);
	    sw.write("\"" + key.replaceAll("_"," ") + "\" : ");
	    processObj(o);
	}
	sw.write("}\n");			   
    }

    public  void processJSONValue(JSONValue jsonValue)  throws IOException  {
	sw.write(jsonValue.toString());			   
    }

    public  void processPlainObject(Object o)  throws IOException  {
	if (o instanceof String) {
	    sw.write("\"" + o + "\"");
	} else {
	    sw.write(o.toString());
	}
    }
    
    public  void readJson(String input)  throws IOException  {

	JSONParser parser = new JSONParser();

	try {

	    Object obj = parser.parse(input);
	    processObj(obj);
	    
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	
    }


}

