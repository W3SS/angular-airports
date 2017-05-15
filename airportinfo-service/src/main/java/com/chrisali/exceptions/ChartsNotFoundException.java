package com.chrisali.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Airport Charts Not Found")
public class ChartsNotFoundException extends Exception {

	private static final long serialVersionUID = 6676945682925669976L;

	public ChartsNotFoundException(String icaoCode) {
        super ("No charts found for airport with ICAO code: " + icaoCode + "!");
    }
}