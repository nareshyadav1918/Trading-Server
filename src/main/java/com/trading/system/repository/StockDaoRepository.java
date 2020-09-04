package com.trading.system.repository;

import com.trading.system.model.Projection.Stock;
import com.trading.system.model.persistent.StockDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StockDaoRepository extends JpaRepository<StockDao, Integer>, JpaSpecificationExecutor<StockDao> {

    /**
     * Get List of all Stock available in database
     * @return
     */
    @Query(value="SELECT s1.* from stock s1", nativeQuery=true)
    List<StockDao> findAllData();


    Optional<StockDao> findBySymbolCode(String symbolCode);
}
