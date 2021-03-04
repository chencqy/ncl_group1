package uk.ac.ncl.rbac.entityApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Timeseries {
	private Latest_or_values latest; 
	
	public Latest_or_values getLatest() {
		return latest;
	}

	public void setLatest(Latest_or_values latest) {
		this.latest = latest;
	}
	

	
}
