package org.paymentSystemApp.Service;

import org.paymentSystemApp.Model.TransactionHistory;
import org.paymentSystemApp.Model.TransactionRequestDTO;
import org.paymentSystemApp.Model.TransactionStatus;
import org.paymentSystemApp.Repository.TransactionHistoryRepository;
import org.paymentSystemApp.Repository.TransactionRepository;
import org.paymentSystemApp.Repository.VpaBalanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service

public class NotificationAndPostingService {
    // notification sent to the creditor and debitor ..print this
    // save in txn status table..status will be notification sent
    // save txn completed in txn status table
    // add completed txn to the txn history table

    private final TransactionRepository transactionRepository;
    private final VpaBalanceRepository vpaBalanceRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;

    public NotificationAndPostingService(TransactionRepository transactionRepository, VpaBalanceRepository vpaBalanceRepository, TransactionHistoryRepository transactionHistoryRepository){
        this.transactionRepository = transactionRepository;
        this.vpaBalanceRepository = vpaBalanceRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    public void sendNotification(TransactionRequestDTO transactionRequestDTO, String txnId, LocalDateTime transactionInitiatedTime){
        System.out.println("Notification sent to the Debitor and Creditor");
        String status = "Notification Sent";
        TransactionStatus txnStatus = new TransactionStatus(txnId, transactionRequestDTO.getDebitor_vpa(),
                transactionRequestDTO.getCreditor_vpa(), transactionRequestDTO.getAmount(), transactionRequestDTO.getCurrency(),
                status, transactionRequestDTO.getNote(), transactionInitiatedTime, LocalDateTime.now());
        TransactionStatus savedTxnStatus = transactionRepository.save(txnStatus);
    }

    public void paymentPosting(TransactionRequestDTO transactionRequestDTO, String txnId, LocalDateTime transactionInitiatedTime){
        String status = "Transaction Completed";
        TransactionStatus txnStatus = new TransactionStatus(txnId, transactionRequestDTO.getDebitor_vpa(),
                transactionRequestDTO.getCreditor_vpa(), transactionRequestDTO.getAmount(), transactionRequestDTO.getCurrency(),
                status, transactionRequestDTO.getNote(), transactionInitiatedTime, LocalDateTime.now());
        TransactionStatus savedTxnStatus = transactionRepository.save(txnStatus);

        TransactionHistory txnHistory = new TransactionHistory(txnId, transactionRequestDTO.getDebitor_vpa(), transactionRequestDTO.getCreditor_vpa(),
                transactionRequestDTO.getAmount(), transactionRequestDTO.getCurrency(), transactionRequestDTO.getNote(), transactionInitiatedTime, LocalDateTime.now());
        TransactionHistory savedTxnHistory = transactionHistoryRepository.save(txnHistory);
    }
}
