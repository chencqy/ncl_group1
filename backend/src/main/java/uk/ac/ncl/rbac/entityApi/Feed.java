package uk.ac.ncl.rbac.entityApi;




import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Feed {
	private String feedId;
	private String metric;
	private List<Timeseries>  timeseries;
	
	public String getFeedId() {
		return feedId;
	}
	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}
	public String getMetric() {
		return metric;
	}
	public void setMetric(String metric) {
		this.metric = metric;
	}
	public List<Timeseries>  getTimeseries() {
		return timeseries;
	}
	public void setTimeseries(List<Timeseries>  timeseries) {
		this.timeseries = timeseries;
	}

}