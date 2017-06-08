package com.chrisali.loaders;

import com.chrisali.model.airportinfo.Airport;
import com.chrisali.repositories.airportinfo.AirportRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@Component
public class AirportLoader implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private AirportRepository airportRepository;
	
	private Logger log = Logger.getLogger(AirportLoader.class);
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		try {
			log.info("Loading airports.dat...");
			
			Resource resource = resourceLoader.getResource("classpath:airports.dat");
			InputStream is = resource.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			log.info("Inserting airport data into database...");
			
			int insertedCount = 0;
			String lineRead;

			while ((lineRead = br.readLine()) != null) {
				parseAndInsert(lineRead.split(","));
				insertedCount++;
			}
			
			log.info("Successfully inserted " + insertedCount + " airports into database");
			
		} catch (IOException | NullPointerException e) {
			log.error("Could not insert airports into database: ", e);
		}
	}
	
	private void parseAndInsert(String[] splitLine) {
		if (splitLine != null) {
			Airport airport = new Airport();

			airport.setAirportName(splitLine[1].compareTo("/N") == 0 ? "" : splitLine[1]);
			airport.setCity(splitLine[2].compareTo("/N") == 0 ? "" : splitLine[2]);
			airport.setCountry(splitLine[3].compareTo("/N") == 0 ? "" : splitLine[3]);
			airport.setIataCode(splitLine[4].compareTo("/N") == 0 ? "" : splitLine[4]);
			airport.setIcaoCode(splitLine[5].compareTo("/N") == 0 ? "" : splitLine[5]);
			airport.setLatitude(splitLine[6].compareTo("/N") == 0 ? 0 : Float.valueOf(splitLine[6]));
			airport.setLongitude(splitLine[7].compareTo("/N") == 0 ? 0 : Float.valueOf(splitLine[7]));
			airport.setAltitude(splitLine[8].compareTo("/N") == 0 ? 0 : Float.valueOf(splitLine[8]));
			airport.setTimeZone(splitLine[9].compareTo("/N") == 0 ? 0 : Float.valueOf(splitLine[9]));
			airport.setTzTimeZone(splitLine[11].compareTo("/N") == 0 ? "" : splitLine[11]);
			
			airportRepository.save(airport);
		}
	}
}