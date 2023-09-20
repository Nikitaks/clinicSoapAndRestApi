package com.testtask.clinic.controller;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.testtask.clinic.database.entity.Patient;
import com.testtask.clinic.database.entity.Timeslot;
import com.testtask.clinic.database.entity.repo.DoctorRepository;
import com.testtask.clinic.database.entity.repo.PatientRepository;
import com.testtask.clinic.database.entity.repo.TimeslotRepository;
import com.testtask.clinic.dto.FreeSlotDoctorDateResponse;
import com.testtask.clinic.dto.OcupiedSlotPatientResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class ClinicController {

	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private TimeslotRepository timeslotRepository;
	
	@Operation(summary = "It shows free slots with doctorId on date specified on dateString")
	@ApiResponses(value = {
    	@ApiResponse(
        	responseCode = "200 or 400",
        	description = "Free slots list or error message")
    }) 
	@GetMapping("/slot/get/free")
	public List<FreeSlotDoctorDateResponse> slotGetFree(@RequestParam long doctorId, @RequestParam String dateString) {
		
		String messageIfWrongInputData = checkInputData(doctorId, dateString);
		if (messageIfWrongInputData != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageIfWrongInputData); 
		}
		LocalDate date = LocalDate.parse(dateString);
		return timeslotRepository
				.findAllByDoctorIdAndVisitdateAndPatientIdIsNull(doctorId, date).stream()
				.map(FreeSlotDoctorDateResponse::new)
				.collect(Collectors.toList());
	}
	
	@Operation(summary = "It shows occupied slots by patient with patientId")
	@ApiResponses(value = {
    	@ApiResponse(
        	responseCode = "200",
        	description = "List of ocupied time slots")
    }) 
	@GetMapping("/slot/get/occupied/id")
	public List<OcupiedSlotPatientResponse> slotGetOcupied(@RequestParam Long patientId) {
		return timeslotRepository.findAllByPatientId(patientId).stream()
			.map(OcupiedSlotPatientResponse::new)
			.collect(Collectors.toList());
	}
	
	@Operation(summary = "It shows occupied slots by patient with uuid")
	@ApiResponses(value = {
    	@ApiResponse(
        	responseCode = "200",
        	description = "List of ocupied time slots")
    }) 
	@GetMapping("/slot/get/occupied/uuid")
	public List<OcupiedSlotPatientResponse> slotGetOcupied(@RequestParam String uuid) {
		Patient patient =  patientRepository.findByUuid(uuid);
		return (patient != null) ? 
			slotGetOcupied((patient.getId())) : 
			Collections.emptyList();
	}
	
	@Operation(summary = "It occupy slot with slotId by patient with patientId")
	@ApiResponses(value = {
    	@ApiResponse(
        	responseCode = "200 or 400",
        	description = "Time slot details or error message")
    }) 
	@PostMapping("/slot/occupy")
	public Timeslot slotOccupyPatientId(@RequestParam long slotId, @RequestParam Long patientId) {
		Optional<Timeslot> optionalTimeslot =  timeslotRepository.findById(slotId);
		Optional<Patient> optionalPatient =  patientRepository.findById(patientId);
		if (optionalTimeslot.isPresent() && optionalPatient.isPresent()) {
			optionalTimeslot.get().setPatientId(patientId);
			timeslotRepository.save(optionalTimeslot.get());
			return optionalTimeslot.get();
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "TimeSlot or patientId are not exist"); 
	}

	private String checkInputData(long doctorId, String dateString) {
				
		if (! doctorRepository.existsById(doctorId)) {
			return "Doctor_id not exists";
		}
		if (dateString == null) {
			return "Date must not be null";
		}
		return null;
	}
		
}
