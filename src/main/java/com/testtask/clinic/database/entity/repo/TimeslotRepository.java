package com.testtask.clinic.database.entity.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.testtask.clinic.database.entity.Timeslot;

public interface TimeslotRepository extends CrudRepository<Timeslot, Long> {

	List<Timeslot> findAllByDoctorIdAndVisitdateAndPatientIdIsNull(long doctorId, LocalDate visitdate);

	List<Timeslot> findAllByPatientId(Long patientId);

	//List<Timeslot> findAllByUuid(UUID fromString);

}
