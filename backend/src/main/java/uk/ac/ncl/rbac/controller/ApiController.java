package uk.ac.ncl.rbac.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.ncl.rbac.service.ApiService;


@RestController
public class ApiController {
	private final ApiService apiService ;
	
    @Autowired
    public ApiController(@Qualifier("apiService")ApiService apiService){
        this.apiService = apiService;
    }
    
    @GetMapping("/get_default_rooms")
	public HashMap<String, Object> getDefaultRooms() {
		return apiService.getDfaultRooms();
	}
    
	 @GetMapping("/get_data/{room}")
	 	public HashMap<String,Object> getData(@PathVariable("room") String room) {	  
		return apiService.getRoom(room);
	 }
	 
	 @GetMapping("/get_timeseries/{room}/{metric}/{start}/{end}")
	 	public HashMap<String,Object> getTimeseries(
	 			@PathVariable("room") String room,
	 			@PathVariable("metric") String metric,
	 			@PathVariable("start") String start,
	 			@PathVariable("end") String end
	 			) 
	 {	  
		return apiService.getTimeseries(room,metric,start,end);
	 }
}
