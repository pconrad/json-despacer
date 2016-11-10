package org.pconrad.corgis.airlines.demos;

import java.util.ArrayList;
import corgis.airlines.AirlinesLibrary;
import corgis.airlines.domain.Airline;

public class ExampleClass {
    public static void main(String[] args) {
        // Get access to the library
        AirlinesLibrary airlinesLibrary = new AirlinesLibrary();
        // Access data inside the library
        ArrayList<Airline> list_of_airline = airlinesLibrary.getReports(false);
        
        System.out.println(list_of_airline);
    }
}
