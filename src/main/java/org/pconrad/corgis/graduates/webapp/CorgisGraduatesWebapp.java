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

package org.pconrad.corgis.graduates.webapp;
import corgis.graduates.domain.GradMajor;

import org.pconrad.corgis.graduates.model.GraduatesDB;
import org.pconrad.corgis.graduates.model.GradMajorPlus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.pconrad.utilities.EnvVars;
import org.pconrad.webapps.sparkjava.MustacheTemplateEngine;

import spark.Spark;
import spark.ModelAndView;

import static spark.Spark.get;



/**
 CorgisGraduatesWebapp

 Based on Mustache template engine example by Sam Pullara
 
 @author Phill Conrad https://github.com/pconrad
 @author Sam Pullara https://github.com/spullara
 */

public class CorgisGraduatesWebapp {
    
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

	GraduatesDB graduatesDB = 
	    new GraduatesDB(envVars.get("MONGO_DB_URI"),
			  envVars.get("MONGO_DB_USER"),
			  envVars.get("MONGO_DB_PASSWORD"),
			  "corgis",
			  "graduates");

	final Map nullMap = new HashMap();

        get("/",
	    (rq, rs) ->
	    new ModelAndView(nullMap, "home.mustache"), new MustacheTemplateEngine());
	
        get("/lookup/majorcode",
	    (rq, rs) ->
	    new ModelAndView(nullMap, "lookup.majorcode.mustache"), new MustacheTemplateEngine());

	get("/lookup/majorcode/result",
	    (rq, rs) ->
	    {
		Map model = new HashMap();
		String majorCodeAsString = rq.queryParams("majorcode"); // get value from form
		int majorCodeAsInt = 0;
		try {
		    majorCodeAsInt = Integer.parseInt(majorCodeAsString);
		} catch (NumberFormatException nfe) {
		    model.put("error","The majorcode entered was invalid:" + majorCodeAsString);
		    return new ModelAndView(model, "lookup.majorcode.result.mustache");
		}
		    
		ArrayList<GradMajorPlus> majors
		    = graduatesDB.findByMajorCode(majorCodeAsInt);

		model.put("majorcode",majorCodeAsString);
		model.put("majors",majors);
		
		return new ModelAndView(model, "lookup.majorcode.result.mustache");
	    },
	    new MustacheTemplateEngine()
	    );
    }
}
