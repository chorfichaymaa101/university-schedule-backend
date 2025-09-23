package com.ensak.emploi.services;

import java.util.List;
import java.util.Optional;

import com.ensak.emploi.model.SemesterTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensak.emploi.model.Program;
import com.ensak.emploi.model.Session;
import com.ensak.emploi.model.TimeTable;
import com.ensak.emploi.model.enums.Day;
import com.ensak.emploi.model.enums.Period;
import com.ensak.emploi.repository.ProgramRepository;
import com.ensak.emploi.repository.SessionRepository;
import com.ensak.emploi.repository.TimeTableRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TimeTableRepository timeTableRepository;

    @Autowired
    private ProgramRepository programRepository;

    public Session saveSession(Session session) {

        TimeTable timeTable = timeTableRepository.findById(session.getTimetableId())
                .orElseThrow(() -> new EntityNotFoundException("TimeTable not found with ID: " + session.getTimetableId()));

        Program program = programRepository.findById(timeTable.getProgramId())
                .orElseThrow(() -> new EntityNotFoundException("TimeTable not found with ID: " + timeTable.getProgramId()));

        if (sessionRepository.existsByModuleIdBySessiontype(
                session.getModuleId(),
                session.getSessionType(),
                timeTable.getAcademicYear())) {
            throw new IllegalArgumentException("Une séance pour ce module et cette année existe déjà.");
        }
        if (sessionRepository.existsByClassAndProgramAndAcademicYearAndDayAndTime(
                session.getClassId(),
                program.getSemester(),
                timeTable.getAcademicYear(),
                session.getDay(),
                session.getTime())) {
            throw new IllegalArgumentException("La classe est déjà assignée pour ce semestre, cette année académique, ce jour et cette période.");
        }

        return sessionRepository.save(session);
    }

    public Session getSessionByTimeTableByDayByTime(Long timeTable, Day day, Period time) {
        return sessionRepository.existsByTimeTableIdByDayByTime(timeTable, day, time);
    }

    public List<Session> getSessionsByTimeTableId(Long timeTableId) {
        try {
            return sessionRepository.findByTimetableId_Id(timeTableId);
            //return sessionRepository.findByTimeTable_Timetable_id(timeTableId);
        } catch (Exception e) {
            System.err.println("Exception thrown at getSessionsByTimeTableId method for timeTableId: " + timeTableId);
            e.printStackTrace();
            throw new RuntimeException("Error retrieving sessions for timetableId: " + timeTableId, e);
        }
    }

//    public Optional<Session> getSessionBySessionIdAndTimeTableId(Long sessionId, Long timeTableId) {
//        Optional<Session> sessionOptional = sessionRepository.findBySessionIdAndTimetableId(sessionId, timeTableId);
//        if (sessionOptional.isPresent()) {
//            return sessionOptional;
//        } else {
//            // Handle the case where no session is found
//            System.out.println("No session found for sessionId: " + sessionId + " and timetableId: " + timeTableId);
//            return Optional.empty();
//        }
//    }
    public Session getSessionBySessionIdAndTimeTableId(Long sessionId, Long timeTableId) {
        try {
            Session session = sessionRepository.findBySessionIdAndTimetableId(sessionId, timeTableId);
            return session;
        } catch (Exception e) {
            System.out.println("No session found for sessionId: " + sessionId + " and timetableId: " + timeTableId);
            throw new RuntimeException(e);
        }
    }

    public void updateSession(Session updatedSession) {
        Session existingSession = null;
        try {
            Optional<Session> sessionOptional = sessionRepository.findById(updatedSession.getId());
            if (sessionOptional.isEmpty()) throw new RuntimeException();
            existingSession = sessionOptional.get();
//
            // Update fields for Session
            existingSession.setProfessorId(updatedSession.getProfessorId());
            existingSession.setModuleId(updatedSession.getModuleId());
            existingSession.setDay(updatedSession.getDay());
            existingSession.setTime(updatedSession.getTime());
            existingSession.setSessionType(updatedSession.getSessionType());
            existingSession.setGroupe(updatedSession.getGroupe());
            existingSession.setCapacity(updatedSession.getCapacity());
            existingSession.setClassId(updatedSession.getClassId());

            sessionRepository.save(existingSession);
        } catch (Exception e) {
            System.out.println("Exception thrown at updateSession method" + e);
        }
//        assert existingSession != null;
//        return existingSession.getId();
    }

    public void deleteSession(Session updatedSession) {
        try {
            Optional<Session> SessionOptional = sessionRepository.findById(updatedSession.getId());
            if (SessionOptional.isPresent()) {
                sessionRepository.delete(updatedSession);
            }
        } catch (Exception e) {
            System.out.println("Exception thrown at deleteSession method");
        }
    }
}
