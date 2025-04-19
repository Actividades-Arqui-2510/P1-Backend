package com.example.p1backend.service;

import com.example.p1backend.dto.AppointmentDTO;
import com.example.p1backend.mapper.AppointmentMapper;
import com.example.p1backend.model.Appointment;
import com.example.p1backend.repository.impl.AppointmentRepositoryImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Inject)
public class AppointmentService {
    private AppointmentRepositoryImpl appointmentRepository;
    private AppointmentMapper appointmentMapper;

    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    public AppointmentDTO getAppointmentById(Long id) {
        Optional<Appointment> appointment = appointmentRepository.findOptionalBy(id);

        // Check if appointment is present and map it to DTO
        return appointment.map(value -> appointmentMapper.toDto(value)).orElse(null);
    }

    public List<AppointmentDTO> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctorDoctorId(doctorId)
                .stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    public List<AppointmentDTO> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientPatientId(patientId)
                .stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    @Transactional
    public AppointmentDTO saveAppointment(AppointmentDTO appointment) {
        Appointment savedAppointment = appointmentMapper.toEntity(appointment);
        savedAppointment = appointmentRepository.save(savedAppointment);
        return appointmentMapper.toDto(savedAppointment);
    }

    @Transactional
    public AppointmentDTO updateAppointment(AppointmentDTO appointment) {
        if(appointmentRepository.findOptionalBy(appointment.getAppointmentId()).isPresent()) {
            Appointment updatedAppointment = appointmentRepository.save(appointmentMapper.toEntity(appointment));
            return appointmentMapper.toDto(updatedAppointment);
        } else {
            throw new IllegalArgumentException("Appointment with ID " + appointment.getAppointmentId() + " does not exist.");
        }
    }

    @Transactional
    public void deleteAppointment(Long id) {
        Optional<Appointment> appointment = appointmentRepository.findOptionalBy(id);
        if (appointment.isPresent()) {
            appointmentRepository.remove(appointment.get());
        } else {
            throw new IllegalArgumentException("Appointment with ID " + id + " does not exist.");
        }
    }

}
