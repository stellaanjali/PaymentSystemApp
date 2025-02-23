package org.paymentSystemApp.Service;

import org.paymentSystemApp.Model.User;
import org.paymentSystemApp.Model.UserDTO;
import org.paymentSystemApp.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
@Service
public class UserService {
    private final  UserRepository userRepository;

    public  UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public ResponseEntity<UserDTO> signupUser(User user){
        System.out.println("Starting to save products");
        User savedUser= userRepository.save(user);
        System.out.println("Product saved");
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(savedUser));

    }

    public ResponseEntity<List<UserDTO>> fetchUsers(){
        System.out.println("Starting to fetch users");
        List<User> usersFetched = userRepository.findAll();
        System.out.println("Users Fetched");
        List<UserDTO> userDTOs = usersFetched.stream().map(UserDTO::new).toList();    //mapping step
        return ResponseEntity.status(HttpStatus.OK).body(userDTOs);
    }
}
