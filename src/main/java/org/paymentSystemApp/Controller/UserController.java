package org.paymentSystemApp.Controller;

import org.paymentSystemApp.Model.User;
import org.paymentSystemApp.Model.UserDTO;
import org.paymentSystemApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController

public class UserController {
    private final UserService userService;
    @Autowired

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity <UserDTO> registerUser(@RequestBody User user){
       return userService.signupUser(user);

    }
    @GetMapping("/fetchUsers")
    public ResponseEntity<List<UserDTO>> getUsers(){
        return userService.fetchUsers();
    }


}
