package com.trading.system.model.persistent;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Data
@Entity
@Table(name = "stock")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @JsonProperty("symbol_id")
    @Column(name = "symbol_code",  nullable = false)
    private String symbolCode;

    @Column(name = "stock_price", nullable = false)
    @JsonProperty("current_price")
    private float stockPrice;

    @Column(name = "last_price", nullable = false)
    @JsonProperty("last_price")
    private float lastPrice;

    @Column(name = "symbol_name", nullable = false)
    @JsonProperty("symbol_name")
    private String symbolName;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.ALL,CascadeType.PERSIST,CascadeType.MERGE }, fetch =FetchType.LAZY ,mappedBy = "symbolObject")
    public List<ExecutedOrdersDao> executedOrdersDao;
}
