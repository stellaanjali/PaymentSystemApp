package org.paymentSystemApp.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Data

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)//null chizen nahi dikhegi in response
public class UserResponseDTO {


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
    @JsonProperty("message")
    private String message;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
//        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public UserResponseDTO(){

    }

//    public UserDTO(Integer id, String name,  String email, String password){
//        this.id = id;
//        this.name = name;
////        this.phoneNumber = phoneNumber;
//        this.email = email;
//        this.password = password;
//
//    }

    public UserResponseDTO(String email, String password){
        this.email = email;
        this.password = password;
    }

    public UserResponseDTO(LoginRequest loginRequest, String message){
        this.email = loginRequest.getEmail();
        this.message = message;
    }

//    public UserDTO(User user) {
//
//    }
}




