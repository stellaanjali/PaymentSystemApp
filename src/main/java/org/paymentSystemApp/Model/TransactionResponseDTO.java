package org.paymentSystemApp.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class TransactionResponseDTO {

    @JsonProperty("transaction_id")
    private String transaction_id;
    @JsonProperty("debitor_vpa")
    private String debitor_vpa;
    @JsonProperty("creditor_vpa")
    private String creditor_vpa;
    @JsonProperty("amount")
    private BigDecimal amount ;
    @JsonProperty("currency")
    private String currency = "INR";
    @JsonProperty("txn_status")
    private String txn_status;
    @JsonProperty("note_from_debitor")
    private String note_from_debitor;
    @JsonProperty("message")
    private String message;
    @JsonProperty("transaction_initiated_timestamp")
    private LocalDateTime transaction_initiated_timestamp;
    @JsonProperty("current_status_timestamp")
    private LocalDateTime current_status_timestamp;
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    @JsonProperty("fraud_prediction")
    private Boolean fraud_prediction;
    @JsonProperty("fraud_probability")
    private Float fraud_probability;

    public TransactionResponseDTO(String transaction_id, String debitor_vpa, String creditor_vpa, BigDecimal amount,
                                  String currency, String txn_status, String note_from_debitor, String message,
                                  LocalDateTime transaction_initiated_timestamp, LocalDateTime current_status_timestamp){
        this.transaction_id = transaction_id;
        this.debitor_vpa = debitor_vpa;
        this.creditor_vpa = creditor_vpa;
        this.amount = amount;
        this.currency = currency;
        this.txn_status = txn_status;
        this.note_from_debitor = note_from_debitor;
        this.message = message;
        this.transaction_initiated_timestamp = transaction_initiated_timestamp;
        this.current_status_timestamp = current_status_timestamp;
    }
}
