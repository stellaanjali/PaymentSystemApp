
package org.paymentSystemApp.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class VpaBalanceRequestDTO {

    @Id
    @JsonProperty("vpa_id")

    private String vpa_id;
    @JsonProperty("vpa_balance")
    private BigDecimal vpa_balance = BigDecimal.ZERO;
    @JsonProperty("last_updated")
    private LocalDateTime last_updated;

}
