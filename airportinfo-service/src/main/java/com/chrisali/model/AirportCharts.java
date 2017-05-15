package com.chrisali.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
//@JsonRootName(value = "rootTagInJson") // how to get this dynamically
public class AirportCharts {
    
    protected Map<String, Object> info;

    private AirportChart[] charts;

    @Data
    static class AirportChart {
        private String id;

        private String type;

        private String name;

        private String url;
    }
}