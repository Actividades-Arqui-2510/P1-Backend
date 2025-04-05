package com.example.p1backend.mapper;

import com.example.p1backend.dto.DoctorDTO;
import com.example.p1backend.model.Doctor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@ApplicationScoped
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Inject)
public class DoctorMapper {

    private ModelMapper modelMapper;

    public DoctorDTO toDto(Doctor doctor) {
        return modelMapper.map(doctor, DoctorDTO.class);
    }

    public Doctor toEntity(DoctorDTO doctorDTO) {
        return modelMapper.map(doctorDTO, Doctor.class);
    }
}
