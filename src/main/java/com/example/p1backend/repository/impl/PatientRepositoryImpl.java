package com.example.p1backend.repository.impl;

import com.example.p1backend.model.Patient;
import com.example.p1backend.repository.PatientRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PatientRepositoryImpl implements PatientRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Patient> findAll() {
        return em.createQuery("SELECT p FROM Patient p", Patient.class)
                .getResultList();
    }

    @Override
    public Optional<Patient> findOptionalBy(Long id) {
        return Optional.ofNullable(em.find(Patient.class, id));
    }

    @Override
    public Optional<Patient> findOptionalByEmail(String email) {
        return Optional.ofNullable(
                em.createQuery("SELECT p FROM Patient p WHERE p.email = :email", Patient.class)
                        .setParameter("email", email)
                        .getSingleResult()
        );
    }

    @Override
    public Patient save(Patient patient) {
        if (patient.getPatientId() == null) {
            em.persist(patient);
            return patient;
        } else {
            return em.merge(patient);
        }
    }

    @Override
    public void remove(Patient patient) {
        if (em.contains(patient)) {
            em.remove(patient);
        } else {
            em.remove(em.merge(patient));
        }
    }
}