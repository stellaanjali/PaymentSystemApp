package org.paymentSystemApp.Model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "transaction_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class TransactionHistory {
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
    @Column(name="note_from_debitor")
    private String note_from_debitor;
    @Column(name="transaction_initiated_timestamp")
    private LocalDateTime transaction_initiated_timestamp;
    @Column(name="completed_timestamp")
    private LocalDateTime completed_timestamp;

    public TransactionHistory(String transaction_id, String debitor_vpa, String creditor_vpa, BigDecimal amount, String currency, String note_from_debitor, LocalDateTime transaction_initiated_timestamp, LocalDateTime completed_timestamp){
        this.transaction_id = transaction_id;
        this.debitor_vpa = debitor_vpa;
        this.creditor_vpa = creditor_vpa;
        this.amount = amount;
        this.currency = currency;
        this.note_from_debitor = note_from_debitor;
        this.transaction_initiated_timestamp = transaction_initiated_timestamp;
        this.completed_timestamp = completed_timestamp;
    }

}
