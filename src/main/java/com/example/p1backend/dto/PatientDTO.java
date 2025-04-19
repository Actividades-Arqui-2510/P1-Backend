package com.example.p1backend.dto;

import com.example.p1backend.config.adapters.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PatientDTO implements Serializable {
    private Long patientId;
    private String firstName;
    private String lastName;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateOfBirth;

    private String email;
    private String phone;
}