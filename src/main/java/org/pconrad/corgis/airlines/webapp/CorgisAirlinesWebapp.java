/*
 * Copyright 2014
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.pconrad.corgis.airlines.webapp;

import corgis.airlines.domain.Airline;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.pconrad.corgis.airlines.model.AirlineDB;
import org.pconrad.corgis.airlines.model.AirlinePlus;
import org.pconrad.utilities.EnvVars;
import org.pconrad.webapps.sparkjava.MustacheTemplateEngine;

import spark.Spark;
import spark.ModelAndView;

import static spark.Spark.get;



/**
 CorgisAirlinesWebapp

 Based on Mustache template engine example by Sam Pullara
 
 @author Phill Conrad https://github.com/pconrad
 @author Sam Pullara https://github.com/spullara
 */

public class CorgisAirlinesWebapp {
    
    public static void main(String[] args) {
	Spark.staticFileLocation("/static");
	try {
	    Spark.port(Integer.valueOf(System.getenv("PORT"))); // needed for Heroku
	} catch (Exception e) {
	    System.err.println("NOTICE: using default port. \n" +
			       "Define PORT env variable to override");
	}

	HashMap<String,String> envVars =
	    EnvVars.getNeededEnvVars(new String []{ "MONGO_DB_USER",
					    "MONGO_DB_PASSWORD",
					    "MONGO_DB_URI"});

	AirlineDB airlineDB = 
	    new AirlineDB(envVars.get("MONGO_DB_URI"),
			  envVars.get("MONGO_DB_USER"),
			  envVars.get("MONGO_DB_PASSWORD"));

	final Map nullMap = new HashMap();

        get("/",
	    (rq, rs) ->
	    new ModelAndView(nullMap, "home.mustache"), new MustacheTemplateEngine());
	
        get("/lookup/airport",
	    (rq, rs) ->
	    new ModelAndView(nullMap, "lookup.airport.mustache"), new MustacheTemplateEngine());

	get("/lookup/airport/result",
	    (rq, rs) ->
	    {
		Map model = new HashMap();
		String airportCode = rq.queryParams("airport_code"); // get value from form
		ArrayList<AirlinePlus> airlines = airlineDB.findByAirportCode(airportCode);
		// System.out.println("airlines=" + airlines);
		model.put("airport_code",airportCode);
		// It's all the same airport.  So just grab the airport name from
		// the first record and display it once.
		if (airlines.size() > 1)
		    model.put("airport_name",airlines.get(0).getAirport().getName());
		model.put("airlines",airlines);
		
		return new ModelAndView(model, "lookup.airport.result.mustache");
	    },
	    new MustacheTemplateEngine()
	    );
    }
}
