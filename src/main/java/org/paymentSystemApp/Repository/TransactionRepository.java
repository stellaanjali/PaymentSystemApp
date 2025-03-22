package org.paymentSystemApp.Repository;


import org.paymentSystemApp.Model.TransactionHistory;
import org.paymentSystemApp.Model.TransactionStatus;
import org.paymentSystemApp.Model.User;
import org.paymentSystemApp.Model.Vpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionStatus, Integer>{

    @Query(value = "SELECT * FROM transaction_status WHERE transaction_id = :transaction_id", nativeQuery = true)
    List<TransactionStatus> findByTxnId(@Param("transaction_id") String transaction_id);

}


