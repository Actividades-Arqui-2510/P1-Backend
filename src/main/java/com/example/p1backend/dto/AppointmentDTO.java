package com.example.p1backend.dto;

import com.example.p1backend.config.adapters.LocalDateAdapter;
import com.example.p1backend.config.adapters.LocalTimeAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate appointmentDate;

    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private LocalTime startTime;

    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private LocalTime endTime;
    private String status;
    private String notes;
}
