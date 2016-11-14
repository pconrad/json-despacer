package org.pconrad.corgis.graduates.model;

import corgis.graduates.domain.GradMajor;
import org.json.simple.JSONObject;


public class GradMajorPlus
    extends GradMajor {
    
    public GradMajorPlus (JSONObject jsonObject) {
	super(jsonObject);
    }

    // This is where you can add additional helper functions
    // for things you want to be able to access in the Mustache template
    // that are not already available in the getters of the basic object
    
}
