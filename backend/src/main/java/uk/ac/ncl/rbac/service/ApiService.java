package uk.ac.ncl.rbac.service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;


import com.fasterxml.jackson.databind.ObjectMapper;

import uk.ac.ncl.rbac.entityApi.Entry;
import uk.ac.ncl.rbac.entityApi.JsonRooms;
import uk.ac.ncl.rbac.entityApi.Timeseries;

@Service("apiService")
public class ApiService {



	public boolean Customizedcontains(List<String> list, String room) {
		for (String s : list) {
			if ((s.replace(" ", "").trim()).equalsIgnoreCase(room)) return true;
		}
		return false;
	}

	public boolean PermissionCheck(String role,String room) {
		if(role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("student") || role.equalsIgnoreCase("researcher") || role.equalsIgnoreCase("public")) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				ClassPathResource classPathResource = new ClassPathResource("/static/Rooms.json");
				InputStream inputStream = classPathResource.getInputStream();
				JsonRooms jsonRooms =  mapper.readValue(inputStream, JsonRooms.class);
				String replacedRoom = room.replace("-", "").trim();
			
				if(role.equalsIgnoreCase("admin")) {
					return true;
				}
				
				if(role.equalsIgnoreCase("researcher")) {
					return Customizedcontains(jsonRooms.getResearcher(),replacedRoom);
				}
					
				if(role.equalsIgnoreCase("student")) {
					return Customizedcontains(jsonRooms.getStudent(),replacedRoom);
				}
					
				if(role.equalsIgnoreCase("public")) {
					return Customizedcontains(jsonRooms.getPublicUser(),replacedRoom);
				}
					
				else {
					return false;
				}
				
				
					
				

			} catch (Exception e) {
				return false;
			} 
		

		}else {
			return false;
		}
	}









	public  HashMap<String,Object> getDfaultRoomsByRole(String role){
		HashMap<String,Object> editedJson = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		JsonRooms jsonRooms;
		List <Object> adminRooms = new ArrayList<Object>();
		try {
			ClassPathResource classPathResource = new ClassPathResource("/static/Rooms.json");
			InputStream inputStream = classPathResource.getInputStream();
			jsonRooms =  mapper.readValue(inputStream, JsonRooms.class);

			

			switch (role) {
			case "admin":
				adminRooms.addAll(jsonRooms.getAdmin());
				adminRooms.addAll(jsonRooms.getResearcher());
				adminRooms.addAll(jsonRooms.getStudent());
				adminRooms.addAll(jsonRooms.getPublicUser());

				editedJson.put("admin",adminRooms);
				break;
			case "researcher":
				editedJson.put("researcher",jsonRooms.getResearcher());
				break;
			case "student":
				editedJson.put("student",jsonRooms.getStudent());
				break;
			case "public":
				editedJson.put("public",jsonRooms.getPublicUser());
				break;

			default:
				editedJson.put("error","Permission denied");
				break;
			}

		}  catch (Exception e) {
		
			editedJson.put("error","Not found");
		}

		return editedJson;

	}


	public HashMap<String,Object> getRoom (String role,String room){

		HashMap<String,Object> editedJson = new HashMap<String,Object>();

		if(PermissionCheck(role,room)) {

			List<HashMap<String,Object>> metrics = new ArrayList<HashMap<String,Object>>();
			ObjectMapper mapper = new ObjectMapper();
			String url = "https://api.usb.urbanobservatory.ac.uk/api/v2.0a/sensors/entity/"+room;

			URL urlRequest;
			BufferedReader br;
			StringBuilder sb = new StringBuilder();
			HttpURLConnection urlConnection;
			boolean success = false;
			while(!success) {

				try {
					Entry jsonEntry;   
					HashMap<String,Object>  metric;


					urlRequest = new URL(url);

					urlConnection = (HttpURLConnection) urlRequest.openConnection();
					urlConnection.setRequestMethod("GET"); 
					urlConnection.setConnectTimeout(10000); 
					urlConnection.setReadTimeout(10000);
					if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
						success = true;
						br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
						String line;
						while ((line = br.readLine()) != null) {
							sb.append(line).append("\n");
						}
						br.close();	
						jsonEntry =  mapper.readValue(sb.toString(), Entry.class); 


						for(int i=0; i<jsonEntry.getFeed().size();i++) {
							metric = new HashMap<String,Object>();
							metric.put("name",jsonEntry.getFeed().get(i).getMetric());
							metric.put("value",jsonEntry.getFeed().get(i).getTimeseries().get(0).getLatest()==null ? null : jsonEntry.getFeed().get(i).getTimeseries().get(0).getLatest().getValue() );	
							metrics.add(metric);
						}

						editedJson.put("name", jsonEntry.getName());  
						editedJson.put("metrics", metrics);
					} 




				} catch (Exception e) {
					editedJson.put("error", "Not Found");
					return editedJson;


				}
			}


		}else {
			editedJson.put("error", "Permission denied");
		}





		return editedJson;

	}




	public HashMap<String,Object> getTimeseries (String role,String room,String metric,String start,String end){
		HashMap<String,Object> editedJson = new HashMap<String,Object>();

		if(PermissionCheck(role,room)) {

			List<HashMap<String,Object>> values = new ArrayList<HashMap<String,Object>>();
			String url = "https://api.usb.urbanobservatory.ac.uk/api/v2/sensors/timeseries/"+room+"/"+metric+"/raw/historic?startTime="+start+"T00:00:00Z&endTime="+end+"T23:59:59";
			ObjectMapper mapper = new ObjectMapper();
			Timeseries jsonTimeSeries;

			URL urlRequest;
			BufferedReader br;
			StringBuilder sb = new StringBuilder();
			HttpURLConnection urlConnection;
			boolean success = false;

			while(!success) {
				try {
					urlRequest = new URL(url);

					urlConnection = (HttpURLConnection) urlRequest.openConnection();
					urlConnection.setRequestMethod("GET"); 
					urlConnection.setConnectTimeout(10000); 
					urlConnection.setReadTimeout(10000);
					if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
						success = true;
						br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
						String line;
						while ((line = br.readLine()) != null) {
							sb.append(line).append("\n");
						}
						br.close();	

						jsonTimeSeries =  mapper.readValue(sb.toString(), Timeseries.class); 
						HashMap<String,Object>  value;
						for(int i=0; i<jsonTimeSeries.gethistoric().getValues().size();i++) {
							value = new HashMap<String,Object>();
							value.put("time",jsonTimeSeries.gethistoric().getValues().get(i).getTime());
							value.put("value",jsonTimeSeries.gethistoric().getValues().get(i).getValue());	
							values.add(value);
						}
						editedJson.put("name", room); 
						editedJson.put("metric", metric); 
						editedJson.put("start", start);
						editedJson.put("end", end);
						editedJson.put("historic", values);

					} 

				} catch (Exception e) {
					editedJson.put("error", "Unable to provide data within a sensible timeframe. Try requesting less data (QueryTimeout)" );
					url = "https://api.usb.urbanobservatory.ac.uk/api/v2/sensors/timeseries/"+room+"/"+metric+"/raw/historic?startTime="+start+"T00:00:00Z&endTime="+start+"T23:59:59";
				}
			}



		}else {

			editedJson.put("error", "Permission denied");

		}



		return editedJson;    


	}
}
