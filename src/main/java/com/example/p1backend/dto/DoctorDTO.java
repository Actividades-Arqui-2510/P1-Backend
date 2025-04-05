package com.example.p1backend.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DoctorDTO {
    private Long doctorId;
    private String firstName;
    private String lastName;
    private String specialty;
    private String email;
    private String phone;
}
