package com.testtask.clinic.database.entity.repo;

import org.springframework.data.repository.CrudRepository;

import com.testtask.clinic.database.entity.Doctor;

public interface DoctorRepository extends CrudRepository<Doctor, Long> {

}
