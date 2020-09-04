package com.trading.system.model.persistent;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "executed_orders")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExecutedOrdersDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("executed_orders_id")
    @Column(name = "executed_orders_id", insertable = false, updatable = false , nullable = false)
    private Integer resultId;

    @Column(name = "account_id",  insertable = false, updatable = false , nullable = false)
    @JsonProperty("account_id")
    private String accountId;

    @Column(name = "symbol_id",  insertable = false, updatable = false ,  nullable = false)
    @JsonProperty("symbolId")
    private String symbolId;

    @Column(name = "quantity", nullable = false)
    @JsonProperty("quantity")
    private Integer quantity;

    @Column(name = "price", nullable = false)
    @JsonProperty("price")
    private float price;

    @Column(name = "total_price", nullable = false)
    @JsonProperty("total_price")
    private float totalPrice;


    @Transient
    @JsonProperty("symbol_name")
    private String symbolName;


    public String getSymbolName(){
        return this.symbolObject.getSymbolName();
    }

   // @Transient
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy.MM.dd hh:mm")
    @JsonFormat(pattern = "yyyy.MM.dd hh:mm")
    @Column(name = "current_dt", nullable = false)
    private Date date;


    @JsonIgnore
    @ManyToOne( fetch =FetchType.LAZY )
    @JoinColumn(name = "account_id")
    public AccountDao accountObject;

    @JsonIgnore
    @ManyToOne( fetch =FetchType.LAZY )
    @JoinColumn(name = "symbol_id")
    public StockDao symbolObject;
}
