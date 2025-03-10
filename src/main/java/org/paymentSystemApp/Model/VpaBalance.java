
package org.paymentSystemApp.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "vpa_balance")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class VpaBalance {

    @Id
    @JsonProperty("vpa_id")
    @Column(name="vpa_id")
    private String vpa_id;
    @JsonProperty("vpa_balance")
    @Column(name="vpa_balance")
    private BigDecimal vpa_balance = BigDecimal.ZERO;
    @JsonProperty("last_updated")
    @Column(name="last_updated")
    private LocalDateTime last_updated;

}
