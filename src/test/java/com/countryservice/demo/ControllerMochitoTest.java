package com.countryservice.demo;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.controllers.CountryController;
import com.countryservice.demo.services.CountryService;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes=ControllerMochitoTest.class)
public class ControllerMochitoTest {
	
	@Mock
	CountryService countryService;
	
	@InjectMocks
	CountryController countryController;
	
	List<Country> mycountries;
	Country country;
	
	@Test
	@Order(1)
	public void test_getAllCountries() {
		
		mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1,"Bangladesh","Dhaka"));
		mycountries.add(new Country(1,"Canada","Ottawa"));
		
		//Mocking
		when(countryService.getAllCountries()).thenReturn(mycountries);
		ResponseEntity<List<Country>> res =countryController.getCountries();
		assertEquals(HttpStatus.FOUND,res.getStatusCode());
		assertEquals(2,res.getBody().size());
	}
	
	@Test
	@Order(2)
	public void test_getCountryByID() {
		
		country = new Country(2,"USA","Washington");
		int countryID = 2;
		//Mock statement
		when(countryService.getCountrybyID(countryID)).thenReturn(country);
		ResponseEntity<Country> res=countryController.getCountryById(countryID);
		
		assertEquals(HttpStatus.FOUND,res.getStatusCode());
		System.out.println("StatusCode : "+ res.getStatusCode());
		assertEquals(countryID, res.getBody().getId());
		
	}
	
	@Test
	@Order(3)
	public void test_getCountryByName() {
		
		country = new Country(2,"USA","Washington");
		String countryName = "USA";
		when(countryService.getCountrybyName(countryName)).thenReturn(country);
		ResponseEntity<Country>  res = countryController.getCountryByName(countryName);
		
		assertEquals(HttpStatus.FOUND, res.getStatusCode());
		assertEquals(countryName, res.getBody().getCountryName());
		
		
	}
	
	@Test
	@Order(4)
	public void test_addCountry() {
		
		country = new Country(3,"Italy","Rome");
		when(countryService.addCountry(country)).thenReturn(country);
		ResponseEntity<Country> res =countryController.addCountry(country);
		
		assertEquals(HttpStatus.CREATED,res.getStatusCode());
		assertEquals(country, res.getBody());
	}
	
	@Test
	@Order(5)
	public void test_updateCountry() {
		
		//Given
		country = new Country(3,"Japan","Tokyo");
		int countryID = 3;
		//When
		//Mock 
		when(countryService.getCountrybyID(countryID)).thenReturn(country);
		when(countryService.updateCountry(country)).thenReturn(country);
		//Execuate the controller
		ResponseEntity<Country> res=countryController.updateCountry(countryID, country);
		//Then
		//Assert
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(countryID, res.getBody().getId());
		assertEquals("Japan", res.getBody().getCountryName());
		assertEquals("Tokyo", res.getBody().getCountryCapital());
		
	}
	
	@Test
	@Order(6)
	public void test_deleteCountry() {
		
		country = new Country(3,"Japan","Tokyo");
		int countryID = 3;
		
		when(countryService.getCountrybyID(countryID)).thenReturn(country);
		ResponseEntity<Country> res=countryController.deleteCountry(countryID);
		
		assertEquals(HttpStatus.OK, res.getStatusCode());
	}
	
	
	
	
	
	
	
	
	
	

}
