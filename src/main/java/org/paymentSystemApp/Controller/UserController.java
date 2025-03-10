package org.paymentSystemApp.Controller;

import org.paymentSystemApp.Model.LoginRequest;
import org.paymentSystemApp.Model.User;
import org.paymentSystemApp.Model.UserRequestDTO;
import org.paymentSystemApp.Model.UserResponseDTO;
import org.paymentSystemApp.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class UserController {
    private final UserService userService;


    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity <UserResponseDTO> registerUser(@RequestBody UserRequestDTO userRequestDTO){
       return userService.signupUser(userRequestDTO);

    }
    @GetMapping("/fetchUsers")
    public ResponseEntity<List<UserResponseDTO>> getUsers(){
        return userService.fetchUsers();
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginUser(@RequestBody UserRequestDTO loginRequest){
          ResponseEntity<UserResponseDTO> var = userService.userLogin(loginRequest);
         return var;
    }

}
