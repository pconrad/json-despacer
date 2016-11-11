package org.pconrad.corgis.airlines.demos;

import corgis.airlines.AirlinesLibrary;
import corgis.airlines.domain.Airline;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ReadJson {
    
    public static void readJson(String fileName) {

	JSONParser parser = new JSONParser();

	try {

	    Object obj = parser.parse(new FileReader(fileName));

	    for (Object o : (JSONArray) obj) {
		Airline a = new Airline((JSONObject) o);
		System.out.println("a=" + a);
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
	readJson("airlines.json");
    }

}

