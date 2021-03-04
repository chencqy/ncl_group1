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
    
	 @GetMapping("/get_data/{room}")
	 	public HashMap<String,Object> get_data(@PathVariable("room") String room) {	  
		return apiService.get_room(room);
	 }
}
