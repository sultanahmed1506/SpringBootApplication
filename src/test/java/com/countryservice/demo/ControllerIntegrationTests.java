package com.countryservice.demo;

import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.countryservice.demo.beans.Country;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class ControllerIntegrationTests {

	@Test
	@Order(1)
	void getAllCountriesIntegrationTest() throws Exception {
		
		String file = "src/main/resources/country.json";
        String expected = readFileAsString(file);
		
		/*
		 * String expected = "[\r\n" + "    {\r\n" + "        \"id\": 1,\r\n" +
		 * "        \"countryName\": \"Bangladesh\",\r\n" +
		 * "        \"countryCapital\": \"Dhaka\"\r\n" + "    },\r\n" + "    {\r\n" +
		 * "        \"id\": 2,\r\n" + "        \"countryName\": \"USA\",\r\n" +
		 * "        \"countryCapital\": \"Washington\"\r\n" + "    }\r\n" + "]";
		 */
		
		String url = "http://localhost:8082/getcountries";
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response =restTemplate.getForEntity(url, String.class);
		 
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		
		JSONAssert.assertEquals(expected, response.getBody(), false);
		
	}
	
	@Test
	@Order(2)
	void getCountryByIDIntegrationTest() throws JSONException {
		
		String expected = "{\r\n" + 
				"    \"id\": 1,\r\n" + 
				"    \"countryName\": \"Bangladesh\",\r\n" + 
				"    \"countryCapital\": \"Dhaka\"\r\n" + 
				"}";
		
		String url = "http://localhost:8082/getcountries/1";
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response =restTemplate.getForEntity(url, String.class);
		 
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		
		JSONAssert.assertEquals(expected, response.getBody(), false);
		
	}

	@Test
	@Order(3)
	void getCountryByNameIntegrationTest() throws JSONException {
		
		String expected = "{\r\n" + 
				"    \"id\": 2,\r\n" + 
				"    \"countryName\": \"USA\",\r\n" + 
				"    \"countryCapital\": \"Washington\"\r\n" + 
				"}";
		
		String url = "http://localhost:8082/getcountries/countryname?name=USA";
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response =restTemplate.getForEntity(url, String.class);
		 
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		
		JSONAssert.assertEquals(expected, response.getBody(), false);
		
	}
	
	@Test
	@Order(4)
	void addCountryIntegrationTest() throws JSONException {
		
		Country country =new Country(3,"Germany","Berlin");
		String url = "http://localhost:8082/addcountry";
		String expected = "{\r\n" + 
				"    \"id\": 3,\r\n" + 
				"    \"countryName\": \"Germany\",\r\n" + 
				"    \"countryCapital\": \"Berlin\"\r\n" + 
				"}";
		
		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Country> requestbody = new HttpEntity<Country>(country,headers);
		
		ResponseEntity<String> response =restTemplate.postForEntity(url, requestbody, String.class);
		
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		
		assertEquals(HttpStatus.CREATED,response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);
		
		
	}

	
	@Test
	@Order(5)
	void updateCountryIntegrationTest() throws JSONException {
		
		Country country =new Country(3,"Japan","Tokyo");
		String url = "http://localhost:8082/updatecountry/3";
		String expected = "{\r\n" + 
				"    \"id\": 3,\r\n" + 
				"    \"countryName\": \"Japan\",\r\n" + 
				"    \"countryCapital\": \"Tokyo\"\r\n" + 
				"}";
		
		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Country> requestbody = new HttpEntity<Country>(country,headers);
		
		ResponseEntity<String> response =restTemplate.exchange(url, HttpMethod.PUT,requestbody, String.class);
		
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		
		assertEquals(HttpStatus.OK,response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);
		
		
	}
	
	@Test
	@Order(6)
	void deleteCountryIntegrationTest() throws JSONException {
		
		Country country =new Country(3,"Japan","Tokyo");
		String url = "http://localhost:8082/deletecountry/3";
		String expected = "{\r\n" + 
				"    \"id\": 3,\r\n" + 
				"    \"countryName\": \"Japan\",\r\n" + 
				"    \"countryCapital\": \"Tokyo\"\r\n" + 
				"}";
		
		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Country> requestbody = new HttpEntity<Country>(country,headers);
		
		ResponseEntity<String> response =restTemplate.exchange(url, HttpMethod.DELETE,requestbody, String.class);
		
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		
		assertEquals(HttpStatus.OK,response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);
		
		
	}
	
	
	public static String readFileAsString(String file)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }
	
}
