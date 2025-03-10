package org.paymentSystemApp.Service;

import org.paymentSystemApp.Model.*;
import org.paymentSystemApp.Repository.TransactionRepository;
import org.paymentSystemApp.Repository.VpaBalanceRepository;
import org.paymentSystemApp.Repository.VpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final VpaRepository vpaRepository;

    public TransactionService(TransactionRepository transactionRepository, VpaRepository vpaRepository) {
        this.transactionRepository = transactionRepository;
        this.vpaRepository = vpaRepository;
    }

    public ResponseEntity<TransactionResponseDTO> initiateTransaction(TransactionRequestDTO transactionRequestDTO) {
        Boolean isValidDebitorVpa = validateDebitorVpa(transactionRequestDTO.getDebitor_vpa());
        Boolean isValidCreditorVpa = validateCreditorVpa(transactionRequestDTO.getCreditor_vpa());

        TransactionResponseDTO response = new TransactionResponseDTO();
            if(isValidDebitorVpa){
                response.setMessage("Debitor VPA does not exist!");
                LocalDateTime currentTimestamp = LocalDateTime.now();
                response.setTimestamp(currentTimestamp);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            else if(isValidCreditorVpa){
                response.setMessage("Creditor VPA does not exist!");
                LocalDateTime currentTimestamp = LocalDateTime.now();
                response.setTimestamp(currentTimestamp);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            else{
                Boolean isValidDebitorPin = validateDebitorPin(transactionRequestDTO.getDebitor_vpa(), transactionRequestDTO.getDebitor_pin());
                if(!isValidDebitorPin){
                    response.setMessage("Please enter the correct pin.");
                    LocalDateTime currentTimestamp = LocalDateTime.now();
                    response.setTimestamp(currentTimestamp);
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
                }
                else{
                    // TODO: Create an API to generate Transaction Id
                    


                }


            }


    }
    private Boolean validateDebitorVpa(String debitor_vpa){
        List<Vpa> checkDebitorVpaInDb = vpaRepository.findByVpa_id(debitor_vpa);
        if(checkDebitorVpaInDb.isEmpty()) {
            return Boolean.FALSE;
        }
        else{
            return Boolean.TRUE;
        }
    }
    private Boolean validateCreditorVpa(String creditor_vpa){
        List<Vpa> checkCreditorVpaInDb = vpaRepository.findByVpa_id(creditor_vpa);
        if(checkCreditorVpaInDb.isEmpty()) {
            return Boolean.FALSE;
        }
        else{
            return Boolean.TRUE;
        }
    }
    private Boolean validateDebitorPin(String debitor_vpa , Integer debitor_pin){
        List<Vpa> vpaFromDbList = vpaRepository.findByVpa_id(debitor_vpa);
        Vpa vpaFromDb = vpaFromDbList.get(0);
        Integer pinFromDb = vpaFromDb.getPin();
        if(debitor_pin.equals(vpaFromDb)){
            return Boolean.TRUE;
        }
        else{
            return Boolean.FALSE;
        }

    }


}
