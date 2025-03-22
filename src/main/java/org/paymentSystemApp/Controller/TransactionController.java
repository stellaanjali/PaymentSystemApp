package org.paymentSystemApp.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.paymentSystemApp.Exceptions.InputValidationException;
import org.paymentSystemApp.Model.TransactionRequestDTO;
import org.paymentSystemApp.Model.TransactionResponseDTO;
import org.paymentSystemApp.Service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping("/initiateTransaction")
    public ResponseEntity<TransactionResponseDTO> initiateTransaction (@RequestBody TransactionRequestDTO transactionRequestDTO) throws JsonProcessingException, InputValidationException {
        return transactionService.initiateTransaction(transactionRequestDTO);
    }

    @GetMapping("/getTransactionHistoryTable")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionHistoryTable (){
        return transactionService.getTransactionHistoryTable();
    }

    @GetMapping("/getTransactionStatus")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionStatus(@RequestParam("transaction_id") String transactionId){
        return transactionService.getTransactionStatus(transactionId);
    }

}
