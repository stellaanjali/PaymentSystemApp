package org.paymentSystemApp.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
//    @JsonProperty("id")
//    @Column(name="id")
//    private Integer id;
    @JsonProperty("user_id")
    @Column(name="user_id")
    private Integer user_id;
    @JsonProperty("phone_number")
    @Column(name="phone_number")
    private String phone_number;
    @JsonProperty("bank_name")
    @Column(name="bank_name")
    private String bank_name;
    @JsonProperty("vpa_id")
    @Column(name="vpa_id")
    private String vpa_id;

}
