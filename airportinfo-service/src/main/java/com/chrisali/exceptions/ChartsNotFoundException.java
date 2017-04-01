package com.chrisali.controllers;

import com.chrisali.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Airport Charts Not Found")
public class ChartsNotFoundException extends Exception {
    public ChartsNotFoundException(String icaoCode) {
        super ("No charts found for airport with ICAO code: " + icaoCode + "!");
    }
}