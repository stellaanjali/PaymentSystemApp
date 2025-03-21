package org.paymentSystemApp.Repository;

import org.paymentSystemApp.Model.TransactionHistory;
import org.paymentSystemApp.Model.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Integer> {

}
