package com.project_do.doola;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
public class HotelController {
	
	@Autowired
	private Hotel hotel;
	@RequestMapping("/")
	public String healthcheck() {
		return "Ola Amigos !!";
	}
	@RequestMapping("/get")
	public String getPerson(@RequestParam(name="latlong", required=false, defaultValue="Unknown") String latlong) throws JSONException{
		
		//hotel.setCoordinates(latlong);
		System.out.println(latlong);
		String [] aStr = latlong.split(" ", 4);
		
		final String uri = "https://www.oyorooms.com/api/search/hotels?additional_fields=category_info%2Ccancellation_policies%2Cbest_image%2Croom_pricing%2Cavailability%2Camenities%2Crestrictions%2Ccategory%2Ccaptains_info%2Cnew_applicable_filters%2Cadditional_charge_info%2Cimages%2Chotel_images%2Cguest_ratings&available_room_count%5Bcheckin%5D={checkin}&available_room_count%5Bcheckout%5D={checkout}&available_room_count%5Bmin_count%5D=1&fields=id%2Cname%2Ccity%2Cstreet%2Ccategory%2Cgeo_location%2Ccategory%2Chotel_type%2Calternate_name%2Cshort_address&filters%5Bcoordinates%5D%5Blatitude%5D={lat}&filters%5Bcoordinates%5D%5Blongitude%5D={lon}&filters%5Ball_room_categories%5D=true&format_response%5Bbatch%5D%5Bcount%5D=20&format_response%5Bbatch%5D%5Boffset%5D=0&format_response%5Bsort_params%5D%5Bsort_on%5D=&format_response%5Bsort_params%5D%5Bascending%5D=true&pre_apply_coupon_switch=true&rooms_config=1%2C0%2C0&source=Web+Booking";
		Map<String,Object> urv = new HashMap<>();
		String inDate = aStr[2];
		System.out.println(inDate);
		String outDate = aStr[3];
	//	String indate = "18%2F06%2F2018";
	//	String outDate = "19%2F06%2F2018";
		String lat = aStr[0];
		String lon = aStr[1];
		System.out.println(lat);
		System.out.println(lon);
		urv.put("checkin", inDate);
		urv.put("checkout", outDate);
		urv.put("lat", lat);
		urv.put("lon", lon);
		 
		String gh="";
	    RestTemplate restTemplate = new RestTemplate();
	    String result =  restTemplate.getForObject(uri, String.class, urv);
	    JSONObject jsonObj = new JSONObject(result);
	    
	    JSONArray hotel_array = jsonObj.getJSONArray("hotels");
	    if(hotel_array.length()==0)
	    	return "Idiot in which world r u living, there r no hotels around u !"; 
	    for(int i=0;i<2;i++){
	    	ArrayList<String> arr = new ArrayList<String>();
            JSONObject obj=hotel_array.getJSONObject(i);
            JSONArray arr1 = obj.getJSONArray("reduced_room_pricing");
            JSONObject obj1=obj.getJSONObject("ratings");
            arr.add(obj1.getString("value"));
            arr.add(obj.getString("oyo_id"));
            arr.add(obj.getString("name"));
            arr.add(arr1.getString(0));
            gh = gh+"\n"+"Name: "+arr.get(2)+"\n"+"Oyo_id: "+arr.get(1)+"\n"+"Rating: "+arr.get(0)+"\n"+"Price: "+arr.get(3)+"!";   
        }
	    
	    System.out.println(gh);
	    return gh;
	}
//	@RequestMapping(value="/person/coordinates", method=RequestMethod.POST)
//	public Person updatePerson(@RequestParam(name="name", required=true) String name) {
//		person.setName(name);
//		return person;
//	}
	@RequestMapping("*")
	@ResponseBody
	public String fallbackMethod(){
		return "Item send a valid request";
	}
	@RequestMapping(value="/person/coordinates", method=RequestMethod.POST, consumes = "application/json")
	public Hotel retreiveCoordinates(@RequestBody Hotel p) {
		hotel.setCoordinates(p.getCoordinates());
	
		return hotel;
    }
	

}
