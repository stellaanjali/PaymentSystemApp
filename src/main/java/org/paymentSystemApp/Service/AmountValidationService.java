package org.paymentSystemApp.Service;

import org.paymentSystemApp.Model.Vpa;
import org.paymentSystemApp.Model.VpaBalance;
import org.paymentSystemApp.Repository.VpaBalanceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service

public class AmountValidationService {
    private final VpaBalanceRepository vpaBalanceRepository;

    public AmountValidationService(VpaBalanceRepository vpaBalanceRepository){
        this.vpaBalanceRepository = vpaBalanceRepository;

    }

    public Boolean validateDebitorBalance(String debitorVpa, BigDecimal amount){
        List<VpaBalance> debitorVpaInDb = vpaBalanceRepository.findByVpa_id(debitorVpa);

        if(debitorVpaInDb.isEmpty()){
            return Boolean.FALSE;
        }
        else{
            VpaBalance fetchedVpaBalance = debitorVpaInDb.get(0);
            BigDecimal fetchedBalance = fetchedVpaBalance.getVpa_balance();
            if(fetchedBalance.compareTo(amount) >= 0){ // to compare two big decimals
                return Boolean.TRUE;
            }
            else{
                return Boolean.FALSE;
            }
        }

    }


}
