//package org.paymentSystemApp.Model;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import jakarta.persistence.Column;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@Getter
//@Setter
//@JsonInclude(JsonInclude.Include.NON_NULL)
//
//@NoArgsConstructor
//@AllArgsConstructor
//
//
//public class VpaBalanceDTO {
//
//    @JsonProperty("vpa_id")
//    private String vpaId;
//    @JsonProperty("vpa_balance")
//    private BigDecimal vpaBalance = BigDecimal.ZERO;
//    @JsonProperty("pin")
//    private Integer pin;
//    @JsonProperty("last_updated")
//    private LocalDateTime lastUpdated;
//
//}
