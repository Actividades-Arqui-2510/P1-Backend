package com.example.p1backend.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AppointmentDTO {
    private Long appointmentId;
    private DoctorDTO doctor;
    private PatientDTO patient;
    private LocalDate appointmentDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;
    private String notes;
}
