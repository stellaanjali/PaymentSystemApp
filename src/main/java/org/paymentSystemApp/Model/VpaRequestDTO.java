package org.paymentSystemApp.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;




@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class VpaRequestDTO {


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

}
