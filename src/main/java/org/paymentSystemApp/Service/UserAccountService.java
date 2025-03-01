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
        Integer userId = vpa.getUserId();
        List<Vpa> checkVpaIdInDb = vpaRepository.findByUserId(userId);
        if (checkVpaIdInDb.isEmpty()) {
            String createdVpa = "";
            createdVpa += vpa.getPhoneNumber();
            createdVpa += vpa.getUserId();
            createdVpa += "@";
            createdVpa += vpa.getBankName();
            Vpa vpaToBeSavedInDb = new Vpa();
            vpaToBeSavedInDb.setUserId(vpa.getUserId());
            vpaToBeSavedInDb.setPhoneNumber(vpa.getPhoneNumber());
            vpaToBeSavedInDb.setBankName(vpa.getBankName());
            vpaToBeSavedInDb.setVpaId(createdVpa);

            Vpa savedVpa = vpaRepository.save(vpaToBeSavedInDb);
            VpaDTO vpaDto = new VpaDTO();
            vpaDto.setUserId(savedVpa.getId());
            vpaDto.setPhoneNumber(savedVpa.getPhoneNumber());
            vpaDto.setBankName(savedVpa.getBankName());
            vpaDto.setVpaId(savedVpa.getVpaId());
            vpaDto.setMessage("VPA created successfully!");
            return ResponseEntity.status(HttpStatus.CREATED).body(vpaDto);
        } else {
            VpaDTO vpaDto = new VpaDTO();
            vpaDto.setMessage("VPA already exists!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(vpaDto);
        }



    }
}