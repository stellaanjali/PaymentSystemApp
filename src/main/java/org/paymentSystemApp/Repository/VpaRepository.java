package org.paymentSystemApp.Repository;

import org.paymentSystemApp.Model.User;
import org.paymentSystemApp.Model.Vpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
    public interface VpaRepository extends JpaRepository<Vpa, Integer> {

        //List<Vpa> findByVpa_id(String vpa_id);

        @Query("SELECT v FROM Vpa v WHERE v.user_id = :user_id")
        List<Vpa> findByUser_id(@Param("user_id") Integer user_id);
        //    @Query("SELECT v FROM Vpa v WHERE v.userId = :userId")
        //    List<Vpa> findByUserId(@Param("userId") Integer userId);
        @Query("SELECT v FROM Vpa v WHERE v.vpa_id = :vpa_id")
        List<Vpa> findByVpa_id(@Param("vpa_id") String vpa_id);

    }
