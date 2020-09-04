package com.trading.system.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.system.exception.ExceptionDetails;
import com.trading.system.exception.ServerApplicationException;
import com.trading.system.model.data.Errors;
import com.trading.system.model.data.UiMessage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class CommonUtility {

    /**
     * Check String in empty
     * @param input
     * @return
     */
    public static boolean isStringEmpty(String input) {
        boolean empty = true;
        if (input != null && !input.trim().equalsIgnoreCase("")) {
            empty = false;
        }
        return empty;

    }

    public static float doubleToFloat(double val ){
        BigDecimal number = new BigDecimal(val).setScale(2, RoundingMode.HALF_UP);;
        return number.floatValue();
    }

    /**
     * Convert String to integer
     * @param inputSource
     * @return
     */
    public static int convertStringToInteger(String inputSource) {
        if (CommonUtility.isStringEmpty(inputSource)) {
            return 0;
        }
        try {
            return Integer.parseInt(inputSource.trim().toString());
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Convert Custom Exception to error Object
     * @param e : ServerApplicationException
     * @return : Errors
     */
    public static Errors getErrorObject(ServerApplicationException e){
        Errors error = new Errors();
        ArrayList<UiMessage> UiError = new ArrayList<UiMessage>();
        UiMessage errorObject = new UiMessage();
        error.setHasError(true);
        errorObject.setCode(e.getErrorCode());
        errorObject.setMessage(e.getUiExpectionMessage(e.getErrorCode(), e.getParams()));
        UiError.add(errorObject);
        error.setErrorDefinitions(UiError);
        return  error;

    }

    /**
     * Get Exception error code  from Exception id
     * @param exceptionId : integer
     * @return : Errors
     */
    public static Errors generateError(int exceptionId ){
        Errors error = new Errors();
        ArrayList<UiMessage> UiError = new ArrayList<UiMessage>();
        UiMessage errorObject = new UiMessage();
        error.setHasError(true);
        errorObject.setCode(exceptionId);
        String[] params = {};
        errorObject.setMessage(new ServerApplicationException().getUiExpectionMessage(exceptionId, params));
        UiError.add(errorObject);
        error.setErrorDefinitions(UiError);
        return  error;
    }

    public static Errors generateError(int exceptionId , String params[]){
        Errors error = new Errors();
        ArrayList<UiMessage> UiError = new ArrayList<UiMessage>();
        UiMessage errorObject = new UiMessage();
        error.setHasError(true);
        errorObject.setCode(exceptionId);
        errorObject.setMessage(new ServerApplicationException().getUiExpectionMessage(exceptionId, params));
        UiError.add(errorObject);
        error.setErrorDefinitions(UiError);
        return  error;
    }

    /**
     * Convert Java Object to Json object
     * @param object
     * @param <T>
     * @return
     * @throws ServerApplicationException
     */
    public static <T> String toJson(T object) throws ServerApplicationException {
        String json = null;
        try {
            ObjectMapper jsonObjMapper = new ObjectMapper();
            jsonObjMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            json = jsonObjMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ServerApplicationException(ExceptionDetails.UNAPPRECIATED_PARAMS, "JsonProcessingException - Unable to create Json String." + e.getMessage());
        }
        return json;
    }

    /**
     * convert json to java object
     * @param jsonString
     * @param object
     * @param <T>
     * @return
     */
    public static <T> T jsonToObject(String jsonString, T object) {
        System.out.println(jsonString);
        String response = jsonString;
        JsonParser parser = null;
        T resource = null;
        try {
            JsonFactory streamingJsonFactory = new MappingJsonFactory();
            JsonFactory streamFactory = streamingJsonFactory;
            parser = streamFactory.createParser(response);

            resource = parser.readValueAs((Class<T>) object.getClass());
        } catch (JsonParseException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (parser != null)
                    parser.close();
            } catch (IOException e) {

            }
        }
        if (resource != null) {
            return resource;
        } else {
            return object;
        }

    }
}
