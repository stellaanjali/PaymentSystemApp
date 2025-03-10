package org.paymentSystemApp.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

@NoArgsConstructor
@AllArgsConstructor

public class VpaResponseDTO {

   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer id;
    @Id
    @JsonProperty("user_id")
    private Integer user_id;
    @JsonProperty("phone_number")
    private String phone_number;
    @JsonProperty("bank_name")
    private String bank_name;
    @JsonProperty("vpa_id")
    private String vpa_id;
    @JsonProperty("pin")
    private Integer pin;
    @JsonProperty("message")
    private String message;
}
