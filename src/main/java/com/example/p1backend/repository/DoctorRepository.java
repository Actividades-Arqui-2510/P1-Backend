package com.example.p1backend.repository;

import com.example.p1backend.model.Doctor;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository {
    List<Doctor> findAll();
    Optional<Doctor> findOptionalBy(Long id);
    Optional<Doctor> findOptionalByEmail(String email);
    Doctor save(Doctor doctor);
    void remove(Doctor doctor);
}