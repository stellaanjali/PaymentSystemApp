package org.paymentSystemApp.Service;

import org.paymentSystemApp.Model.LoginRequest;
import org.paymentSystemApp.Model.User;
import org.paymentSystemApp.Model.UserRequestDTO;
import org.paymentSystemApp.Model.UserResponseDTO;
import org.paymentSystemApp.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final  UserRepository userRepository;

    public  UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public ResponseEntity<UserResponseDTO> signupUser(UserRequestDTO userRequestDTO){
        String email = userRequestDTO.getEmail();
        List<User> checkEmailInDb = userRepository.findByEmail(email);
        if(checkEmailInDb.isEmpty()) {
            User user = new User();
            user.setName(userRequestDTO.getName());
            user.setEmail(userRequestDTO.getEmail());
            user.setPassword(userRequestDTO.getPassword());
            System.out.println("Starting to save users");
            User savedUser = userRepository.save(user);//user table mei saman save krta h
            System.out.println("User saved in DB");
            //UserDTO userDto = new UserDTO(savedUser);//user type se userdto mei ja rhe..kyuki return type is userdto
            UserResponseDTO userResponseDto = new UserResponseDTO();
            userResponseDto.setId(savedUser.getId());
            userResponseDto.setName(savedUser.getName());
            userResponseDto.setEmail(savedUser.getEmail());
            userResponseDto.setPassword(savedUser.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
        }
        else{
            UserResponseDTO userResponseDto = new UserResponseDTO();

            userResponseDto.setMessage("Email already exists! Please use a different email.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userResponseDto);

        }
    }


        public ResponseEntity<List<UserResponseDTO>> fetchUsers(){
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
            List<UserResponseDTO> userResponseDTOS = usersFetched.stream().map(user -> new UserResponseDTO(user)).toList();
            System.out.println("Mapped DTOs: " + userResponseDTOS);
            return ResponseEntity.status(HttpStatus.OK).body(userResponseDTOS);
        }


        public ResponseEntity<UserResponseDTO> userLogin(UserRequestDTO loginRequest){
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        System.out.println("Starting to fetch email from DB");
        List<User> loginCredsCheck = userRepository.findByEmail(email);
        System.out.println("Fetched email from DB");
        if(loginCredsCheck.isEmpty()){
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.setMessage("Please enter valid login credentails.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userResponseDTO);
        }
        else{
            User fetchedFromDB = loginCredsCheck.get(0);
            String passwordFromDB = fetchedFromDB.getPassword();
            if(password.equals(passwordFromDB)){
                UserResponseDTO userResponseDTO = new UserResponseDTO();
                userResponseDTO.setMessage("Login Successful");
                return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
            }
            else{
                UserResponseDTO userResponseDTO = new UserResponseDTO();
                userResponseDTO.setMessage("Please enter valid login credentails.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userResponseDTO);
            }
        }
    }
}
