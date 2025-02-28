package org.paymentSystemApp.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "vpa")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter


public class Vpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="user_id")
    private Integer userId;
    @Column(name="phoneNumber")
    private String phoneNumber;
    @Column(name="bankName")
    private String bankName;
    @Column(name="vpa_id")
    private String vpaId;

}
