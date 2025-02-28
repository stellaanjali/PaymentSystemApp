package org.paymentSystemApp.Repository;

import org.paymentSystemApp.Model.User;
import org.paymentSystemApp.Model.Vpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByEmail(String email);
    //findByEmail, findByName, findByEmailContaining, findByNameContaining, countByStatus,etc. are some methods already provided by spring data jpa




}
