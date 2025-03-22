package org.paymentSystemApp.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.paymentSystemApp.Exceptions.InputValidationException;
import org.paymentSystemApp.Model.*;
import org.paymentSystemApp.Repository.TransactionHistoryRepository;
import org.paymentSystemApp.Repository.TransactionRepository;
import org.paymentSystemApp.Repository.VpaBalanceRepository;
import org.paymentSystemApp.Repository.VpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.paymentSystemApp.Config.ShortenSOPln.print;

@Service

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final VpaRepository vpaRepository;
    private final TransactionIdGenerationService transactionIdGenerationService;
    private final AmountValidationService amountValidationService;
    private final VpaBalanceRepository vpaBalanceRepository;
    private final RiskFraudCallerService riskFraudCallerService;
    private final SettlementService settlementService;
    private final NotificationAndPostingService notificationAndPostingService;
    private final TransactionHistoryRepository transactionHistoryRepository;

    public TransactionService(TransactionRepository transactionRepository, VpaRepository vpaRepository, TransactionIdGenerationService transactionIdGenerationService, AmountValidationService amountValidationService,
                              VpaBalanceRepository vpaBalanceRepository, RiskFraudCallerService riskFraudCallerService,
                              SettlementService settlementService, NotificationAndPostingService notificationAndPostingService,
                              TransactionHistoryRepository transactionHistoryRepository) { //Constructor Injection
        this.transactionRepository = transactionRepository;
        this.vpaRepository = vpaRepository;
        this.transactionIdGenerationService = transactionIdGenerationService;
        this.amountValidationService = amountValidationService;
        this.vpaBalanceRepository = vpaBalanceRepository;
        this.riskFraudCallerService = riskFraudCallerService;
        this.settlementService = settlementService;
        this.notificationAndPostingService = notificationAndPostingService;
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    public ResponseEntity<TransactionResponseDTO> initiateTransaction(TransactionRequestDTO transactionRequestDTO) throws JsonProcessingException, InputValidationException {
        LocalDateTime transactionInitiatedTime = LocalDateTime.now();
        Boolean isValidDebitorVpa = validateDebitorVpa(transactionRequestDTO.getDebitor_vpa());
        Boolean isValidCreditorVpa = validateCreditorVpa(transactionRequestDTO.getCreditor_vpa());


        TransactionResponseDTO response = new TransactionResponseDTO();
        if (!isValidDebitorVpa) {
            response.setMessage("Debitor VPA does not exist!");
            LocalDateTime currentTimestamp = LocalDateTime.now();
            response.setTimestamp(currentTimestamp);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else if (!isValidCreditorVpa) {
            response.setMessage("Creditor VPA does not exist!");
            LocalDateTime currentTimestamp = LocalDateTime.now();
            response.setTimestamp(currentTimestamp);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            Boolean isValidDebitorPin = validateDebitorPin(transactionRequestDTO.getDebitor_vpa(), transactionRequestDTO.getDebitor_pin());
            if (!isValidDebitorPin) {
                response.setMessage("Please enter the correct pin.");
                LocalDateTime currentTimestamp = LocalDateTime.now();
                response.setTimestamp(currentTimestamp);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            } else {
                // TODO: Create an API to generate Transaction Id
                //Traditional Method...
//                    TransactionIdGenerationService generateTxnId = new TransactionIdGenerationService();
//                    String generatedId = generateTxnId.generateTransactionId(transactionRequestDTO.getDebitor_vpa());
                // using DI
                String txnId = transactionIdGenerationService.generateTransactionId(transactionRequestDTO.getDebitor_vpa());
                print("Generated txn id = " + txnId);

//                    TransactionStatus txnStatus = new TransactionStatus(txnId); //ek aisa constructor jo txnId accept krta wo exist krta h transaction status mei, so we can use that to create objects of that class
//                    TransactionStatus txnStatus = new TransactionStatus(); //ye v use kr sakte for obj creation as it exists in the txn status class
//                    TransactionStatus txnStatus = new TransactionStatus();
//                    txnStatus.setTransaction_id(txnId);
//                    txnStatus.setDebitor_vpa(transactionRequestDTO.getDebitor_vpa());
//                    txnStatus.setCreditor_vpa(transactionRequestDTO.getCreditor_vpa());
//                    txnStatus.setAmount(transactionRequestDTO.getAmount());
//                    txnStatus.setCurrency(transactionRequestDTO.getCurrency());
//                    txnStatus.setTransaction_initiated_timestamp(LocalDateTime.now());
//                    txnStatus.setNote_from_debitor(transactionRequestDTO.getNote());
//                    txnStatus.setTxn_status("Transaction Id Generated!");
//                    txnStatus.setCurrent_status_timestamp(LocalDateTime.now());
//                    This entire thing was for an empty or no args constructor, we will now use an all args constructor

                String status = "Transaction Id Generated";
                TransactionStatus txnStatus = new TransactionStatus(txnId, transactionRequestDTO.getDebitor_vpa(),
                        transactionRequestDTO.getCreditor_vpa(), transactionRequestDTO.getAmount(), transactionRequestDTO.getCurrency(),
                        status, transactionRequestDTO.getNote(), transactionInitiatedTime, LocalDateTime.now());
                TransactionStatus savedTxnStatus = transactionRepository.save(txnStatus); // transaction id generate ho gya so save kr rhe
                if (!amountValidationService.validateDebitorBalance(transactionRequestDTO.getDebitor_vpa(), transactionRequestDTO.getAmount())) {
                    status = "Amount Validation Failed";
                    TransactionStatus txnAmountValidation = new TransactionStatus(txnId, transactionRequestDTO.getDebitor_vpa(),
                            transactionRequestDTO.getCreditor_vpa(), transactionRequestDTO.getAmount(), transactionRequestDTO.getCurrency(),
                            status, transactionRequestDTO.getNote(), transactionInitiatedTime, LocalDateTime.now());
                    TransactionStatus transactionValidationFailed = transactionRepository.save(txnAmountValidation);//line 84 mei jo txn id save kiya wahi idhar save kr rhe
                    response.setMessage("Amount Validation Failed");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
                }


                status = "Amount Validation Passed";
                TransactionStatus txnAmountValidation = new TransactionStatus(txnId, transactionRequestDTO.getDebitor_vpa(),
                        transactionRequestDTO.getCreditor_vpa(), transactionRequestDTO.getAmount(), transactionRequestDTO.getCurrency(),
                        status, transactionRequestDTO.getNote(), transactionInitiatedTime, LocalDateTime.now());
                TransactionStatus transactionValidationPassed = transactionRepository.save(txnAmountValidation);


                // response.setMessage("Done till Amount Validation, Fraud and Risk Check Next");
                // return ResponseEntity.status(HttpStatus.OK).body(response);
                FraudCheckRequestDTO fraudCheckRequestDTO = new FraudCheckRequestDTO();
                BigDecimal debitorBalance = fetchBalanceFromVpaId(transactionRequestDTO.getDebitor_vpa());
                BigDecimal creditorBalance = fetchBalanceFromVpaId(transactionRequestDTO.getCreditor_vpa());
                fraudCheckRequestDTO.setDebitor_balance(debitorBalance);
                fraudCheckRequestDTO.setCreditor_balance(creditorBalance);

                fraudCheckRequestDTO.setAmount(transactionRequestDTO.getAmount());
                fraudCheckRequestDTO.setDebitor_txn_history(transactionHistoryRepository.getCountOfDebitorVpa(transactionRequestDTO.getDebitor_vpa()));
                fraudCheckRequestDTO.setCreditor_txn_history(transactionHistoryRepository.getCountOfCreditorVpa(transactionRequestDTO.getCreditor_vpa()));
                if(transactionHistoryRepository.getAvgDebitorTxnAmount(transactionRequestDTO.getDebitor_vpa()) == null){
                    fraudCheckRequestDTO.setDebitor_avg_txn(BigDecimal.ZERO);
                }
                else {
                    fraudCheckRequestDTO.setDebitor_avg_txn(transactionHistoryRepository.getAvgDebitorTxnAmount(transactionRequestDTO.getDebitor_vpa()));
                }

                print("Starting to call risk and fraud microservice to check our transaction");
                long beforeCallingRiskFraudService = System.currentTimeMillis();
                print("Current Timestamp in Milliseconds: " + beforeCallingRiskFraudService);

                FraudCheckResponseDTO fraudCheckResponseDTO = riskFraudCallerService.sendDataToRiskFraudService(fraudCheckRequestDTO);
                long afterCallingRiskFraudService = System.currentTimeMillis();
                print("Current Timestamp in Milliseconds: " + afterCallingRiskFraudService);
                long apiRTT = afterCallingRiskFraudService - beforeCallingRiskFraudService;
                print("Round trip time for the API was:" + apiRTT + " ms");
                print("Response fetched from risk and fraud");

                if (fraudCheckResponseDTO.getFraud_prediction()) {
                    status = "Fraud Payment Detected";
                    TransactionStatus fraudValidation = new TransactionStatus(txnId, transactionRequestDTO.getDebitor_vpa(),
                            transactionRequestDTO.getCreditor_vpa(), transactionRequestDTO.getAmount(), transactionRequestDTO.getCurrency(),
                            status, transactionRequestDTO.getNote(), transactionInitiatedTime, LocalDateTime.now());
                    TransactionStatus fraudValidationPassed = transactionRepository.save(fraudValidation);
                    response.setFraud_prediction(fraudCheckResponseDTO.getFraud_prediction());

                    response.setFraud_probability(fraudCheckResponseDTO.getFraud_probability());
                    response.setMessage("Fraud Payment Detected");
                    response.setTimestamp(LocalDateTime.now());
                    return ResponseEntity.status(HttpStatus.OK).body(response); // jaha v return kr rhe code aage nahi jayega
                }
                status = "Payment is not Fraud";
                TransactionStatus fraudValidation = new TransactionStatus(txnId, transactionRequestDTO.getDebitor_vpa(),
                        transactionRequestDTO.getCreditor_vpa(), transactionRequestDTO.getAmount(), transactionRequestDTO.getCurrency(),
                        status, transactionRequestDTO.getNote(), transactionInitiatedTime, LocalDateTime.now());
                TransactionStatus fraudValidationPassed = transactionRepository.save(fraudValidation);


                settlementService.settleTransaction(transactionRequestDTO, txnId, transactionInitiatedTime);
                notificationAndPostingService.sendNotification(transactionRequestDTO, txnId, transactionInitiatedTime);
                notificationAndPostingService.paymentPosting(transactionRequestDTO, txnId, transactionInitiatedTime);
                String responseStatus = "Transaction Completed";
                String responseMessage = "Payment completed successfully";
                TransactionResponseDTO sendResponse = new TransactionResponseDTO(txnId, transactionRequestDTO.getDebitor_vpa(), transactionRequestDTO.getCreditor_vpa(),
                        transactionRequestDTO.getAmount(), transactionRequestDTO.getCurrency(), responseStatus, transactionRequestDTO.getNote(), responseMessage,
                        transactionInitiatedTime, LocalDateTime.now());
                sendResponse.setFraud_probability(fraudCheckResponseDTO.getFraud_probability());
                sendResponse.setFraud_prediction(fraudCheckResponseDTO.getFraud_prediction());
                return ResponseEntity.status(HttpStatus.OK).body(sendResponse);
            }
        }
    }

        public ResponseEntity<List<TransactionResponseDTO>> getTransactionHistoryTable() {
            List<TransactionHistory> fetchedTxnHistory = transactionHistoryRepository.findAll();
            Integer tableSize = fetchedTxnHistory.size();
            List<TransactionResponseDTO> showTxnHistoryTable = new ArrayList<>();
            for (int pos = 0; pos < tableSize; pos++) {
                TransactionHistory fetchedTxnHistoryTable = fetchedTxnHistory.get(pos);

                TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
                transactionResponseDTO.setTransaction_id(fetchedTxnHistoryTable.getTransaction_id());
                transactionResponseDTO.setDebitor_vpa(fetchedTxnHistoryTable.getDebitor_vpa());
                transactionResponseDTO.setCreditor_vpa(fetchedTxnHistoryTable.getCreditor_vpa());
                transactionResponseDTO.setAmount(fetchedTxnHistoryTable.getAmount());
                transactionResponseDTO.setCurrency(fetchedTxnHistoryTable.getCurrency());
                transactionResponseDTO.setNote_from_debitor(fetchedTxnHistoryTable.getNote_from_debitor());
                transactionResponseDTO.setTransaction_initiated_timestamp(fetchedTxnHistoryTable.getTransaction_initiated_timestamp());
                transactionResponseDTO.setCurrent_status_timestamp(fetchedTxnHistoryTable.getCompleted_timestamp());
                showTxnHistoryTable.add(transactionResponseDTO);

            }
            return ResponseEntity.status(HttpStatus.OK).body(showTxnHistoryTable);

        }

        public ResponseEntity<List<TransactionResponseDTO>> getTransactionStatus(String transaction_id){
        List<TransactionStatus> fetchedTxnStatus = transactionRepository.findByTxnId(transaction_id);
        Integer tableLength = fetchedTxnStatus.size();
        List<TransactionResponseDTO> showTxnStatus = new ArrayList<>();
        for(int pos = 0; pos < tableLength; pos++){
            TransactionStatus fetchedTxnStatusTable = fetchedTxnStatus.get(pos);
            TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
            transactionResponseDTO.setTransaction_id(transaction_id);
            transactionResponseDTO.setDebitor_vpa(fetchedTxnStatusTable.getDebitor_vpa());
            transactionResponseDTO.setCreditor_vpa(fetchedTxnStatusTable.getCreditor_vpa());
            transactionResponseDTO.setAmount(fetchedTxnStatusTable.getAmount());
            transactionResponseDTO.setCurrency(fetchedTxnStatusTable.getCurrency());
            transactionResponseDTO.setTxn_status(fetchedTxnStatusTable.getTxn_status());
            transactionResponseDTO.setNote_from_debitor(fetchedTxnStatusTable.getNote_from_debitor());
            transactionResponseDTO.setTransaction_initiated_timestamp(fetchedTxnStatusTable.getTransaction_initiated_timestamp());
            transactionResponseDTO.setCurrent_status_timestamp(fetchedTxnStatusTable.getCurrent_status_timestamp());
            showTxnStatus.add(transactionResponseDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(showTxnStatus);

        }



        public BigDecimal fetchBalanceFromVpaId (String vpaId){
            List<VpaBalance> vpaInDb = vpaBalanceRepository.findByVpa_id(vpaId);
            VpaBalance fetchedVpaBalance = vpaInDb.get(0);
            BigDecimal fetchedBalance = fetchedVpaBalance.getVpa_balance();
            return fetchedBalance;
        }




    private Boolean validateDebitorVpa(String debitor_vpa){
       List<Vpa> checkDebitorVpaInDb = vpaRepository.findByVpa_id(debitor_vpa);
       if(checkDebitorVpaInDb.isEmpty()) {
           return false;
       }
       else{
           return true;
       }
    }
    private Boolean validateCreditorVpa(String creditor_vpa){
        List<Vpa> checkCreditorVpaInDb = vpaRepository.findByVpa_id(creditor_vpa);
        if(checkCreditorVpaInDb.isEmpty()) {
            return false;
        }
        else{
            return true;
        }
    }
    private Boolean validateDebitorPin(String debitor_vpa , Integer debitor_pin){
        List<Vpa> vpaFromDbList = vpaRepository.findByVpa_id(debitor_vpa);
        Vpa vpaFromDb = vpaFromDbList.get(0);
        Integer pinFromDb = vpaFromDb.getPin();
        if(Objects.equals(debitor_pin, pinFromDb)){
            return true;
        }
        else{
            return false;
        }
    }
}

