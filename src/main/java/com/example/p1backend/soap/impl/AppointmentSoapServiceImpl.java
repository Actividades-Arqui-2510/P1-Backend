package com.example.p1backend.soap.impl;

import com.example.p1backend.dto.AppointmentDTO;
import com.example.p1backend.service.AppointmentService;
import com.example.p1backend.soap.AppointmentSoapService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.jws.WebService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@WebService(
        name = "AppointmentService",
        targetNamespace = "http://soap.p1backend.example.com/",
        endpointInterface = "com.example.p1backend.soap.AppointmentSoapService"
)
@ApplicationScoped
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSoapServiceImpl implements AppointmentSoapService {
    private AppointmentService appointmentService;

    @PostConstruct
    public void init() {
        if (appointmentService == null) {
            appointmentService = CDI.current().select(AppointmentService.class).get();
        }
    }

    @Override
    public AppointmentDTO getAppointmentDetails(Long appointmentId) {
        return appointmentService.getAppointmentById(appointmentId);
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentService.getAppointmentsByDoctorId(doctorId);
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByPatientId(Long patientId) {
        return appointmentService.getAppointmentsByPatientId(patientId);
    }

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDetails) {
        return appointmentService.saveAppointment(appointmentDetails);
    }

    @Override
    public AppointmentDTO updateAppointment(AppointmentDTO appointmentDetails) {
        return appointmentService.updateAppointment(appointmentDetails);
    }

    @Override
    public void deleteAppointment(Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
    }
}