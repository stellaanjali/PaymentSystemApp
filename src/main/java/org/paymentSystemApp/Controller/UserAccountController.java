package org.paymentSystemApp.Controller;

import org.paymentSystemApp.Model.Vpa;
import org.paymentSystemApp.Model.VpaDTO;
import org.paymentSystemApp.Service.UserAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class UserAccountController {
    private final UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService){
        this.userAccountService = userAccountService;
    }

    @PostMapping("/createVpa")
    public ResponseEntity<VpaDTO> createVpa(@RequestBody Vpa vpa){
        return userAccountService.createVpa(vpa);
    }


}
