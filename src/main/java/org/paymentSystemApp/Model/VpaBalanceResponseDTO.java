package org.paymentSystemApp.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

@NoArgsConstructor
@AllArgsConstructor


public class VpaBalanceResponseDTO {

    @Id
    @JsonProperty("vpa_id")
    private String vpa_id;
    @JsonProperty("vpa_balance")
    private BigDecimal vpa_balance;
    @JsonProperty("last_updated")
    private LocalDateTime last_updated;
    @JsonProperty("message")
    private String message;

}
