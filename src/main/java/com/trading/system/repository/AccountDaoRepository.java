package com.trading.system.repository;

import com.trading.system.model.Projection.Account;
import com.trading.system.model.persistent.AccountDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AccountDaoRepository extends JpaRepository<AccountDao, Integer>, JpaSpecificationExecutor<AccountDao> {


    /**
     * Get Account projection This method is user for login
     * @param userName : string
     * @param password : string
     * @return Optional<Account>
     */
    Optional<AccountDao> findByUserNameAndPassword(String userName , String password);

    /**
     * TTo get Account basic information
     * @param accountId : String
     * @return Optional<Account>
     */
    Optional<AccountDao> findByAccountId(String accountId );



}
