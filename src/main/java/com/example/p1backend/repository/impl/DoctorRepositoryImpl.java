package com.example.p1backend.repository.impl;

import com.example.p1backend.model.Doctor;
import com.example.p1backend.repository.DoctorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class DoctorRepositoryImpl implements DoctorRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Doctor> findAll() {
        return em.createQuery("SELECT d FROM Doctor d", Doctor.class)
                .getResultList();
    }

    @Override
    public Optional<Doctor> findOptionalBy(Long id) {
        return Optional.ofNullable(em.find(Doctor.class, id));
    }

    @Override
    public Optional<Doctor> findOptionalByEmail(String email) {
        return Optional.ofNullable(
                em.createQuery("SELECT d FROM Doctor d WHERE d.email = :email", Doctor.class)
                        .setParameter("email", email)
                        .getSingleResult()
        );
    }

    @Override
    public Doctor save(Doctor doctor) {
        if (doctor.getDoctorId() == null) {
            em.persist(doctor);
            return doctor;
        } else {
            return em.merge(doctor);
        }
    }

    @Override
    public void remove(Doctor doctor) {
        if (em.contains(doctor)) {
            em.remove(doctor);
        } else {
            em.remove(em.merge(doctor));
        }
    }
}