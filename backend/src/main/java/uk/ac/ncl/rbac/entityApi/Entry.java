package uk.ac.ncl.rbac.entityApi;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Entry {
	
	private String name;
	private List<Feed> feed;


	
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
    
	
	public List<Feed> getFeed() {
		return feed;
	}

	public void setFeed(List<Feed> feed) {
		this.feed = feed;
	}
	
	
}