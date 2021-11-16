package com.countryservice.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.controllers.AddResponse;
import com.countryservice.demo.repositories.CountryRepository;

@Component
@Service
public class CountryService {
	
	@Autowired
	CountryRepository countryrep;
	
	/*
	 * static HashMap<Integer, Country> countryIdMap;
	 * 
	 * public CountryService() { countryIdMap = new HashMap<Integer, Country>();
	 * 
	 * Country bangladeshCountry = new Country(1,"Bangladesh","Dhaka"); Country
	 * usaCountry = new Country(2,"USA","Washington"); Country ukCountry = new
	 * Country(3,"UK","London");
	 * 
	 * countryIdMap.put(1, bangladeshCountry); countryIdMap.put(2, usaCountry);
	 * countryIdMap.put(3, ukCountry);
	 * 
	 * }
	 */

	public List<Country> getAllCountries() {
		/*
		 * List countries = new ArrayList(countryIdMap.values()); return countries;
		 */
		return countryrep.findAll();
	}
	
	public Country getCountrybyID(int id) {
		/*
		 * Country country =countryIdMap.get(id); return country;
		 */	
		return countryrep.findById(id).get();
	}
	
	public Country getCountrybyName(String countryName) {
		/*
		 * Country country = null; for(int i:countryIdMap.keySet()) {
		 * if(countryIdMap.get(i).getCountryName().equals(countryName)) {
		 * country=countryIdMap.get(i); } } return country;
		 */	
		List<Country> countries = countryrep.findAll();
		Country country = null;
		for (Country con:countries) {
			if(con.getCountryName().equalsIgnoreCase(countryName))
				country=con;
		}
		return country;
	}
	
	
	public Country addCountry(Country country) {
		/*
		 * country.setId(getMaxId()); countryIdMap.put(country.getId(), country); return
		 * country;
		 */
		country.setId(getMaxId());
		countryrep.save(country);
		return country;
		
	}
	
	public Country updateCountry(Country country) {
		/*
		 * if(country.getId()>0) { countryIdMap.put(country.getId(), country); } return
		 * country;
		 */
		countryrep.save(country);
		return country;
	}
	
	public AddResponse deleteCountry(int id) {
		/*
		 * countryIdMap.remove(id); AddResponse res = new AddResponse();
		 * res.setMsg("Country deleted.."); res.setId(id); return res;
		 */
		countryrep.deleteById(id);
		AddResponse res = new AddResponse();
		res.setMsg("Country deleted..!!");
		res.setId(id);
		return res;
	}
	
	//Utility method to get max id
	public int getMaxId() {
		/*
		 * int max=0; for (int id:countryIdMap.keySet()) { if(max<=id) { max=id; } }
		 * return max+1;
		 */
		return countryrep.findAll().size()+1;
	}
	
}
