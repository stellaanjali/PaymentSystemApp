
package org.paymentSystemApp.Model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "vpaBalance")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class VpaBalance {

    @Column(name="vpa_id")
    private String vpaId;
    @Column(name="vpa_balance")
    private BigDecimal vpaBalance = BigDecimal.ZERO;
    @Column(name="pin")
    private Integer pin;
    @Column(name="last_updated")
    private LocalDateTime lastUpdated;

}
