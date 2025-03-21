package org.paymentSystemApp.Repository;


import org.paymentSystemApp.Model.TransactionHistory;
import org.paymentSystemApp.Model.TransactionStatus;
import org.paymentSystemApp.Model.User;
import org.paymentSystemApp.Model.Vpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionStatus, Integer>{

}


