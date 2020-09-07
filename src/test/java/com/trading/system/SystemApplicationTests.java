package com.trading.system;

import com.trading.system.integration.TradingService;
import com.trading.system.model.data.ApiResponseWrapper;
import com.trading.system.model.persistent.AccountDao;
import com.trading.system.model.persistent.StockDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.*;


import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class SystemApplicationTests {

    @Autowired
	private TradingService tradingService;
    private final String userName  = "Nicholas";
    private final String password = "12345";
    private final  String accountId  = "UMD0035";
	private final  String symbolId  = "005158";
	private final Integer qty = 5 ;



	@Test
	public void testUserLogin() throws Exception {
		ApiResponseWrapper apiResponseWrapper = tradingService.login(userName, password);
		assertTrue(apiResponseWrapper.getResponse() != null);
		Map<String , Object> returnResponse  = new HashMap<>();
		try{
			returnResponse= (Map<String , Object>) apiResponseWrapper.getResponse();
		}catch(Exception e){
			returnResponse = null;
		}
		assertTrue(returnResponse != null);
		assertTrue((boolean)returnResponse.get("login_success") == true);
		assertTrue((String)returnResponse.get("token") != null);
		assertTrue((String)returnResponse.get("account_id") != null);
		assertTrue((String)returnResponse.get("first_name") != null);
		assertTrue((String)returnResponse.get("last_name") != null);

	}

	@Test
	public void testUserAccountDetails() throws Exception {
		ApiResponseWrapper apiResponseWrapper = tradingService.getAccountDetails(this.accountId);
		assertTrue(apiResponseWrapper.getResponse() != null);
		AccountDao returnResponse  = new AccountDao();
		try{
			returnResponse= (AccountDao) apiResponseWrapper.getResponse();
		}catch(Exception e){
			returnResponse = null;
		}
		assertTrue(returnResponse != null);

	}

	@Test
	public void testLivePrice(){
		ApiResponseWrapper apiResponseWrapper = tradingService.livePrice();
		assertTrue(apiResponseWrapper.getResponse() != null);
		List<StockDao> returnResponse  = new ArrayList<>();
		try{
			returnResponse= (List<StockDao>) apiResponseWrapper.getResponse();
		}catch(Exception e){
			returnResponse = null;
		}
		assertTrue(returnResponse != null);
		assertTrue(returnResponse.size()  > 0 );
		for(StockDao stockDaoObj : returnResponse) {
			assertTrue(stockDaoObj.getStockPrice() > 0.0);
			assertTrue(stockDaoObj.getSymbolCode() != "");
			assertTrue(stockDaoObj.getSymbolName() != "");
		}
	}


	@Test
	public void testUpdateOrder(){

		Optional<String> symbolId  = Optional.of(this.symbolId.toString());
		Optional<String> accountId = Optional.of(this.accountId.toString());
		Optional<Integer> quantity = Optional.of(this.qty);
		Optional<Double> price =  Optional.of(0.0);

		//get currentPrice
		ApiResponseWrapper apiResponseWrapper = tradingService.livePrice();
		assertTrue(apiResponseWrapper.getResponse() != null);
		List<StockDao> returnResponse  = new ArrayList<>();
		try{
			returnResponse= (List<StockDao>) apiResponseWrapper.getResponse();
		}catch(Exception e){
			returnResponse = null;
		}
		for(StockDao stockDaoObj : returnResponse) {
			if(stockDaoObj.getSymbolCode().equalsIgnoreCase(symbolId.get())){
				price = Optional.of(new Double(stockDaoObj.getStockPrice()));
				break;
			}
		}

		ApiResponseWrapper apiResponseWrapperForUpdateOrder = tradingService.updateOrder(
				symbolId, accountId,quantity,price);
		assertTrue(apiResponseWrapperForUpdateOrder.getResponse() != null);
		Map<String , Object> updateOrderResponse  = new HashMap<>();
		updateOrderResponse =(Map<String , Object>) apiResponseWrapperForUpdateOrder.getResponse();
		assertTrue((boolean)updateOrderResponse.get("transaction_success") == true);

	}

	@Test
	public void testExecutedOrder(){

		ApiResponseWrapper apiResponseWrapper = tradingService.executedOrder(this.symbolId , this.accountId);
		assertTrue(apiResponseWrapper.getResponse() != null);
		List<StockDao> returnResponse  = new ArrayList<>();
		try{
			returnResponse= (List<StockDao>) apiResponseWrapper.getResponse();
		}catch(Exception e){
			returnResponse = null;
		}
		assertTrue(apiResponseWrapper.getResponse() != null);
		Map<String , Object> updateOrderResponse  = new HashMap<>();
		updateOrderResponse =(Map<String , Object>) apiResponseWrapper.getResponse();

		assertTrue(updateOrderResponse.containsKey("stock_balance")  == true);
		assertTrue(updateOrderResponse.containsKey("orders")  == true);
	}



}
