package org.paymentSystemApp.Controller;

import org.paymentSystemApp.Exceptions.InputValidationException;
import org.paymentSystemApp.Model.*;
import org.paymentSystemApp.Service.UserAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class UserAccountController {
    private final UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService){
        this.userAccountService = userAccountService;
    }

    @PostMapping("/createVpa")
    public ResponseEntity<VpaResponseDTO> createVpa(@RequestBody VpaRequestDTO vpaRequestDTO) throws InputValidationException {
        return userAccountService.createVpa(vpaRequestDTO);
    }

    @PostMapping("/addBalance")
    public ResponseEntity<VpaBalanceResponseDTO> addBalance (@RequestBody VpaBalanceRequestDTO vpaBalanceRequestDTO){
        return userAccountService.addBalance(vpaBalanceRequestDTO);
    }

    @GetMapping("/fetchBalance")
    public ResponseEntity<VpaBalanceResponseDTO> fetchBalance (@RequestParam("vpa_id") String vpa_id){
        return userAccountService.fetchBalance(vpa_id);
    }

    @GetMapping("/fetchVpaBalanceForAll")
    public ResponseEntity<List<VpaBalanceResponseDTO>> fetchedVpaBalanceForAll (){
        return userAccountService.fetchedVpaBalanceForAll();
    }

    @PostMapping("/addDataToVpaBalance")
    public ResponseEntity<VpaBalanceResponseDTO> addDataToVpaBalance (@RequestBody VpaBalanceRequestDTO vpaBalanceRequestDTO){
        return userAccountService.addDataToVpaBalance(vpaBalanceRequestDTO);
    }




}
