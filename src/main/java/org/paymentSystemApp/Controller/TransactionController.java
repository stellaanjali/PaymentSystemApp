package org.paymentSystemApp.Controller;

import org.paymentSystemApp.Model.TransactionRequestDTO;
import org.paymentSystemApp.Model.TransactionResponseDTO;
import org.paymentSystemApp.Model.VpaBalanceRequestDTO;
import org.paymentSystemApp.Model.VpaBalanceResponseDTO;
import org.paymentSystemApp.Service.TransactionService;
import org.paymentSystemApp.Service.UserAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping("/initiateTransaction")
    public ResponseEntity<TransactionResponseDTO> initiateTransaction (@RequestBody TransactionRequestDTO transactionRequestDTO){
        return transactionService.initiateTransaction(transactionRequestDTO);
    }



}
