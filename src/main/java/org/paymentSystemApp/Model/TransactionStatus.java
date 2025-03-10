package org.paymentSystemApp.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "transaction_status")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter


public class TransactionStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="transaction_id")
    private String transaction_id;
    @Column(name="debitor_vpa")
    private String debitor_vpa;
    @Column(name="creditor_vpa")
    private String creditor_vpa;
    @Column(name="amount")
    private BigDecimal amount ;
    @Column(name="currency")
    private String currency = "INR";
    @Column(name="txn_status")
    private String txn_status;
    @Column(name="note_from_debitor")
    private String note_from_debitor;
    @Column(name="transaction_initiated_timestamp")
    private LocalDateTime transaction_initiated_timestamp;
    @Column(name="current_status_timestamp")
    private LocalDateTime current_status_timestamp;
}
