package com.trading.system.configuration;

import com.trading.system.exception.ExceptionDetails;
import com.trading.system.model.persistent.AccountDao;
import com.trading.system.model.persistent.StockDao;
import com.trading.system.repository.AccountDaoRepository;
import com.trading.system.repository.ExecutedOrderDaoRepository;
import com.trading.system.repository.StockDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DecimalFormat;

@Configuration
public class AppConfiguration {


    @Autowired
    private AccountDaoRepository accountDaoRepository;

    @Autowired
    private ExecutedOrderDaoRepository executedOrderDaoRepository;

    @Autowired
    private StockDaoRepository stockDaoRepository;


    @Bean
    public void initiatedDataBase(){
        this.saveStock("005158","55.52","55.52","Boeing");
        this.saveStock("005159","55.52","55.52","Apple");
        this.saveStock("005153","55.52","55.52","Microsoft");
        this.saveStock("005154","55.52","55.52","Facebook");

        this.saveAccount("UMD0034","Matt","Blanc","0123456789");
        this.saveAccount("UMD0035","Nicholas","Tesla","0123456789");

    }

    private void saveAccount(String accountId , String firstName , String lastName ,String phoneNumber ){

        AccountDao accountDao = new AccountDao();
        accountDao.setAccountId(accountId);
        accountDao.setFirstName(firstName);
        accountDao.setLastName(lastName);
        accountDao.setEmail(firstName+"_"+lastName+"@gmail.com");
        accountDao.setPhone(phoneNumber);
        accountDao.setUserName(firstName);
        accountDao.setPassword("12345");
        accountDao.setMainBalance(Float.parseFloat("1000.00"));
        accountDao.setAmountInvested(Float.parseFloat("0.00"));
        accountDaoRepository.save(accountDao);
    }

    private void saveStock(String code , String price , String lastPrice ,String name ){

        StockDao storeDao =   new StockDao();
        storeDao.setSymbolCode(code);
        storeDao.setStockPrice(Float.parseFloat(price));
        storeDao.setLastPrice(Float.parseFloat(lastPrice));
        storeDao.setSymbolName(name);
        stockDaoRepository.save(storeDao);
    }




}
