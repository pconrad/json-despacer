package org.pconrad.corgis.airlines.model;

import corgis.airlines.domain.Airline;
import org.json.simple.JSONObject;

public class AirlinePlus extends Airline {
    public AirlinePlus (JSONObject jsonObject) {
	super(jsonObject);
    }

    public String getMonth() {
	return String.format("%4d/%02d",
			     this.getTime().getYear(),
			     this.getTime().getMonth());
    }
    
    public String getOnTimePercentage() {
	int onTime = this.getStatistics().getFlights().getOnTime();
	int total = this.getStatistics().getFlights().getTotal();
	if (total==0)
	    return "-";
	return String.format("%5.2f", (100.0 * onTime)/total);
    }
    
}

