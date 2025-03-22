package org.paymentSystemApp.Repository;

import org.paymentSystemApp.Model.User;
import org.paymentSystemApp.Model.Vpa;
import org.paymentSystemApp.Model.VpaBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface VpaBalanceRepository extends JpaRepository<VpaBalance, Integer> {

    //List<Vpa> findByVpa_id(String vpa_id);

        @Query("SELECT v FROM VpaBalance v WHERE v.vpa_id = :vpa_id") // here v means row..
        List<VpaBalance> findByVpa_id(@Param("vpa_id") String vpa_id);
    //    @Query("SELECT v FROM Vpa v WHERE v.userId = :userId")
    //    List<Vpa> findByUserId(@Param("userId") Integer userId);
//    @Query("SELECT v FROM VpaBalance v WHERE v.vpa_balance = :vpa_balance")
//    List<VpaBalance> findByAmount(@Param("vpa_balance") BigDecimal vpa_balance);

}

