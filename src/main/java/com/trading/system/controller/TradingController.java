package com.trading.system.controller;

import com.trading.system.exception.ExceptionDetails;
import com.trading.system.integration.TradingService;
import com.trading.system.model.data.ApiResponseWrapper;
import com.trading.system.util.CommonUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
public class TradingController {

    @Autowired
    private TradingService tradingService;

    /**
     * Login Api
     * @param userName
     * @param password
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestParam(value="username") String userName , @RequestParam(value="password") String password) throws Exception {
        ApiResponseWrapper apiResponseWrapper = new  ApiResponseWrapper();
        apiResponseWrapper =  tradingService.login(userName, password);
        return ResponseEntity.ok(apiResponseWrapper);
    }


    /**
     * Get account details
     * @param authorization
     * @return
     * @throws Exception
     */
    @GetMapping("/get/account/details")
    public ResponseEntity<?> accountDetails(@RequestHeader(value="Authorization") Optional<String> authorization) throws Exception {
        ApiResponseWrapper apiResponseWrapper = new  ApiResponseWrapper();
        if(!authorization.isPresent() && CommonUtility.isStringEmpty(authorization.get())){
            apiResponseWrapper.setErrorCode(CommonUtility.generateError(ExceptionDetails.USER_NOT_FOUND));
            return ResponseEntity.ok(apiResponseWrapper);
        }
        apiResponseWrapper =  tradingService.getAccountDetails(authorization.get());
        return ResponseEntity.ok(apiResponseWrapper);
    }


    /**
     * get executed orders
     * @param authorization
     * @return
     * @throws Exception
     */
    @GetMapping("/my/executed/orders")
    public ResponseEntity<?> executedOrders(@RequestHeader(value="Authorization") Optional<String> authorization) throws Exception {
        ApiResponseWrapper apiResponseWrapper = new  ApiResponseWrapper();
        if(!authorization.isPresent() && CommonUtility.isStringEmpty(authorization.get())){
            apiResponseWrapper.setErrorCode(CommonUtility.generateError(ExceptionDetails.USER_NOT_FOUND));
            return ResponseEntity.ok(apiResponseWrapper);
        }
        apiResponseWrapper =  tradingService.executedOrder(authorization.get());
        return ResponseEntity.ok(apiResponseWrapper);
    }

    /**
     * instrument Live  price
     * @param authorization
     * @return
     * @throws Exception
     */
    @GetMapping("/instrument/live/prices")
    public ResponseEntity<?> livePrice(@RequestHeader(value="Authorization") Optional<String> authorization) throws Exception {
        ApiResponseWrapper apiResponseWrapper = new  ApiResponseWrapper();
        if(!authorization.isPresent() && CommonUtility.isStringEmpty(authorization.get())){
            apiResponseWrapper.setErrorCode(CommonUtility.generateError(ExceptionDetails.USER_NOT_FOUND));
            return ResponseEntity.ok(apiResponseWrapper);
        }
        apiResponseWrapper =  tradingService.livePrice();
        return ResponseEntity.ok(apiResponseWrapper);
    }


    /**
     * transact orders
     * @param authorization
     * @param symbolId
     * @param quantity
     * @param price
     * @return
     * @throws Exception
     */
    @PostMapping("/transact/orders")
    public ResponseEntity<?> placeOrder(@RequestHeader(value="Authorization") Optional<String> authorization ,
                                        @RequestParam(value="symbol_id") Optional<String> symbolId ,
                                        @RequestParam(value="quantity") Optional<Integer> quantity,
                                        @RequestParam(value="price")  Optional<Double> price) throws Exception {
        ApiResponseWrapper apiResponseWrapper = new  ApiResponseWrapper();
        if(!authorization.isPresent() && CommonUtility.isStringEmpty(authorization.get())){
            apiResponseWrapper.setErrorCode(CommonUtility.generateError(ExceptionDetails.USER_NOT_FOUND));
            return ResponseEntity.ok(apiResponseWrapper);
        }
        apiResponseWrapper =  tradingService.updateOrder(symbolId, authorization, quantity, price);
        return ResponseEntity.ok(apiResponseWrapper);
    }


}
