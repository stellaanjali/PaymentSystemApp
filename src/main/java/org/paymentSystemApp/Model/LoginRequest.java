package org.paymentSystemApp.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@Data

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class LoginRequest {

    private String email;

    private String password;



}