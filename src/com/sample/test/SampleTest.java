package com.sample.test;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SampleTest {

	public static void main(String[] rgs) {
		String jsonstr = "{\"cropName\":\"Crop1\",\"id\":\"9564\",\"localPrice\":\"3.885\"}";
		ObjectMapper mapper = new ObjectMapper();
		KeywordPOJO staff1;
		try {
			staff1 = mapper.readValue(jsonstr, KeywordPOJO.class);
			System.out.println("staff1:: " + staff1.toString());
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
