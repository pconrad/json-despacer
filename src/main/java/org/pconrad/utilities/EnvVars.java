package org.pconrad.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
   Utility class for dealing with environment variables

 */

public class EnvVars {

    /**
       return a HashMap with values of all the environment variables
       listed; print error message for each missing one, and exit if any
       of them is not defined.
 
       @param neededEnvVars array of String representing required env var names
       @return HashMap of environment variable values
    */

    public static HashMap<String,String>
	getNeededEnvVars(String [] neededEnvVars) {
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

}
