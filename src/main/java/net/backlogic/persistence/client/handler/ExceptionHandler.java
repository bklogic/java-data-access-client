package net.backlogic.persistence.client.handler;

import net.backlogic.persistence.client.DataAccessException;

public class ExceptionHandler {

    private JsonHandler jsonHandler;

    public ExceptionHandler(JsonHandler jsonHandler) {
        this.jsonHandler = jsonHandler;
    }

    public DataAccessException handleResponse(int statusCode, String body) {
        DataAccessException exception;

        switch (statusCode) {
            case 403:
                exception = new DataAccessException(DataAccessException.ServiceException, statusCode + " - " + body);
                break;
            default:
                exception = this.jsonHandler.readException(body);
        }
        return exception;
    }

}
