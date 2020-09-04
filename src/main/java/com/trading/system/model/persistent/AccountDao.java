package com.trading.system.model.persistent;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trading.system.util.CommonUtility;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Data
@Entity
@Table(name = "account")
public class AccountDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @JsonProperty("account_id")
    @Column(name = "account_id",  nullable = false)
    private String accountId;

    @JsonProperty("first_name")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @JsonProperty("last_name")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @JsonProperty("email")
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @JsonProperty("user_name")
    @Column(name = "user_name", nullable = false)
    private String userName;

    @JsonProperty("password")
    @Column(name = "password", nullable = false)
    private String password;

    @JsonProperty("main_balance")
    @Column(name = "main_balance", nullable = false)
    private float mainBalance;

    @JsonProperty("amount_invested")
    @Column(name = "amount_invested", nullable = false)
    private float amountInvested;

    @Transient
    @JsonProperty("profit")
    private float profit;

    public float getProfit(){
        float totalInvestment  = this.mainBalance + this.amountInvested;
        return totalInvestment - 1000;
    }






    @JsonBackReference
    @OneToMany(cascade = {CascadeType.ALL,CascadeType.PERSIST,CascadeType.MERGE }, fetch =FetchType.LAZY ,mappedBy = "accountObject")
    public List<ExecutedOrdersDao> executedOrdersDaoAccount;

}
