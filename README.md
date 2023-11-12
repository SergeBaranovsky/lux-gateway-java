# Coding Assignment

## Decription
Test project implementing the coding assignment following the **Instructions** below.

`lux-rest-app` is the dummy application with one POST endpoint.

`lux-gateway-java` is the Spring Cloud Gateway service for routing API calls to the REST API above.

## NOTES
1. Only local deployment profile at this point
2. Did not include unit tests
3. A single profile, didn't implement application-dev/application-prod
4. Log files are written in the project directory - need to move
5. Logging pattern in the file - need to modify to remove the package name, etc

## Deviation from the REQs
- The jwt authentication is done via Spring Security `SecurityWebFilterChain`, not in a Gateway pre-filter.
- The Gateway service is implemented in Java to meet the timing requirement, more details in email. Will convert to Kotlin once figure out the (likely) conflicting dependecies preventing the start of the ApplicationContext.

## Instructions
1. Run `lux-rest-app` - configured on port 8088
2. Run `lux-gateway-java` - configured on port 8080
3. I used curl and Insomnia for testing but also created PS scripts - lux-gateway-java/docs/ps-scripts.txt 

## Current Limitations
- Logging is omitted with the exception of the required logging in the custom pre-filter
- Exception handling very basic if any
- Does not include tests

## Coding Task
1. Create a dummy application with one POST endpoint which returns static data (Java/Spring/Gradle)
2. Create a separate Spring Cloud Gateway service (Kotlin/Spring/Gradle) which
    1. Has an endpoint for generating jwt auth token
    2. Routes API calls to the first service (file for routers, filters, etc) 
    3. Routes must be configured via application.yml file (post request, logging, validate jwt, post filter)
    4. Develop a custom pre-filter that logs the request details before it is routed to the downstream service. The details should include the HTTP method, URI, and any headers that are relevant for auditing purposes.
    5. Develop a custom pre-filter that validate jwt token from headers. If token is missing or invalid, then the request must be forbidden
    6. Create a post-filter that modifies the response headers by adding a custom header `X-Custom-Header` with a value of your choice.


