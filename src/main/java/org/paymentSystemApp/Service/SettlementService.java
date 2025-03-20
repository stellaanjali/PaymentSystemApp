package org.paymentSystemApp.Service;

import org.paymentSystemApp.Model.TransactionRequestDTO;
import org.paymentSystemApp.Model.TransactionStatus;
import org.paymentSystemApp.Model.VpaBalance;
import org.paymentSystemApp.Repository.TransactionRepository;
import org.paymentSystemApp.Repository.VpaBalanceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service

public class SettlementService {
    private  final VpaBalanceRepository vpaBalanceRepository;
    private  final TransactionRepository transactionRepository;

    public SettlementService(VpaBalanceRepository vpaBalanceRepository, TransactionRepository transactionRepository) {
        this.vpaBalanceRepository = vpaBalanceRepository;
        this.transactionRepository = transactionRepository;
    }


    public void settleTransaction(TransactionRequestDTO transactionRequestDTO, String txnId, LocalDateTime transactionInitiatedTime) {
        //1. we have to update two tables, vpaBalance table and txnStatus table
        //2. first we are saving in vpaBalance table,
        //3. search debitorVpa in vpaBalance table and update it
        //4. search creditorVpa in vpaBalance table and update it
        //5. update txnStatus table with status, "Txn Settled"
        // txn complete hoga jab notification jayega

        String debitorVpa = transactionRequestDTO.getDebitor_vpa();
        String creditorVpa = transactionRequestDTO.getCreditor_vpa();
        BigDecimal amountToBeSettled = transactionRequestDTO.getAmount();
        List<VpaBalance> debitorVpaInDb = vpaBalanceRepository.findByVpa_id(debitorVpa);
        VpaBalance fetchedVpaBalance = debitorVpaInDb.get(0);
        BigDecimal fetchedBalance = fetchedVpaBalance.getVpa_balance();
        BigDecimal updateDebitorBalance = fetchedBalance.subtract(amountToBeSettled);
        System.out.println(fetchedBalance+" "+updateDebitorBalance+" "+amountToBeSettled);
        fetchedVpaBalance.setVpa_balance(updateDebitorBalance);
        fetchedVpaBalance.setLast_updated(LocalDateTime.now());
        VpaBalance updateDBalance = vpaBalanceRepository.save(fetchedVpaBalance);

        List<VpaBalance> creditorVpaInDb = vpaBalanceRepository.findByVpa_id(creditorVpa);
        VpaBalance fetchedCreditorVpaBalance = creditorVpaInDb.get(0);
        BigDecimal fetchedCreditorBalance = fetchedCreditorVpaBalance.getVpa_balance();
        BigDecimal updateCreditorBalance = fetchedCreditorBalance.add(amountToBeSettled);
        System.out.println(fetchedCreditorBalance+" "+updateCreditorBalance+" "+amountToBeSettled);
        fetchedCreditorVpaBalance.setVpa_balance(updateCreditorBalance);
        fetchedCreditorVpaBalance.setLast_updated(LocalDateTime.now());
        VpaBalance updateCBalance = vpaBalanceRepository.save(fetchedCreditorVpaBalance);

        String status = "Transaction Settled";
        TransactionStatus txnStatus = new TransactionStatus(txnId, transactionRequestDTO.getDebitor_vpa(),
                transactionRequestDTO.getCreditor_vpa(), transactionRequestDTO.getAmount(), transactionRequestDTO.getCurrency(),
                status, transactionRequestDTO.getNote(), transactionInitiatedTime, LocalDateTime.now());
        TransactionStatus savedTxnStatus = transactionRepository.save(txnStatus);
//        String response = "Payment Settled";
//        return response;
    }
}

