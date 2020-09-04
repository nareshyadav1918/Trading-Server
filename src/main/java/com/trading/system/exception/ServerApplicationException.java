package com.trading.system.exception;

import lombok.Data;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class ServerApplicationException extends Exception implements Serializable {
    public ExceptionDetails exceptionDetails  =  new ExceptionDetails();


    private String[] params;
    private int applicationId = 0;
    private int componentId = 0;
    private int errorId = 0;

    // Error code field is a combination of application, component & error Ids
    private int errorCode;

    public ExceptionDetails getxceptionDetailsClassObject(){
        return this.exceptionDetails;
    }

    public String getUiExpectionMessage(int errorCode) {
        String defaultErrorMessage = "No available";
        if (errorCode > 0 && exceptionDetails.uiExecptionlist.containsKey(errorCode)) {
            defaultErrorMessage = exceptionDetails.uiExecptionlist.get(errorCode);
        }
        return defaultErrorMessage;
    }



    public String getUiExpectionMessage(int errorCode, String[] params) {
        String defaultErrorMessage = "No available";
        int counter = 0;
        String replaceKey = "";
        if (errorCode > 0 && exceptionDetails.uiExecptionlist.containsKey(errorCode)) {
            defaultErrorMessage = exceptionDetails.uiExecptionlist.get(errorCode);
            if (params != null && params.length > 0) {
                for (String param : params) {
                    replaceKey = "{" + counter + "}";
                    defaultErrorMessage = defaultErrorMessage.replace(replaceKey, param);
                    counter = counter + 1;
                }
            }
            Pattern pattrenToMatchPlaceHolder = Pattern.compile("^\\{+[0-9]+\\}+$");
            Matcher matcher = pattrenToMatchPlaceHolder.matcher(defaultErrorMessage);

        }
        return defaultErrorMessage;
    }

    public boolean isErrorCodeExist(int errorCode) {
        if (errorCode > 0 && exceptionDetails.uiExecptionlist.containsKey(errorCode)) {
            return true;
        } else {
            return false;
        }
    }

    public ServerApplicationException(int errorCode, String message, String[] params) {
        super(message);
        this.errorCode = errorCode;
        this.params = params;
    }

    public ServerApplicationException(int errorCode, String[] params) {
        super(new ServerApplicationException().getUiExpectionMessage(errorCode, params));
        this.errorCode = errorCode;
        this.params = params;
    }

    public ServerApplicationException(int errorCode) {
        super(new ServerApplicationException().getUiExpectionMessage(errorCode));
        String[] exceptionParams = {};
        this.errorCode = errorCode;
        this.params = exceptionParams;
    }

    public String[] getParams() {
        return this.params;
    }

    public ServerApplicationException() {
        super();

    }

    public ServerApplicationException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServerApplicationException(int componentId, int errorId) {
        super();
        this.applicationId = applicationId;
        this.componentId = componentId;
        this.errorId = errorId;
    }

    public ServerApplicationException(int componentId, int errorId, String message) {
        super(message);
    }



}
