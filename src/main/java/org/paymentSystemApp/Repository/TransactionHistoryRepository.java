package org.paymentSystemApp.Repository;

import org.paymentSystemApp.Model.TransactionHistory;
import org.paymentSystemApp.Model.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Integer> {


    @Query("SELECT COUNT(t.debitor_vpa) FROM TransactionHistory t WHERE t.debitor_vpa = :debitor_vpa") //JPQL query
    Integer getCountOfDebitorVpa(@Param("debitor_vpa") String debitor_vpa);

    @Query("SELECT COUNT(t.creditor_vpa) FROM TransactionHistory t WHERE t.creditor_vpa = :creditor_vpa") //JPQL query
    Integer getCountOfCreditorVpa(@Param("creditor_vpa") String creditor_vpa);

    @Query(value = "SELECT AVG(amount) FROM transaction_history WHERE debitor_vpa = :debitor_vpa", nativeQuery = true)
    BigDecimal getAvgDebitorTxnAmount(@Param("debitor_vpa") String debitor_vpa);

//    @Query(value = "SELECT AVG(amount) FROM transaction_history WHERE creditor_vpa = :creditor_vpa", nativeQuery = true)
//    BigDecimal getAvgCreditorTxnAmount(@Param("creditor_vpa") String creditor_vpa);

}
