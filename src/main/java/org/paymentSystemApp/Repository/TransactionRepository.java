package org.paymentSystemApp.Repository;


import org.paymentSystemApp.Model.TransactionHistory;
import org.paymentSystemApp.Model.TransactionStatus;
import org.paymentSystemApp.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionStatus, Integer> {

//    @Query("SELECT e.fieldName, COUNT(e) FROM MyEntity e GROUP BY e.fieldName ORDER BY COUNT(e) DESC")
//    List<Object[]> countFieldFrequency();
//
//    // Calculate average value of a numeric field
//    @Query("SELECT AVG(e.numericField) FROM MyEntity e")
//    Double calculateAverageValue();

}
