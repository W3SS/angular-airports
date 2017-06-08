package com.chrisali.model.airportinfo;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class AirportCharts {
    
    protected Map<String, Object> info;

    protected Map<String, AirportChart[]> charts;

    @Data
    static class AirportChart {
        private String id;

        private String chartname;

        private String url;
        
        private String proxy;
    }
}