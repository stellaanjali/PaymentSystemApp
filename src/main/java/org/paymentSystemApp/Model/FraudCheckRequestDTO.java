package org.paymentSystemApp.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class FraudCheckRequestDTO {

    @JsonProperty("debitor_balance")
    private BigDecimal debitor_balance;
    @JsonProperty("creditor_balance")
    private BigDecimal creditor_balance;
    @JsonProperty("amount")
    private BigDecimal amount ;
    @JsonProperty("debitor_txn_history")
    private Integer debitor_txn_history;
    @JsonProperty("creditor_txn_history")
    private Integer creditor_txn_history;
    @JsonProperty("debitor_avg_txn")
    private BigDecimal debitor_avg_txn = BigDecimal.ZERO;
}
