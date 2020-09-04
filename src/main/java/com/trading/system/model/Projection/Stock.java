package com.trading.system.model.Projection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;


@JsonInclude(JsonInclude.Include.NON_NULL)
public interface Stock {

    @JsonProperty("symbol_id")
    @Value("#{target.symbolCode}")
    public  String getSymbolCode();

    @JsonProperty("symbol_name")
    @Value("#{target.symbolName}")
    public  String getSymbolName();

    @JsonProperty("current_price")
    @Value("#{target.stockPrice}")
    public float getStockPrice();

    @JsonProperty("last_price")
    @Value("#{target.lastPrice}")
    public  float getLastPrice();




}
