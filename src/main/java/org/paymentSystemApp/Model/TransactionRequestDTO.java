package org.paymentSystemApp.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter


public class TransactionRequestDTO {

    @JsonProperty("debitor_vpa")
    private String debitor_vpa;
    @JsonProperty("debitor_pin")
    private Integer debitor_pin;
    @JsonProperty("amount")
    private BigDecimal amount ;
    @JsonProperty("creditor_vpa")
    private String creditor_vpa;
    @JsonProperty("currency")
    private String currency = "INR";
    @JsonProperty("note")
    private String note;
}
