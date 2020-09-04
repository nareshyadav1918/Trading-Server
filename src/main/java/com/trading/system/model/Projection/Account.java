package com.trading.system.model.Projection;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;




@JsonInclude(JsonInclude.Include.NON_NULL)
public interface Account {

    @JsonProperty("accountId")
    @Value("#{target.accountId}")
    public  String getAccountId();

    @JsonProperty("firstName")
    @Value("#{target.firstName}")
    public String getFirstName();

    @JsonProperty("lastName")
    @Value("#{target.lastName}")
    public String getLastName();


    @JsonProperty("email")
    @Value("#{target.email}")
    public String getEmail();

    @JsonProperty("phone")
    @Value("#{target.phone}")
    public String getPhone();

    @JsonProperty("userName")
    @Value("#{target.userName}")
    public String getUserName();

    @JsonProperty("password")
    @Value("#{target.password}")
    public String getPassword();

    @JsonProperty("mainBalance")
    @Value("#{target.mainBalance}")
    public float getMainBalance();

    @JsonProperty("unreleasedBalance")
    @Value("#{target.unreleasedBalance}")
    public float getUnreleasedBalance();

    @JsonProperty("unreleasedProfitLoss")
    @Value("#{target.unreleasedProfitLoss}")
    public float getUnreleasedProfitLoss();
}
