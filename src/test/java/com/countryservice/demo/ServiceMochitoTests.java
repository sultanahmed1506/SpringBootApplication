package com.countryservice.demo;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.repositories.CountryRepository;
import com.countryservice.demo.services.CountryService;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes=ServiceMochitoTests.class)
public class ServiceMochitoTests {
	
	@Mock
	CountryRepository countryrep;
	
	@InjectMocks
	CountryService countryService;
	
	public List<Country> mycountries;

	@Test
	@Order(1)
	public void test_getAllCountries() {
		
		//Given
		List<Country> mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1,"Bangladesh","Dhaka"));
		mycountries.add(new Country(2,"USA","Washington"));
		
		//When
		//Mocking
		when(countryrep.findAll()).thenReturn(mycountries);
		//Then
		//Asserting
		assertEquals(2,countryService.getAllCountries().size());
		
	}
	
	@Test()
	@Order(2)
	public void test_getCountryById(){	
		List<Country> mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1,"Bangladesh","Dhaka"));
		mycountries.add(new Country(2,"USA","Washington"));
		
		int countryID=1; //Validate CountryID = 1
		
		when(countryrep.findAll()).thenReturn(mycountries); //Mocking
		
		assertEquals(countryID, countryService.getCountrybyID(countryID).getId());
		
	}
	
	@Test()
	@Order(3)
	public void test_getCountryByName(){	
		List<Country> mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1,"Bangladesh","Dhaka"));
		mycountries.add(new Country(2,"USA","Washington"));
		
		String countryName="Bangladesh"; //Validate CountryName = 1
		
		when(countryrep.findAll()).thenReturn(mycountries); //Mocking
		
		assertEquals(countryName, countryService.getCountrybyName(countryName).getCountryName());
		
	}
	
	@Test()
	@Order(4)
	public void test_addCountry(){
		Country country = new Country(3,"Germany","Berlin");
		when(countryrep.save(country)).thenReturn(country);
		assertEquals(country, countryService.addCountry(country));
		
	}
	
	@Test()
	@Order(5)
	public void test_updateCountry(){
		Country country = new Country(3,"UK","London");
		when(countryrep.save(country)).thenReturn(country);
		assertEquals(country, countryService.updateCountry(country));
		
	}
	
	@Test()
	@Order(6)
	public void test_deleteCountry(){
		
		Country country = new Country(3,"UK","London");
		countryService.deleteCountry(country);
		verify(countryrep,times(1)).delete(country); //This is assertion and mocking
	}
	
}
