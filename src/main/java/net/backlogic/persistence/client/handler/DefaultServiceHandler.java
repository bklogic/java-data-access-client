package net.backlogic.persistence.client.handler;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.backlogic.persistence.client.DataAccessException;

public class DefaultServiceHandler implements ServiceHandler {
    Logger LOGGER = LoggerFactory.getLogger(DefaultServiceHandler.class);
    private static String HTTP_CONTENT_TYPE = "Content-Type";
    private static String HTTP_ACCEPT = "Accept";
    private static String APPLICATION_JSON = "application/json";
    private static String AUTHORIZATION = "Authorization";
    private static String BEARER = "Bearer ";
    private String baseUrl;

    private JsonHandler jsonHandler;
    private ExceptionHandler exceptionHandler;
    private Supplier<String> jwtProvider;

    public DefaultServiceHandler(String baseUrl, JsonHandler jsonHandler) {
        this.baseUrl = baseUrl;
        this.jsonHandler = jsonHandler;
        this.exceptionHandler = new ExceptionHandler(jsonHandler);
        this.jwtProvider = () -> "";
    }

    public void setJwtProvider(Supplier<String> jwtProvider) {
    	this.jwtProvider = jwtProvider;
    }
    
    
    @Override
    public Object invoke(String serviceUrl, Object serviceInput, ReturnType returnType, Class<?> elementType) {
        // url
        String url = this.baseUrl + serviceUrl;

        // post service
        HttpResponse<String> response;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header(HTTP_CONTENT_TYPE, APPLICATION_JSON)
                    .header(HTTP_ACCEPT, APPLICATION_JSON)
                    .header(AUTHORIZATION, BEARER + this.jwtProvider.get())
                    .POST(HttpRequest.BodyPublishers.ofString(this.jsonHandler.toJson(serviceInput)))
                    .build();
            LOGGER.info("URL: {}", url);
            LOGGER.info("INPUT: {}", jsonHandler.toJson(serviceInput));
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            LOGGER.info("STATUS CODE: {}", response.statusCode());
            LOGGER.info("BODY: {}", response.body());            
        } catch (Exception e) {
            LOGGER.error("HTTP Exception", e);
            throw new DataAccessException(DataAccessException.HttpException, "HTTP Exception", e);
        }

        // handle response
        int statusCode = response.statusCode();
        if (statusCode == 200) {
            return this.jsonHandler.toReturnType(response.body(), returnType, elementType);
        }
        else if (statusCode > 0) {
            throw this.exceptionHandler.handleResponse(statusCode, response.body());
        }
        else {
            throw new DataAccessException("ClientHttpException", response.body());
        }
    }

}
