package org.paymentSystemApp.Repository;

import org.paymentSystemApp.Model.User;
import org.paymentSystemApp.Model.Vpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
    public interface VpaRepository extends JpaRepository<Vpa, Integer> {

        List<Vpa> findByVpaId(String vpaId);

        List<Vpa> findByUserId(Integer userId);

    }
