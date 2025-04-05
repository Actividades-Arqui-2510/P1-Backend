package com.example.p1backend.soap;

import com.example.p1backend.dto.DoctorDTO;
import com.example.p1backend.dto.PatientDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;

import java.util.List;

@WebService(name = "UserService", targetNamespace = "http://soap.p1backend.example.com/")
public interface UserSoapService {
    @WebMethod
    @WebResult(name = "patients")
    List<PatientDTO> getAllPatients();
    
    @WebMethod
    @WebResult(name = "doctors")
    List<DoctorDTO> getAllDoctors();

    @WebMethod
    @WebResult(name = "patient")
    PatientDTO getPatientById(@WebParam(name = "patientId") Long id);

    @WebMethod
    @WebResult(name = "patient")
    PatientDTO getPatientByEmail(@WebParam(name = "email") String email);

    @WebMethod
    @WebResult(name = "patient")
    PatientDTO loginPatient(
            @WebParam(name = "email") String email,
            @WebParam(name = "password") String password
    );

    @WebMethod
    @WebResult(name = "doctor")
    DoctorDTO getDoctorById(@WebParam(name = "doctorId") Long id);

    @WebMethod
    @WebResult(name = "doctor")
    DoctorDTO getDoctorByEmail(@WebParam(name = "email") String email);

    @WebMethod
    @WebResult(name = "doctor")
    DoctorDTO loginDoctor(
            @WebParam(name = "email") String email,
            @WebParam(name = "password") String password
    );

    @WebMethod
    @WebResult(name = "patient")
    PatientDTO savePatient(@WebParam(name = "patient") PatientDTO patient);

    @WebMethod
    @WebResult(name = "doctor")
    DoctorDTO saveDoctor(@WebParam(name = "doctor") DoctorDTO doctor);

    @WebMethod
    @WebResult(name = "patient")
    PatientDTO updatePatient(@WebParam(name = "patient") PatientDTO patient);

    @WebMethod
    @WebResult(name = "doctor")
    DoctorDTO updateDoctor(@WebParam(name = "doctor") DoctorDTO doctor);

    @WebMethod
    void deletePatient(@WebParam(name = "patientId") Long id);

    @WebMethod
    void deleteDoctor(@WebParam(name = "doctorId") Long id);
}
