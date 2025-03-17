package org.paymentSystemApp.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*; // lombok mei jo v h sab import kro..* ka matlab sara

@Entity
@Data
@Table(name = "vpa")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter


public class Vpa {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @JsonProperty("id")
//    @Column(name="id")
//    private Integer id;

    @Column(name="user_id")
    private Integer user_id;

    @Column(name="phone_number")
    private String phone_number;
    @Column(name="bank_name")
    private String bank_name;

    @Column(name="vpa_id")
    private String vpa_id;

    @Column(name="pin")
    private Integer pin;

}
