package org.paymentSystemApp.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class UserDTO {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
//    @JsonProperty("phoneNumber")
//    private String phoneNumber;

    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
//        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}




