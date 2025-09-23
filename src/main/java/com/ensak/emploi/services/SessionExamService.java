package com.ensak.emploi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ensak.emploi.model.Person;
import com.ensak.emploi.model.Program;
import com.ensak.emploi.model.Module;
import com.ensak.emploi.repository.ModuleRepository;
import com.ensak.emploi.repository.PersonRepository;
import com.ensak.emploi.repository.ProgramRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import com.ensak.emploi.model.SessionExam;
import com.ensak.emploi.repository.SessionExamRepository;

@Service
public class SessionExamService {

    @Autowired
    private SessionExamRepository sessionExamRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    public SessionExam saveSessionExam(SessionExam sessionExam) {
        if (sessionExamRepository.existsByDayAndExamPeriodDebutAndClassId(
                sessionExam.getDay(),
                sessionExam.getExamPeriodDebut(),
                sessionExam.getExamPeriodFin(),
                sessionExam.getClassId())) {
            throw new IllegalArgumentException("Cette classe est déjà utilisée pour cette période et ce jour.");
        }
        if (sessionExamRepository.existsByDayAndExamPeriodFinAndClassId(
                sessionExam.getDay(),
                sessionExam.getExamPeriodDebut(),
                sessionExam.getExamPeriodFin(),
                sessionExam.getClassId())) {
            throw new IllegalArgumentException("Cette classe est déjà utilisée pour cette période et ce jour.");
        }

        if (sessionExamRepository.existsByProgramAndModule(
                sessionExam.getProgramId(),
                sessionExam.getModuleId(),
                sessionExam.getDay())) {
            throw new IllegalArgumentException("Cet examen pour cette filière et ce module éxiste déjà.");
        }
        if (sessionExamRepository.existsByProgramAndModuleByExamCalender(
                sessionExam.getModuleId(),
                sessionExam.getProgramId(),
                sessionExam.getExamCalendarId())) {
            throw new IllegalArgumentException(
                    "Cet examen pour cette filière et ce module éxiste déjà dans cet emploi d'examen.");
        }
        return sessionExamRepository.save(sessionExam);
    }

    public List<SessionExam> getSessionExamByExamCalendar(final Long examCalendar) {
        return sessionExamRepository.existsByExamCalendarId(examCalendar);
    }

    public List<SessionExam> getSessionExamsByExamTableId(Long examCalendarId) {
        try {
            return sessionExamRepository.findByExamCalendarId_Id(examCalendarId);
        } catch (JpaSystemException e) {
            System.err.println("Error while retrieving session exams: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list in case of error
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public SessionExam getSessionExamBySessionExamIdAndExamTableId(Long sessionExamId, Long examCalendarId) {
        try {
            SessionExam session = sessionExamRepository.findSessionExamBySessionExamIdAndExamId(sessionExamId,
                    examCalendarId);
            return session;
        } catch (Exception e) {
            System.out.println("No sessionExam found for sessionExamId: " + sessionExamId + " and examCalendarId: "
                    + examCalendarId);
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void updateSessionExam(SessionExam updatedSessionExam) {
        if (updatedSessionExam == null || updatedSessionExam.getId() == null) {
            throw new IllegalArgumentException("SessionExam or ID cannot be null");
        }

        Optional<SessionExam> sessionExamOptional = sessionExamRepository.findById(updatedSessionExam.getId());
        if (!sessionExamOptional.isPresent()) {
            throw new EntityNotFoundException("SessionExam not found with id: " + updatedSessionExam.getId());
        }

        SessionExam existingSessionExam = sessionExamOptional.get();

        // Update all fields
        if (updatedSessionExam.getProfessor() != null) {
            existingSessionExam.setProfessor(updatedSessionExam.getProfessor());
        } else if (updatedSessionExam.getProfessorId() != null) {
            existingSessionExam.setProfessorId(updatedSessionExam.getProfessorId());
        }

        if (updatedSessionExam.getProgramId() != null) {
            existingSessionExam.setProgramId(updatedSessionExam.getProgramId());
        }

        if (updatedSessionExam.getModuleId() != null) {
            existingSessionExam.setModuleId(updatedSessionExam.getModuleId());
        }

        if (updatedSessionExam.getClassId() != null) {
            existingSessionExam.setClassId(updatedSessionExam.getClassId());
        }

        if (updatedSessionExam.getExamCalendarId() != null) {
            existingSessionExam.setExamCalendarId(updatedSessionExam.getExamCalendarId());
        }

        if (updatedSessionExam.getExamPeriodDebut() != null) {
            existingSessionExam.setExamPeriodDebut(updatedSessionExam.getExamPeriodDebut());
        }

        if (updatedSessionExam.getExamPeriodFin() != null) {
            existingSessionExam.setExamPeriodFin(updatedSessionExam.getExamPeriodFin());
        }

        if (updatedSessionExam.getDay() != null) {
            existingSessionExam.setDay(updatedSessionExam.getDay());
        }

        if (updatedSessionExam.getCapacity() != null) {
            existingSessionExam.setCapacity(updatedSessionExam.getCapacity());
        }

        try {
            sessionExamRepository.save(existingSessionExam);
        } catch (Exception e) {
            throw new RuntimeException("Error updating SessionExam: " + e.getMessage(), e);
        }
    }
}
