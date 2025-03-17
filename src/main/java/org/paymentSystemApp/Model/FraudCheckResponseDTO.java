package org.paymentSystemApp.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class FraudCheckResponseDTO {

    @JsonProperty("fraud_prediction")
    private Boolean fraud_prediction;
    @JsonProperty("fraud_probability")
    private Float fraud_probability;
}
