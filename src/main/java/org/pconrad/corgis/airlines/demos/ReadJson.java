package org.pconrad.corgis.airlines.demos;

import java.util.ArrayList;
import corgis.airlines.AirlinesLibrary;
import corgis.airlines.domain.Airline;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


public class ReadJson {

    public static void readJson(String fileName) {
	//read file into stream, try-with-resources
	try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
	    stream.forEach(System.out::println);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    public static void main(String[] args) {
	readJson("airlines.json");
    }
}
