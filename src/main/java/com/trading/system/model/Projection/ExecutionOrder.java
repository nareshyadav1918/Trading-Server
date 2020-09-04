package com.trading.system.model.Projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ExecutionOrder {

    @JsonProperty("executed_orders_id")
    @Value("#{target.resultId}")
    public  String getResultId();

    @JsonProperty("accountId")
    @Value("#{target.accountId}")
    public  String getAccountId();

    @JsonProperty("symbol_name")
    @Value("#{target.symbolObject.symbolName}")
    public  String getSymbolName();

    @JsonProperty("symbolId")
    @Value("#{target.symbolId}")
    public  String getSymbolId();


    @JsonProperty("quantity")
    @Value("#{target.quantity}")
    public  int getQuantity();

    @JsonProperty("price")
    @Value("#{target.price}")
    public  float getPrice();

    @JsonProperty("total_price")
    @Value("#{target.totalPrice}")
    public  float getTotalPrice();

    @JsonProperty("date")
    @Value("#{target.date}")
    @JsonFormat(pattern = "yyyy.MM.dd hh:mm")
    public Date getDate();
}
