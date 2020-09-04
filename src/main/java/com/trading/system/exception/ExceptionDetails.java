package com.trading.system.exception;

import java.util.HashMap;
import java.util.Map;

public class ExceptionDetails {
    public Map<Integer, String> uiExecptionlist = new HashMap<Integer, String>();
    // General
    public static int FILE_NOT_FOUND = 14010001;
    public static int DB_CONNECTION_ERROR = 14010002;
    public static int DB_TRANSACTION_ERROR = 14010003;
    public static int GENERAL_ERROR = 14010004;
    public static int UNAPPRECIATED_PARAMS = 14010006;
    public static int IMAGES_NOT_ABLE_TO_SAVE = 14010007;
    public static int USER_NOT_FOUND = 14010008;
    public static int SYMBOL_ID_NOT_EXIST = 14010009;
    public static int ACCOUNT_ID_NOT_EXIST = 14010010;


    public ExceptionDetails(){
        this.prepareModelException();
    }

    public void prepareModelException() {
        // General
        uiExecptionlist.put(FILE_NOT_FOUND, "Failed to find file at location {0}");
        uiExecptionlist.put(DB_TRANSACTION_ERROR, "Error while DB transaction"); // general
        uiExecptionlist.put(DB_CONNECTION_ERROR, "Error while Connecting to DB"); // general
        uiExecptionlist.put(GENERAL_ERROR, "{0}"); // general
        uiExecptionlist.put(UNAPPRECIATED_PARAMS, "Unappreciated input parameter"); // general
        uiExecptionlist.put(IMAGES_NOT_ABLE_TO_SAVE, "Image not able to save"); // general
        uiExecptionlist.put(USER_NOT_FOUND, "User Not Found"); // general
        uiExecptionlist.put(SYMBOL_ID_NOT_EXIST, "symbolId is not exist or not valid"); // general
        uiExecptionlist.put(ACCOUNT_ID_NOT_EXIST, "Account is not exist or not valid"); // general
    }
}
