package uk.ac.ncl.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ncl.rbac.service.ApiService;

import java.util.HashMap;


@RestController
@CrossOrigin
public class ApiController {
	private final ApiService apiService ;
	
    @Autowired
    public ApiController(@Qualifier("apiService")ApiService apiService){
        this.apiService = apiService;
    }
    
    

    @GetMapping("/api/{role}/get_default_rooms")
	public HashMap<String, Object> getDefaultRoomsByRole(@PathVariable("role") String role) {
		return apiService.getDfaultRoomsByRole(role);
	}
    
    
	 @GetMapping("/api/{role}/get_data/{room}")
	 	public HashMap<String,Object> getData(
	 			@PathVariable("role") String role,
	 			@PathVariable("room") String room
	 			) 
	 {	  
		return apiService.getRoom(role,room);
	 }
	 
	 @GetMapping("/api/{role}/get_timeseries/{role}/{room}/{metric}/{start}/{end}")
	 	public HashMap<String,Object> getTimeseries(
	 			@PathVariable("role") String role,
	 			@PathVariable("room") String room,
	 			@PathVariable("metric") String metric,
	 			@PathVariable("start") String start,
	 			@PathVariable("end") String end
	 			) 
	 {	  
		return apiService.getTimeseries(role,room,metric,start,end);
	 }
}
