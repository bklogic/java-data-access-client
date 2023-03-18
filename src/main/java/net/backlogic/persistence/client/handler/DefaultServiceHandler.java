package net.backlogic.persistence.client.handler;

import net.backlogic.persistence.client.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DefaultServiceHandler implements ServiceHandler {
    Logger LOGGER = LoggerFactory.getLogger(DefaultServiceHandler.class);
    private static String HTTP_CONTENT_TYPE = "Content-Type";
    private static String HTTP_ACCEPT = "Accept";
    private static String APPLICATION_JSON = "application/json";
    private String baseUrl;

    private HttpClient httpClient;
    private JsonHandler jsonHandler;
    private ExceptionHandler exceptionHandler;

    public DefaultServiceHandler(String baseUrl) {
        this.baseUrl = baseUrl;
//        this.httpClient = HttpClient.newHttpClient();
        this.jsonHandler = new JsonHandler();
        this.exceptionHandler = new ExceptionHandler(jsonHandler);
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
                    .POST(HttpRequest.BodyPublishers.ofString(this.jsonHandler.toJson(serviceInput)))
                    .build();
            LOGGER.info("URL: {}", url);
            LOGGER.info("INPUT: {}", serviceInput);
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
