package com.trading.system.repository;

import com.trading.system.model.Projection.ExecutionOrder;
import com.trading.system.model.persistent.ExecutedOrdersDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExecutedOrderDaoRepository extends JpaRepository<ExecutedOrdersDao, Integer>, JpaSpecificationExecutor<ExecutedOrdersDao> {


    /**
     * gGet list Of ExecutionOrder  base on account Id
     * @param accountId
     * @return
     */
    @Query(value="SELECT eo.* from executed_orders as eo INNER JOIN stock as st1 On( st1.symbol_code = eo.symbol_id ) INNER JOIN account as ac On( ac.account_id = eo.account_id ) where eo.account_id = ?1 ORDER BY eo.current_dt  DESC", nativeQuery=true)
    List<ExecutedOrdersDao> getExecutedOrderByAccountId(String accountId);

    /**
     * gGet list Of ExecutionOrder  base on account Id
     * @param accountId
     * @return
     */
    @Query(value="SELECT eo.* from executed_orders as eo INNER JOIN stock as st1 On( st1.symbol_code = eo.symbol_id ) INNER JOIN account as ac On( ac.account_id = eo.account_id ) where eo.account_id = ?1 and eo.symbol_id=?2 ORDER BY eo.current_dt  DESC", nativeQuery=true)
    List<ExecutedOrdersDao> getExecutedOrderByAccountIdAndSymbolId(String accountId , String symbolId);

    @Query(value="SELECT eo.price from executed_orders eo where eo.symbol_id=? ORDER BY eo.current_dt  DESC limit 10", nativeQuery=true)
    List<Float> getTenLastStockPrice(String symbolId);

}
