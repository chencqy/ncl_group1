package uk.ac.ncl.rbac.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;


import com.fasterxml.jackson.databind.ObjectMapper;

import uk.ac.ncl.rbac.entityApi.Entry;

@Service("apiService")
public class ApiService {
	
	public HashMap<String,Object> get_room (String room){
		HashMap<String,Object> json_edited = new HashMap<String,Object>();
		List<HashMap<String,Object>> metircs = new ArrayList<HashMap<String,Object>>();
		ObjectMapper mapper = new ObjectMapper();
		
		System.out.println(room);
		String url = "https://api.usb.urbanobservatory.ac.uk/api/v2.0a/sensors/entity/"+room;
	    
		
		try {
		       Entry entry_json =  mapper.readValue(new URL(url), Entry.class);   
	           HashMap<String,Object>  metric;
	            for(int i=0; i<entry_json.getFeed().size();i++) {
	            	 metric = new HashMap<String,Object>();
	           	  	 metric.put("name",entry_json.getFeed().get(i).getMetric());
	       		     metric.put("value",entry_json.getFeed().get(i).getTimeseries().get(0).getLatest()==null ? null : entry_json.getFeed().get(i).getTimeseries().get(0).getLatest().getValue() );	
	       		     metircs.add(metric);
	            }
	            
	            json_edited.put("name", entry_json.getName());  
	            json_edited.put("metircs", metircs);
	            
	            
	            
		} catch (Exception e) {
			json_edited.put("error", e);
			return json_edited;
			
			
		}
		return json_edited;
		      
	}
}
