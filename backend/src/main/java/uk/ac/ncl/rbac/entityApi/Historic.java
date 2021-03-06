package uk.ac.ncl.rbac.entityApi;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Historic {
	
	private List<LatestOrValues> values;
	
	public List<LatestOrValues> getValues() {
		return values;
	}

	public void setValues(List<LatestOrValues> values) {
		this.values = values;
	}
	
	
}
