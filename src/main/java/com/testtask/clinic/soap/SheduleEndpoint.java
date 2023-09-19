package com.testtask.clinic.soap;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.GregorianCalendar;
import java.util.stream.Stream;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.testtask.clinic.database.entity.Timeslot;
import com.testtask.clinic.database.entity.repo.DoctorRepository;
import com.testtask.clinic.database.entity.repo.TimeslotRepository;
import com.testtask.clinic.soap.gen.MakeSheduleRequest;
import com.testtask.clinic.soap.gen.SheduleResponce;


@Endpoint
public class SheduleEndpoint {

    private static final String NAMESPACE_URI = "http://www.testtask.com/clinic/soap/gen";

    @Autowired
	private DoctorRepository doctorRepository;
    
    @Autowired
	private TimeslotRepository timeslotRepository;
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "makeSheduleRequest")
    @ResponsePayload
    public SheduleResponce getCountry(@RequestPayload MakeSheduleRequest sheduleReauest) {
    	SheduleResponce response = new SheduleResponce();

    	String messageIfWrongInputData = checkInputData(sheduleReauest);
		if (messageIfWrongInputData != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageIfWrongInputData);
		}
		
		computeIntervalMinutesAndIntervalNumber(sheduleReauest);
			
		Stream
			.iterate(sheduleReauest.getStartTime().toGregorianCalendar().toZonedDateTime().toLocalTime(), 
				time -> formNextTimeAndAddToResponce(time, sheduleReauest, response))
			.limit(sheduleReauest.getTimeSlotsNumber())
			.map(startTime -> new Timeslot(0, sheduleReauest.getDoctorId(), 
								null, startTime, sheduleReauest.getDate().toGregorianCalendar().toZonedDateTime().toLocalDate()))
			.forEach(timeSlot -> timeslotRepository.save(timeSlot));
		  	
        return response;
    }
    
    private LocalTime formNextTimeAndAddToResponce(LocalTime time, MakeSheduleRequest sheduleReauest, SheduleResponce response) {
    	LocalTime startTime = time.plusMinutes(sheduleReauest.getIntervalTimeSlotMinutes());
		GregorianCalendar calendarStartTime = new GregorianCalendar(0, 0, 0, 
				startTime.getHour(), startTime.getMinute(), startTime.getSecond());
		try {
			response.getTimeSlot().add(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendarStartTime));
		} catch (DatatypeConfigurationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "DateTime conversion problem");
		}
		return startTime;
	}

	private String checkInputData(MakeSheduleRequest sheduleReauest) {
		if (! doctorRepository.existsById(sheduleReauest.getDoctorId())) {
			return "Doctor_id not exists";
		}
		if ( sheduleReauest.getDate() == null) {
			return "Date must not be null";
		}
		if (sheduleReauest.getStartTime().compare(sheduleReauest.getFinishTime()) == DatatypeConstants.GREATER) {
			return "StartTime must not be after finishTime";
		}
		if ((sheduleReauest.getIntervalTimeSlotMinutes() == 0) 
			&& (sheduleReauest.getTimeSlotsNumber() == 0)) {
			return "Can't create shedule cause IntervalTimeSlotMinutes and TimeSlotsNumber mustn't equal zero both";
		}
		if ((sheduleReauest.getIntervalTimeSlotMinutes() != 0) 
				&& (sheduleReauest.getTimeSlotsNumber() != 0)) {
			return "Can't create shedule cause IntervalTimeSlotMinutes and TimeSlotsNumber mustn't equal non zero both";
		}
		return null;
	}	
    
    private void computeIntervalMinutesAndIntervalNumber(MakeSheduleRequest sheduleReauest) {
    	LocalDateTime startTime = sheduleReauest.getStartTime().toGregorianCalendar().toZonedDateTime().toLocalDateTime();
		LocalDateTime finishTime = sheduleReauest.getFinishTime().toGregorianCalendar().toZonedDateTime().toLocalDateTime();
    	if (sheduleReauest.getIntervalTimeSlotMinutes() == 0) {
			long intervalTimeSlotMinutes = startTime.until(finishTime, ChronoUnit.MINUTES) / 
											sheduleReauest.getTimeSlotsNumber();
			sheduleReauest.setIntervalTimeSlotMinutes((int)intervalTimeSlotMinutes);
		}
		else {
			long timeSlotsNumber = startTime.until(finishTime, ChronoUnit.MINUTES) / 
									sheduleReauest.getIntervalTimeSlotMinutes();
			sheduleReauest.setTimeSlotsNumber((int)timeSlotsNumber);
		}
	}
}