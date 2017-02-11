# Angular Airports - An Airport Search Tool SPA Using Spring Boot and Angular 1.x

## About

- This is a simple SPA that allows the user to search a list of cities for an airport. 

- Selecting an airport then displays basic information about the airport, the current weather and a map of the airspace surrounding it.

- Angular JS 1.x is used on the front end, with Spring Boot providing a Tomcat instance to run the SPA

- Spring Boot is used in the back end as well as a separate REST service to handle all H2 interactions

## Usage

- cd into the airportinfo and angularui directories and run them using mvn spring-boot:run

## Sources

- NOAA's Aviation Digital Data Service (ADDS) Text Data Server is used to provide weather info ( http://aviationweather.gov/dataserver )

- A data dump from OpenFlights' airport database provides data to seed the app's H2 database ( http://openflights.org/data.html )

- VFR MAP's API provides airport airspace map images, which link back to their site ( http://vfrmap.com/map_api.html )