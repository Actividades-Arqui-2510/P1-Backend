package com.example.p1backend.service;

import com.example.p1backend.dto.DoctorDTO;
import com.example.p1backend.dto.PatientDTO;
import com.example.p1backend.mapper.DoctorMapper;
import com.example.p1backend.mapper.PatientMapper;
import com.example.p1backend.model.Doctor;
import com.example.p1backend.model.Patient;
import com.example.p1backend.repository.impl.DoctorRepositoryImpl;
import com.example.p1backend.repository.impl.PatientRepositoryImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@ApplicationScoped
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Inject)
public class UserService {
    private PatientRepositoryImpl patientRepository;
    private DoctorRepositoryImpl doctorRepository;
    private PatientMapper patientMapper;
    private DoctorMapper doctorMapper;

    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patientMapper::toDto)
                .toList();
    }

    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::toDto)
                .toList();
    }

    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findOptionalBy(id).orElse(null);

        if (patient != null) {
            return patientMapper.toDto(patient);
        }
        return null;
    }

    public PatientDTO getPatientByEmail(String email) {
        Patient patient = patientRepository.findOptionalByEmail(email).orElse(null);
        if (patient != null) {
            return patientMapper.toDto(patient);
        }
        return null;
    }

    public PatientDTO loginPatient(String email, String password) {
        Patient patient = patientRepository.findOptionalByEmail(email).orElse(null);
        if (patient != null && patient.getPassword().equals(password)) {
            return patientMapper.toDto(patient);
        }
        return null;
    }

    public DoctorDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findOptionalBy(id).orElse(null);
        if (doctor != null) {
            return doctorMapper.toDto(doctor);
        }
        return null;
    }

    public DoctorDTO getDoctorByEmail(String email) {
        Doctor doctor = doctorRepository.findOptionalByEmail(email).orElse(null);
        if (doctor != null) {
            return doctorMapper.toDto(doctor);
        }
        return null;
    }

    public DoctorDTO loginDoctor(String email, String password) {
        Doctor doctor = doctorRepository.findOptionalByEmail(email).orElse(null);
        if (doctor != null && doctor.getPassword().equals(password)) {
            return doctorMapper.toDto(doctor);
        }
        return null;
    }

    @Transactional
    public PatientDTO savePatient(PatientDTO patient, String password) {
        Patient savedPatient = patientMapper.toEntity(patient);
        savedPatient.setPassword(password);
        savedPatient = patientRepository.save(savedPatient);
        return patientMapper.toDto(savedPatient);
    }

    @Transactional
    public DoctorDTO saveDoctor(DoctorDTO doctor, String password) {
        Doctor savedDoctor = doctorMapper.toEntity(doctor);
        savedDoctor.setPassword(password);
        savedDoctor = doctorRepository.save(savedDoctor);
        return doctorMapper.toDto(savedDoctor);
    }

    @Transactional
    public PatientDTO updatePatient(PatientDTO patient) {
        if (patientRepository.findOptionalBy(patient.getPatientId()).isPresent()) {
            Patient updatedPatient = patientRepository.save(patientMapper.toEntity(patient));
            return patientMapper.toDto(updatedPatient);
        } else {
            throw new IllegalArgumentException("Patient with ID " + patient.getPatientId() + " does not exist.");
        }
    }

    @Transactional
    public DoctorDTO updateDoctor(DoctorDTO doctor) {
        if (doctorRepository.findOptionalBy(doctor.getDoctorId()).isPresent()) {
            Doctor updatedDoctor = doctorRepository.save(doctorMapper.toEntity(doctor));
            return doctorMapper.toDto(updatedDoctor);
        } else {
            throw new IllegalArgumentException("Doctor with ID " + doctor.getDoctorId() + " does not exist.");
        }
    }

    @Transactional
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findOptionalBy(id).orElse(null);
        if (patient != null) {
            patientRepository.remove(patient);
        } else {
            throw new IllegalArgumentException("Patient with ID " + id + " does not exist.");
        }
    }

    @Transactional
    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findOptionalBy(id).orElse(null);
        if (doctor != null) {
            doctorRepository.remove(doctor);
        } else {
            throw new IllegalArgumentException("Doctor with ID " + id + " does not exist.");
        }
    }
}
