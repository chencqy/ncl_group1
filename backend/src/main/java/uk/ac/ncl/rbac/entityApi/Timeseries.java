package uk.ac.ncl.rbac.entityApi;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Timeseries {
	private LatestOrValues latest; //get_data
	private Historic historic; //get_timeseries
	
	public LatestOrValues getLatest() {
		return latest;
	}

	public void setLatest(LatestOrValues latest) {
		this.latest = latest;
	}
	
	public Historic gethistoric() {
		return historic;
	}

	public void sethistoric(Historic historic) {
		this.historic = historic;
	}
	
}

