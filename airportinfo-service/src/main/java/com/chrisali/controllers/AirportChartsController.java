package com.chrisali.controllers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.chrisali.exceptions.ChartsNotFoundException;
import com.chrisali.model.airportinfo.AirportCharts;
import com.chrisali.utilities.AirportChartsParserHelper;

@RestController
@RequestMapping("/airports/")
@CrossOrigin(origins = "http://localhost:8080")
public class AirportChartsController {
    
    private static final String URL_PREFIX = "https://api.aircharts.org/v2/Airport/";

    @RequestMapping(value = "charts/icao/{icaoCode}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public AirportCharts getChartByIcao(@PathVariable String icaoCode) throws ChartsNotFoundException, IOException {
        String urlToHit = URL_PREFIX + icaoCode;

        RestTemplate restTemplate = new RestTemplate();
        AirportCharts charts = null;

        try {
		    String rawJson = restTemplate.getForObject(urlToHit, String.class);
            charts = AirportChartsParserHelper.getAirportChartsFromJson(rawJson, icaoCode);

            if (charts == null || charts.getInfo() == null) 
                throw new ChartsNotFoundException(icaoCode);

        } catch (IllegalArgumentException ex) {
            throw new ChartsNotFoundException(icaoCode);
        } catch (IOException ex) {
            throw new IOException("Unable to read parsed data! \n" + ex.getMessage());
        } 
        
        return charts;
    }
}