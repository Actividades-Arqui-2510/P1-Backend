package com.example.p1backend.repository;

import com.example.p1backend.model.Appointment;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {
    List<Appointment> findAll();
    Optional<Appointment> findOptionalBy(Long id);
    Appointment save(Appointment appointment);
    void remove(Appointment appointment);
    List<Appointment> findByDoctorDoctorId(Long doctorId);
    List<Appointment> findByPatientPatientId(Long patientId);
}