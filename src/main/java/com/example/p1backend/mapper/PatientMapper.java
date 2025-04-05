package com.example.p1backend.mapper;

import com.example.p1backend.dto.PatientDTO;
import com.example.p1backend.model.Patient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@ApplicationScoped
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Inject)
public class PatientMapper {
    private ModelMapper modelMapper;

    public PatientDTO toDto(Patient patient) {
        return modelMapper.map(patient, PatientDTO.class);
    }

    public Patient toEntity(PatientDTO patientDTO) {
        return modelMapper.map(patientDTO, Patient.class);
    }
}
