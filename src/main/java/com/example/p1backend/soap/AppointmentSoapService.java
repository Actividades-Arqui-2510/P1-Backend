package com.example.p1backend.soap;

import com.example.p1backend.dto.AppointmentDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;

import java.util.List;

@WebService(name = "AppointmentService", targetNamespace = "http://soap.p1backend.example.com/")
public interface AppointmentSoapService {
    @WebMethod
    @WebResult(name = "appointment")
    AppointmentDTO getAppointmentDetails(@WebParam(name = "appointmentId") Long appointmentId);

    @WebMethod
    @WebResult(name = "appointments")
    List<AppointmentDTO> getAllAppointments();

    @WebMethod
    @WebResult(name = "appointments")
    List<AppointmentDTO> getAppointmentsByDoctorId(@WebParam(name = "doctorId") Long doctorId);

    @WebMethod
    @WebResult(name = "appointments")
    List<AppointmentDTO> getAppointmentsByPatientId(@WebParam(name = "patientId") Long patientId);

    @WebMethod
    @WebResult(name = "appointment")
    AppointmentDTO createAppointment(@WebParam(name = "appointment") AppointmentDTO appointmentDetails);

    @WebMethod
    @WebResult(name = "appointment")
    AppointmentDTO updateAppointment(@WebParam(name = "appointment") AppointmentDTO appointmentDetails);

    @WebMethod
    @WebResult(name = "appointment")
    void deleteAppointment(@WebParam(name = "appointmentId") Long appointmentId);

}
