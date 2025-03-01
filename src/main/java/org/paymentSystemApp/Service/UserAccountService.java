package org.paymentSystemApp.Service;

import org.paymentSystemApp.Model.User;
import org.paymentSystemApp.Model.UserDTO;
import org.paymentSystemApp.Model.Vpa;
import org.paymentSystemApp.Model.VpaDTO;
import org.paymentSystemApp.Repository.UserRepository;
import org.paymentSystemApp.Repository.VpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserAccountService {
    private final VpaRepository vpaRepository;

    public  UserAccountService(VpaRepository vpaRepository){
        this.vpaRepository = vpaRepository;
    }

    public ResponseEntity<VpaDTO> createVpa(Vpa vpa) {
        Integer user_id = vpa.getUser_id();
        List<Vpa> checkVpaIdInDb = vpaRepository.findByUser_id(user_id);
        if (checkVpaIdInDb.isEmpty()) {
            String createdVpa = "";
            createdVpa += vpa.getPhone_number();
            createdVpa += vpa.getUser_id();
            createdVpa += "@";
            createdVpa += vpa.getBank_name();
            Vpa vpaToBeSavedInDb = new Vpa();
            vpaToBeSavedInDb.setUser_id(vpa.getUser_id());
            vpaToBeSavedInDb.setPhone_number(vpa.getPhone_number());
            vpaToBeSavedInDb.setBank_name(vpa.getBank_name());
            vpaToBeSavedInDb.setVpa_id(createdVpa);

            Vpa savedVpa = vpaRepository.save(vpaToBeSavedInDb);
            VpaDTO vpaDto = new VpaDTO();
            vpaDto.setUser_id(savedVpa.getUser_id());
            vpaDto.setPhone_number(savedVpa.getPhone_number());
            vpaDto.setBank_name(savedVpa.getBank_name());
            vpaDto.setVpa_id(savedVpa.getVpa_id());
            vpaDto.setMessage("VPA created successfully!");
            return ResponseEntity.status(HttpStatus.CREATED).body(vpaDto);
        } else {
            VpaDTO vpaDto = new VpaDTO();
            vpaDto.setMessage("VPA already exists!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(vpaDto);
        }



    }
}