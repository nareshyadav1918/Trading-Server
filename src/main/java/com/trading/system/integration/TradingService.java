package com.trading.system.integration;

import com.trading.system.exception.ExceptionDetails;
import com.trading.system.model.Projection.Account;
import com.trading.system.model.Projection.ExecutionOrder;
import com.trading.system.model.Projection.Stock;
import com.trading.system.model.data.ApiResponseWrapper;
import com.trading.system.model.persistent.AccountDao;
import com.trading.system.model.persistent.ExecutedOrdersDao;
import com.trading.system.model.persistent.StockDao;
import com.trading.system.repository.AccountDaoRepository;
import com.trading.system.repository.ExecutedOrderDaoRepository;
import com.trading.system.repository.StockDaoRepository;
import com.trading.system.util.CommonUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


@Service("TradingService")
public class TradingService {

    @Autowired
    private AccountDaoRepository accountDaoRepository;

    @Autowired
    private ExecutedOrderDaoRepository executedOrderDaoRepository;

    @Autowired
    private StockDaoRepository stockDaoRepository;


    private final double stockMaxMoveIsPercentage = 10.00;

    /**
     * Get account login information
     * @param username
     * @param password
     * @return
     */
    public ApiResponseWrapper login(String username , String password){
        ApiResponseWrapper apiResponseWrapper = new  ApiResponseWrapper();
        Optional<AccountDao> userName  = accountDaoRepository.findByUserNameAndPassword(username,password);
        Map<String , Object> response = new HashMap<>();
        if(!userName.isPresent()){
            apiResponseWrapper.setErrorCode(CommonUtility.generateError(ExceptionDetails.USER_NOT_FOUND));
            response.put("login_success", false);
            apiResponseWrapper.setResponse(response);
            return apiResponseWrapper;
        }
        response.put("login_success", true);
        response.put("token", userName.get().getAccountId());
        response.put("account_id", userName.get().getAccountId());
        response.put("first_name", userName.get().getFirstName());
        response.put("last_name", userName.get().getLastName());
        response.put("email", userName.get().getEmail());
        response.put("phone", userName.get().getPhone());
        response.put("user_name", userName.get().getUserName());
        response.put("password", userName.get().getPassword());
        response.put("main_balance", userName.get().getMainBalance());
        response.put("amount_invested", userName.get().getAmountInvested());
        apiResponseWrapper.setResponse(response);
        return apiResponseWrapper;
    }


    /**
     * Get Account details base on account Number
     * @param accountId :String account
     * @Error USER_NOT_FOUND
     * @return
     */
    public ApiResponseWrapper getAccountDetails(String accountId){
        ApiResponseWrapper apiResponseWrapper = new  ApiResponseWrapper();
        Optional<AccountDao> userName  = accountDaoRepository.findByAccountId(accountId);
        if(!userName.isPresent()){
            apiResponseWrapper.setErrorCode(CommonUtility.generateError(ExceptionDetails.USER_NOT_FOUND));
            return apiResponseWrapper;
        }
        apiResponseWrapper.setResponse(userName.get());
        return apiResponseWrapper;
    }


    /**
     * Get Update List instrument
     * @return
     */
    public ApiResponseWrapper livePrice(){
        ApiResponseWrapper apiResponseWrapper = new  ApiResponseWrapper();
        List<StockDao> liveStock  = stockDaoRepository.findAllData();
        if(liveStock.size() > 0 ){
            apiResponseWrapper.setResponse(liveStock);
        }
        return apiResponseWrapper;
    }


    /**
     * Update Api Order calculate instrument new price and profit and loss
     * @param symbolId
     * @param accountId
     * @param quantity
     * @param price
     * @return
     */
    public ApiResponseWrapper updateOrder(Optional<String> symbolId ,
                                          Optional<String> accountId ,
                                          Optional<Integer> quantity ,
                                          Optional<Double> price){
        ApiResponseWrapper apiResponseWrapper = new  ApiResponseWrapper();
        if(!symbolId.isPresent() || !quantity.isPresent() || !quantity.isPresent() || !price.isPresent()){
            apiResponseWrapper.setErrorCode(CommonUtility.generateError(ExceptionDetails.UNAPPRECIATED_PARAMS));
            return apiResponseWrapper;
        }
        Optional<StockDao> stockDao = stockDaoRepository.findBySymbolCode(symbolId.get());
        if(!stockDao.isPresent() || !stockDao.get().getSymbolCode().equalsIgnoreCase(symbolId.get())){
            apiResponseWrapper.setErrorCode(CommonUtility.generateError(ExceptionDetails.SYMBOL_ID_NOT_EXIST));
            return apiResponseWrapper;
        }
        Optional<AccountDao> accountDetails = accountDaoRepository.findByAccountId(accountId.get());
        if(!accountDetails.isPresent() || !accountDetails.get().getAccountId().equalsIgnoreCase(accountId.get())){
            apiResponseWrapper.setErrorCode(CommonUtility.generateError(ExceptionDetails.ACCOUNT_ID_NOT_EXIST));
            return apiResponseWrapper;
        }
        double stockCurrentPrice = stockDao.get().getStockPrice();
        double minRage  = stockCurrentPrice -  (stockCurrentPrice * stockMaxMoveIsPercentage / 100);
        double maxRage  = stockCurrentPrice + (stockCurrentPrice * stockMaxMoveIsPercentage / 100);
        // Buying Case
        ExecutedOrdersDao executedOrdersDao = new ExecutedOrdersDao();
        executedOrdersDao.setAccountId(accountId.get());
        executedOrdersDao.setSymbolId(symbolId.get());
        executedOrdersDao.setPrice(CommonUtility.doubleToFloat(price.get()));
        executedOrdersDao.setQuantity(quantity.get());
        AccountDao accountDao =  accountDetails.get();
        StockDao stockObject =  stockDao.get();
        if(quantity.get() > 0){
            double buyingPriceValue = price.get() * quantity.get();
            // Buy price will be equal to less that 10% of current Share price
            if(price.get() > stockCurrentPrice || price.get() < minRage){
                String[] param  = {"Price will be range of "+minRage +" And " + stockCurrentPrice};
                apiResponseWrapper.setErrorCode(CommonUtility.generateError(ExceptionDetails.GENERAL_ERROR, param ));
                return apiResponseWrapper;
            }
            // check user have balance to buy this stock
            if(buyingPriceValue > accountDetails.get().getMainBalance()){
                String[] param  = {"You don't have enough balance to buy stock balance available" +accountDetails.get().getMainBalance()};
                apiResponseWrapper.setErrorCode(CommonUtility.generateError(ExceptionDetails.GENERAL_ERROR, param));
                return apiResponseWrapper;
            }

            executedOrdersDao.setTotalPrice(CommonUtility.doubleToFloat(buyingPriceValue));
            float newBalance  =  accountDao.getMainBalance() - CommonUtility.doubleToFloat(buyingPriceValue);
            float amountInvested  =  accountDao.getAmountInvested() + CommonUtility.doubleToFloat(buyingPriceValue);
            accountDao.setMainBalance(newBalance);
            accountDao.setAmountInvested(amountInvested);
            accountDaoRepository.save(accountDao);
        }else{
            //Sale case
            if(price.get() < stockCurrentPrice || price.get() > maxRage) {
                String[] param = {"Price will be range of " + stockCurrentPrice + " And " + maxRage};
                apiResponseWrapper.setErrorCode(CommonUtility.generateError(ExceptionDetails.GENERAL_ERROR, param));
                return apiResponseWrapper;
            }
            double sellingPriceValue = price.get() * Math.abs(quantity.get());
            HashMap<String,Object> returnObject =  this.calculation(symbolId.get(),accountId.get());
            if(returnObject.size() == 0 || !returnObject.containsKey("countPurchasedQyt") || (int) returnObject.get("countPurchasedQyt") < Math.abs(quantity.get())){
                String[] param = {"You does not have such instrument in your holdings or you hold lesser units for the instrument." };
                apiResponseWrapper.setErrorCode(CommonUtility.generateError(ExceptionDetails.GENERAL_ERROR, param));
                return apiResponseWrapper;
            }
            float investedAmountAvg  = (float)  returnObject.get("averagePrice") * Math.abs(quantity.get());
            float newBalance  =  accountDao.getMainBalance() + CommonUtility.doubleToFloat(sellingPriceValue);
            //booking loss

            float amountInvested  =  accountDao.getAmountInvested() - investedAmountAvg;
            accountDao.setMainBalance(newBalance);
            accountDao.setAmountInvested(amountInvested < 0 ? 0 : amountInvested);

            executedOrdersDao.setTotalPrice(CommonUtility.doubleToFloat(sellingPriceValue));
        }

        //account data Save
        accountDao = accountDaoRepository.save(accountDao);

        //Store Date Save
        stockObject.setLastPrice(stockDao.get().getStockPrice());
        stockObject.setStockPrice(this.CalculateAveragePrice(stockDao.get().getSymbolCode(), price.get()));
        stockObject=  stockDaoRepository.save(stockObject);


        //Order data save
        executedOrdersDao.setDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        executedOrdersDao.setAccountObject(accountDao);
        executedOrdersDao.setSymbolObject(stockObject);
        executedOrdersDao = executedOrderDaoRepository.save(executedOrdersDao);
        HashMap<String ,Object > returnObject  = new  HashMap<String ,Object >();
        returnObject.put("transaction_success",true);
        returnObject.put("symbol_id",symbolId.get());
        returnObject.put("symbol_name",stockObject.getSymbolName());
        returnObject.put("quantity",executedOrdersDao.getQuantity());
        returnObject.put("execution_price",executedOrdersDao.getPrice());
        returnObject.put("total_price",executedOrdersDao.getTotalPrice());
        returnObject.put("date",executedOrdersDao.getDate());
        apiResponseWrapper.setResponse(returnObject);
        return apiResponseWrapper;
    }

    /**
     * Calculate instrument price base on last 10 transitions
     * @param symbolId
     * @param currentPrice
     * @return
     */
    private float CalculateAveragePrice(String symbolId , double currentPrice ){
        List<Float>  lastTenStock =  executedOrderDaoRepository.getTenLastStockPrice(symbolId);
        if(lastTenStock == null && lastTenStock.size() ==0){
            lastTenStock = new ArrayList<>();
        }
        lastTenStock.add(CommonUtility.doubleToFloat(currentPrice));
        if(lastTenStock.size() ==1){
            return CommonUtility.doubleToFloat(currentPrice);
        }
        double sum = 0.0, standardDeviation = 0.0;
        int length = lastTenStock.size();
        for(double num : lastTenStock) {
            sum += num;
        }
        double mean = sum/length;
        return CommonUtility.doubleToFloat(mean);

    }



    /**
     * calculate average of instrument base on transitions
     * @param symbolId
     * @param accountId
     * @return
     */
    private HashMap<String,Object> calculation(String symbolId , String accountId){
        List<ExecutedOrdersDao> executionOrderList  = executedOrderDaoRepository.getExecutedOrderByAccountIdAndSymbolId(accountId,symbolId);
        HashMap<String,Object> returnObject = new  HashMap<String,Object>();
        if(executionOrderList.size()> 0) {

            float currentSymbolPrice =executionOrderList.get(0).getSymbolObject().getStockPrice();

            // count the number of quantity
            int countPurchasedQyt = 0;
            float countPurchasedPrice = 0;
            float countStockPurchasedPrice = 0;
            int numberOfPurchasedTransaction =0;


            int numberOfStockSale = 0;
            float countSalePrice = 0;
            float countStockSalePrice = 0;
            int numberOfSaleTransaction =0;

           for(int index  =0  ; index < executionOrderList.size() ; index++){
                 // buy Case
                 if(executionOrderList.get(index).getQuantity() > 0){

                     countPurchasedQyt +=  Math.abs(executionOrderList.get(index).getQuantity());
                     countPurchasedPrice +=  executionOrderList.get(index).getTotalPrice();
                     countStockPurchasedPrice +=  executionOrderList.get(index).getPrice();
                     numberOfPurchasedTransaction +=1;

                 }else{
                     //sale case
                     numberOfStockSale +=  Math.abs(executionOrderList.get(index).getQuantity());
                     countSalePrice +=  executionOrderList.get(index).getTotalPrice();
                     countStockSalePrice +=  executionOrderList.get(index).getPrice();
                     numberOfSaleTransaction +=1;
                 }
             }
             //purchased
             returnObject.put("countPurchasedQyt",countPurchasedQyt);
            returnObject.put("countPurchasedPrice",countPurchasedPrice);
            returnObject.put("countStockPurchasedPrice",countStockPurchasedPrice);
            returnObject.put("numberOfPurchasedTransaction",numberOfPurchasedTransaction);

            //sale
            returnObject.put("numberOfStockSale",numberOfStockSale);
            returnObject.put("countSalePrice",countSalePrice);
            returnObject.put("countStockSalePrice",countStockSalePrice);
            returnObject.put("numberOfSaleTransaction",numberOfSaleTransaction);

            float currentBuyAmountHolding = countPurchasedPrice - countSalePrice;
            int currentStockHolding =   countPurchasedQyt  -  numberOfStockSale;
            if(currentStockHolding <= 0){
                returnObject.put("inProcessing",false);
            }else{
                returnObject.put("inProcessing",true);
            }
            float averagePrice = currentBuyAmountHolding/currentStockHolding;
            float profitAndLoss = (currentStockHolding)*((currentSymbolPrice) - (averagePrice));

            returnObject.put("currentBuyAmountHolding",currentBuyAmountHolding);
            returnObject.put("currentStockHolding",currentStockHolding);
            returnObject.put("averagePrice",averagePrice);
            returnObject.put("profitAndLoss",profitAndLoss);
            returnObject.put("currentSymbolPrice",currentSymbolPrice);
        }
        return returnObject;
    }


    /**
     * get List on all executedOrder base on  accountId and symbolId
     * @param accountId
     * @param symbolId
     * @return
     */
    public ApiResponseWrapper executedOrder(String accountId, String symbolId){
        ApiResponseWrapper apiResponseWrapper = new  ApiResponseWrapper();
        HashMap<String,Object> result  = new HashMap<String,Object>();
        result.put("orders" ,   new  ArrayList<HashMap<String,Object>>());
        result.put("stock_balance" , new  ArrayList<HashMap<String,Object>>());
        List<ExecutedOrdersDao> executionOrderList;
        if(symbolId == null) {
           executionOrderList = executedOrderDaoRepository.getExecutedOrderByAccountId(accountId);
        }else{
            executionOrderList = executedOrderDaoRepository.getExecutedOrderByAccountIdAndSymbolId(accountId,symbolId);
        }
        if(executionOrderList.size() > 0 ){

            ArrayList<HashMap<String,Object>> stockBalance  = new ArrayList<HashMap<String,Object>>();
            result.put("orders", executionOrderList);
            List<StockDao> liveStock = new ArrayList<>();
            if(symbolId == null) {
                liveStock  = stockDaoRepository.findAllData();
            }else{
                Optional<StockDao>  liveStockTmp  = stockDaoRepository.findBySymbolCode(symbolId);
                if(liveStockTmp.isPresent()){
                    liveStock.add(liveStockTmp.get());
                }
            }
            for (StockDao stock : liveStock) {
                HashMap<String,Object> stockInfo  = new HashMap<String,Object>();
                HashMap<String,Object> stockDetails =  this.calculation(stock.getSymbolCode(), accountId);
                if(stockDetails.size() > 0 && stockDetails.containsKey("averagePrice")
                        && stockDetails.containsKey("profitAndLoss") && stockDetails.containsKey("currentStockHolding")
                        && stockDetails.containsKey("currentBuyAmountHolding")  && stockDetails.containsKey("inProcessing") && (boolean)stockDetails.get("inProcessing")) {

                    stockInfo.put("avg_price", stockDetails.get("averagePrice"));
                    stockInfo.put("profit_loss", stockDetails.get("profitAndLoss"));
                    stockInfo.put("account_id", accountId);
                    stockInfo.put("symbol_id", stock.getSymbolCode());
                    stockInfo.put("symbol_name", stock.getSymbolName());
                    stockInfo.put("quantity", stockDetails.get("currentStockHolding"));
                    stockInfo.put("total_price", stockDetails.get("currentBuyAmountHolding"));
                    stockInfo.put("current_price", stock.getStockPrice());
                    stockBalance.add(stockInfo);

                }
            }
            result.put("stock_balance", stockBalance);
            apiResponseWrapper.setResponse(result);
        }else{
            apiResponseWrapper.setResponse(result);
        }
        return apiResponseWrapper;
    }

    /**
     * get List on all executedOrder base on  accountId
     * @param accountId
     * @return
     */
    public ApiResponseWrapper executedOrder(String accountId){
        return executedOrder(accountId, null);
    }

}
