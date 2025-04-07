package com.example.p1backend.soap.impl;

import com.example.p1backend.dto.DoctorDTO;
import com.example.p1backend.dto.PatientDTO;
import com.example.p1backend.service.UserService;
import com.example.p1backend.soap.UserSoapService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;
import jakarta.jws.WebService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@WebService(
        name = "UserService",
        targetNamespace = "http://soap.p1backend.example.com/",
        endpointInterface = "com.example.p1backend.soap.UserSoapService"
)
@ApplicationScoped
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Inject)
public class UserSoapServiceImpl implements UserSoapService {
    private UserService userService;

    @PostConstruct
    public void init() {
        if (userService == null) {
            userService = CDI.current().select(UserService.class).get();
        }
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        return userService.getAllPatients();
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        return userService.getAllDoctors();
    }

    @Override
    public PatientDTO getPatientById(Long id) {
        return userService.getPatientById(id);
    }

    @Override
    public PatientDTO getPatientByEmail(String email) {
        return userService.getPatientByEmail(email);
    }

    @Override
    public PatientDTO loginPatient(String email, String password) {
        return userService.loginPatient(email, password);
    }

    @Override
    public DoctorDTO getDoctorById(Long id) {
        return userService.getDoctorById(id);
    }

    @Override
    public DoctorDTO getDoctorByEmail(String email) {
        return userService.getDoctorByEmail(email);
    }

    @Override
    public DoctorDTO loginDoctor(String email, String password) {
        return userService.loginDoctor(email, password);
    }

    @Override
    public PatientDTO savePatient(PatientDTO patient) {
        return userService.savePatient(patient);
    }

    @Override
    public DoctorDTO saveDoctor(DoctorDTO doctor) {
        return userService.saveDoctor(doctor);
    }

    @Override
    public PatientDTO updatePatient(PatientDTO patient) {
        return userService.updatePatient(patient);
    }

    @Override
    public DoctorDTO updateDoctor(DoctorDTO doctor) {
        return userService.updateDoctor(doctor);
    }

    @Override
    public void deletePatient(Long id) {
        userService.deletePatient(id);
    }

    @Override
    public void deleteDoctor(Long id) {
        userService.deleteDoctor(id);
    }
}
