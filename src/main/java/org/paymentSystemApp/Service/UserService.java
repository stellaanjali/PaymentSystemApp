package org.paymentSystemApp.Service;

import org.paymentSystemApp.Model.LoginRequest;
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
        String email = user.getEmail();
        List<User> checkEmailInDb = userRepository.findByEmail(email);
        if(checkEmailInDb.isEmpty()) {
            System.out.println("Starting to save users");
            User savedUser = userRepository.save(user);//user table mei saman save krta h
            System.out.println("User saved in DB");
            //UserDTO userDto = new UserDTO(savedUser);//user type se userdto mei ja rhe..kyuki return type is userdto
            UserDTO userDto= new UserDTO();
            userDto.setId(savedUser.getId());
            userDto.setName(savedUser.getName());
            userDto.setEmail(savedUser.getEmail());
            userDto.setPassword(savedUser.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
        }
        else{
            UserDTO userDto = new UserDTO();

            userDto.setMessage("Email already exists! Please use a different email.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userDto);

        }
    }


        public ResponseEntity<List<UserDTO>> fetchUsers(){
            System.out.println("Starting to fetch users");
            List<User> usersFetched = userRepository.findAll();

            // Debugging: Print fetched users
            System.out.println("Fetched Users: " + usersFetched);

            if (usersFetched.isEmpty()) {
                System.out.println("No users found in DB.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
            }
            System.out.println("Users Fetched Successfully");
            // Debugging: Print mapped DTOs
            List<UserDTO> userDTOs = usersFetched.stream().map(user -> new UserDTO(user)).toList();
            System.out.println("Mapped DTOs: " + userDTOs);
            return ResponseEntity.status(HttpStatus.OK).body(userDTOs);
        }


        public ResponseEntity<UserDTO> userLogin(LoginRequest loginRequest){
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        System.out.println("Starting to fetch email from DB");
        List<User> loginCredsCheck = userRepository.findByEmail(email);
        System.out.println("Fetched email from DB");
        if(loginCredsCheck.isEmpty()){
            UserDTO userDTO = new UserDTO();
            userDTO.setMessage("Please enter valid login credentails.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userDTO);
        }
        else{
            User fetchedFromDB = loginCredsCheck.get(0);
            String passwordFromDB = fetchedFromDB.getPassword();
            if(password.equals(passwordFromDB)){
                UserDTO userDTO = new UserDTO();
                userDTO.setMessage("Login Successful");
                return ResponseEntity.status(HttpStatus.OK).body(userDTO);
            }
            else{
                UserDTO userDTO = new UserDTO();
                userDTO.setMessage("Please enter valid login credentails.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userDTO);
            }
        }
    }
}
