package com.testtask.clinic.database.entity.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.testtask.clinic.database.entity.Patient;

public interface PatientRepository extends CrudRepository<Patient, Long> {

	Patient findByUuid(String uuid);

}
