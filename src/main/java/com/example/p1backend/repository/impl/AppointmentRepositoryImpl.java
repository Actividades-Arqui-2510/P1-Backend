package com.example.p1backend.repository.impl;

import com.example.p1backend.model.Appointment;
import com.example.p1backend.repository.AppointmentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AppointmentRepositoryImpl implements AppointmentRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Appointment> findAll() {
        return em.createQuery("SELECT a FROM Appointment a", Appointment.class)
                .getResultList();
    }

    @Override
    public Optional<Appointment> findOptionalBy(Long id) {
        return Optional.ofNullable(em.find(Appointment.class, id));
    }

    @Override
    public Appointment save(Appointment appointment) {
        if (appointment.getAppointmentId() == null) {
            em.persist(appointment);
            return appointment;
        } else {
            return em.merge(appointment);
        }
    }

    @Override
    public void remove(Appointment appointment) {
        if (em.contains(appointment)) {
            em.remove(appointment);
        } else {
            em.remove(em.merge(appointment));
        }
    }

    @Override
    public List<Appointment> findByDoctorDoctorId(Long doctorId) {
        return em.createQuery(
                        "SELECT a FROM Appointment a WHERE a.doctor.doctorId = :doctorId",
                        Appointment.class)
                .setParameter("doctorId", doctorId)
                .getResultList();
    }

    @Override
    public List<Appointment> findByPatientPatientId(Long patientId) {
        return em.createQuery(
                        "SELECT a FROM Appointment a WHERE a.patient.patientId = :patientId",
                        Appointment.class)
                .setParameter("patientId", patientId)
                .getResultList();
    }
}