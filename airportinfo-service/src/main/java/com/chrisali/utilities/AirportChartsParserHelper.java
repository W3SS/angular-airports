package com.chrisali.utilities;

import java.io.IOException;

import com.chrisali.model.airportinfo.AirportCharts;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AirportChartsParserHelper {

    /**
    *  Removes dynamic root node from AirportCharst JSON, and then maps to an
    *  AirportCharts object
    *  @param rawJson
    *  @param airportIdentifier - either 4 letter ICAO or 3 letter US codes (N87, *         N40, etc)
    *  @return AirportCharts object
    */
    public static AirportCharts getAirportChartsFromJson(String rawJson, String airportIdentifier) throws IllegalArgumentException, IOException {
        if (airportIdentifier.length() > 4 || airportIdentifier.length() < 3)
            throw new IllegalArgumentException("Airport identifier must be either 3 or 4 characters long!");

        ObjectMapper objectMapper = new ObjectMapper();

        int startIndex = airportIdentifier.length() == 4 ? 8 : 7;
        rawJson = rawJson.substring(startIndex, rawJson.length()-1);

        if (rawJson.contains("false"))
            return null;

        AirportCharts charts = objectMapper.readValue(rawJson, AirportCharts.class);

        return charts;
    }
}
