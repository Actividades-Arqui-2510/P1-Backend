package com.example.p1backend.repository;

import com.example.p1backend.model.Patient;
import java.util.List;
import java.util.Optional;

public interface PatientRepository {
    List<Patient> findAll();
    Optional<Patient> findOptionalBy(Long id);
    Optional<Patient> findOptionalByEmail(String email);
    Patient save(Patient patient);
    void remove(Patient patient);
}