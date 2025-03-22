package org.paymentSystemApp.Service;

import org.paymentSystemApp.Exceptions.InputValidationException;
import org.paymentSystemApp.Model.*;
import org.paymentSystemApp.Repository.VpaBalanceRepository;
import org.paymentSystemApp.Repository.VpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserAccountService {
    private final VpaRepository vpaRepository;
    private final VpaBalanceRepository vpaBalanceRepository;


    public UserAccountService(VpaRepository vpaRepository, VpaBalanceRepository vpaBalanceRepository) {
        this.vpaRepository = vpaRepository;
        this.vpaBalanceRepository = vpaBalanceRepository;

    }

    public ResponseEntity<VpaResponseDTO> createVpa(VpaRequestDTO vpaRequestDTO){
        try {
            InputValidationService.validatePhoneNumber(vpaRequestDTO.getPhone_number());
        } catch (InputValidationException e) {
            throw new RuntimeException(e);
        }
        Integer user_id = vpaRequestDTO.getUser_id();
        List<Vpa> checkVpaIdInDb = vpaRepository.findByUser_id(user_id);
        if (checkVpaIdInDb.isEmpty()) {
            String createdVpa = "";
            createdVpa += vpaRequestDTO.getPhone_number();
            createdVpa += vpaRequestDTO.getUser_id();
            createdVpa += "@";
            createdVpa += vpaRequestDTO.getBank_name();
            Vpa vpaToBeSavedInDb = new Vpa();// saving in database
            vpaToBeSavedInDb.setUser_id(vpaRequestDTO.getUser_id());
            vpaToBeSavedInDb.setPhone_number(vpaRequestDTO.getPhone_number());
            vpaToBeSavedInDb.setBank_name(vpaRequestDTO.getBank_name());
            vpaToBeSavedInDb.setVpa_id(createdVpa);
            vpaToBeSavedInDb.setPin(vpaRequestDTO.getPin());
            Vpa savedVpa = vpaRepository.save(vpaToBeSavedInDb);

            VpaResponseDTO vpaResponseDto = new VpaResponseDTO();// creating response

            vpaResponseDto.setUser_id(savedVpa.getUser_id());
            vpaResponseDto.setPhone_number(savedVpa.getPhone_number());
            vpaResponseDto.setBank_name(savedVpa.getBank_name());
            vpaResponseDto.setVpa_id(savedVpa.getVpa_id());
            vpaResponseDto.setMessage("VPA created successfully!");
            return ResponseEntity.status(HttpStatus.CREATED).body(vpaResponseDto);
        } else {
            VpaResponseDTO vpaResponseDto = new VpaResponseDTO();

            vpaResponseDto.setMessage("VPA already exists!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(vpaResponseDto);
        }
    }

    public ResponseEntity<VpaBalanceResponseDTO> addBalance(VpaBalanceRequestDTO vpaBalanceRequestDTO) {
        String vpa_id = vpaBalanceRequestDTO.getVpa_id();
        List<VpaBalance> fetchedVpaBalanceList = vpaBalanceRepository.findByVpa_id(vpa_id);
        if (!fetchedVpaBalanceList.isEmpty()) {
            VpaBalance fetchedVpaBalance = fetchedVpaBalanceList.get(0);
            VpaBalance updatedVpaBalanceToBeSavedInDb = new VpaBalance();
            updatedVpaBalanceToBeSavedInDb.setVpa_id(vpa_id);
            updatedVpaBalanceToBeSavedInDb.setVpa_balance(vpaBalanceRequestDTO.getVpa_balance());
            LocalDateTime timestamp = LocalDateTime.now();
            updatedVpaBalanceToBeSavedInDb.setLast_updated(timestamp);
            VpaBalance savedVpaBalance = vpaBalanceRepository.save(updatedVpaBalanceToBeSavedInDb);
            VpaBalanceResponseDTO vpaBalanceResponseDTO = new VpaBalanceResponseDTO();
            vpaBalanceResponseDTO.setVpa_id(savedVpaBalance.getVpa_id());
            vpaBalanceResponseDTO.setVpa_balance(savedVpaBalance.getVpa_balance());
            LocalDateTime currentTimestamp = LocalDateTime.now();
            vpaBalanceResponseDTO.setLast_updated(currentTimestamp);
            vpaBalanceResponseDTO.setMessage("VPA Balance updated successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(vpaBalanceResponseDTO);
        } else {
            VpaBalanceResponseDTO vpaBalanceResponseDTO = new VpaBalanceResponseDTO();
            vpaBalanceResponseDTO.setMessage("VPA Id does not exist!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(vpaBalanceResponseDTO);
        }
    }


    // vpa_id se we fetched all the lists containing that vpa_id, ek list ho sakta cuz vpa_id is unique, ab bs wo fetched balance ko response mei bhej dena h
    public ResponseEntity<VpaBalanceResponseDTO> fetchBalance(String vpa_id) {
        List<VpaBalance> fetchedBalanceList = vpaBalanceRepository.findByVpa_id(vpa_id);
        if (!fetchedBalanceList.isEmpty()) {
            VpaBalance fetchedVpaBalance = fetchedBalanceList.get(0);
            VpaBalanceResponseDTO vpaBalanceResponseDTO = new VpaBalanceResponseDTO();
            vpaBalanceResponseDTO.setVpa_id(vpa_id);
            vpaBalanceResponseDTO.setVpa_balance(fetchedVpaBalance.getVpa_balance());
            LocalDateTime currentTimestamp = LocalDateTime.now();
            vpaBalanceResponseDTO.setLast_updated(currentTimestamp);
            vpaBalanceResponseDTO.setMessage("Balance Fetched Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(vpaBalanceResponseDTO);

        } else {
            VpaBalanceResponseDTO vpaBalanceResponseDTO = new VpaBalanceResponseDTO();
            vpaBalanceResponseDTO.setMessage("VPA Id does not exist!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(vpaBalanceResponseDTO);

        }

    }

    public ResponseEntity<List<VpaBalanceResponseDTO>> fetchedVpaBalanceForAll(){
        List<VpaBalance> fetchedVpaBalanceForAll = vpaBalanceRepository.findAll();
        Integer listSize = fetchedVpaBalanceForAll.size();

        List<VpaBalanceResponseDTO> responseDTO = new ArrayList<>();
        for(Integer pos=0; pos<listSize; pos++) {
            VpaBalance fetchedVpaBalance = fetchedVpaBalanceForAll.get(pos);
            VpaBalanceResponseDTO vpaBalanceResponseDTO = new VpaBalanceResponseDTO();
            vpaBalanceResponseDTO.setVpa_id(fetchedVpaBalance.getVpa_id());
            vpaBalanceResponseDTO.setVpa_balance(fetchedVpaBalance.getVpa_balance());
            LocalDateTime currentTimestamp = LocalDateTime.now();
            vpaBalanceResponseDTO.setLast_updated(currentTimestamp);
            responseDTO.add(vpaBalanceResponseDTO);

        }
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    public ResponseEntity<VpaBalanceResponseDTO> addDataToVpaBalance (VpaBalanceRequestDTO vpaBalanceRequestDTO){

        String vpaId = vpaBalanceRequestDTO.getVpa_id();
        List<VpaBalance> fetchedDataFromVpaBalance = vpaBalanceRepository.findByVpa_id(vpaId);
        if(fetchedDataFromVpaBalance.isEmpty()){
            VpaBalance vpaBalance = new VpaBalance();
            vpaBalance.setVpa_balance(vpaBalanceRequestDTO.getVpa_balance());
            vpaBalance.setVpa_id(vpaBalanceRequestDTO.getVpa_id());
            LocalDateTime currentTimestamp = LocalDateTime.now();
            vpaBalance.setLast_updated(currentTimestamp);

            VpaBalance savedInDb = vpaBalanceRepository.save(vpaBalance);
            VpaBalanceResponseDTO responseDTO = new VpaBalanceResponseDTO();// isme saare fields hai but null h
            responseDTO.setVpa_id(savedInDb.getVpa_id());
            responseDTO.setVpa_balance(savedInDb.getVpa_balance());
            responseDTO.setLast_updated(savedInDb.getLast_updated());
            responseDTO.setMessage("Data Saved Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

        }
        else{
            VpaBalanceResponseDTO vpaBalanceResponseDTO = new VpaBalanceResponseDTO();
            vpaBalanceResponseDTO.setMessage("Data already exists!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(vpaBalanceResponseDTO);

        }
    }


}
//{
//vpa id : s1
//vpa bal : s2
//timestamp : s3
//},
//        {
//vpa id : s4
//vpa bal : s5
//timestamp : s6
//},


