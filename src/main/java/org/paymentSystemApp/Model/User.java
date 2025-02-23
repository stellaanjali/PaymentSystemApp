package org.paymentSystemApp.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "users")


public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="name")
    private String name;
//    @Column(name="phoneNumber", nullable=false, unique=true)
//    private String phoneNumber;

    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
//    @CreationTimestamp
//    @Column(updatable = false, nullable = false)
//    private LocalDateTime createdAt;

    public User(){

    }
    public User(Integer id, String name,  String email, String password){
        this.id = id;
        this.name = name;
//        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;

    }
    // Getters and Setters
    public Integer getId(){
        return id;
    }
    public String getName(){
        return name;
    }
//    public String getPhoneNumber(){
//        return phoneNumber;
//    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }

    public void setId(Integer id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;//this.name class ka variable hai jisme bahar se naam/name aake jur rha , class ke variable ko access krne ke liye this use krte
    }
//    public void setPhoneNumber(String phoneNumber){
//        this.phoneNumber = phoneNumber;
//    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPassword(String password){
        this.password = password;
    }



}

