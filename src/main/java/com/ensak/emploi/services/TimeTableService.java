package com.ensak.emploi.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensak.emploi.model.ExamTable;
import com.ensak.emploi.model.Program;
import com.ensak.emploi.model.SessionExam;
import com.ensak.emploi.model.TimeTable;
import com.ensak.emploi.model.enums.SemesterNumber;
import com.ensak.emploi.repository.TimeTableRepository;

import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class TimeTableService {

    @Autowired
    private TimeTableRepository timeTableRepository;

    public TimeTable savetimeTable(TimeTable timeTable) {
        if (timeTableRepository.existsTimeTableByProgramId(timeTable.getProgramId(), timeTable.getAcademicYear())) {
            throw new IllegalArgumentException("Cette filiére a déjà un emploi du temps.");
        }

        return timeTableRepository.save(timeTable);
    }

    public boolean timeTableExist(Long programId, String academicYear) {
        return timeTableRepository.existsTimeTableByProgramId(programId, academicYear);
    }

    public TimeTable getTimeTableByProgramYear(Long programid, String academicYear) {
        return timeTableRepository.findByProgramAndAcademicYear(programid, academicYear);
    }

    public Long updateTimeTable(TimeTable updatedTimeTable) {
        TimeTable existingTimeTable = null;
        try {
            Optional<TimeTable> timeTableOptional = timeTableRepository.findById(updatedTimeTable.getId());

//            if (!timeTableOptional.isPresent()) throw new RuntimeException();
            existingTimeTable = timeTableOptional.get();
            // Update fields for TimeTable
            existingTimeTable.setAcademicYear(updatedTimeTable.getAcademicYear());
            //existingTimeTable.setSemester(updatedTimeTable.getSemester());
            existingTimeTable.setProgramId(updatedTimeTable.getProgramId());
            timeTableRepository.save(existingTimeTable);
        } catch (Exception e) {
            System.out.println("Exception thrown at updateTimeTable method");
        }
        return existingTimeTable.getId();
    }

    public TimeTable getTimeTable(Long timeTableID) {
        Optional<TimeTable> timeTableOptional = null;
        try {
            timeTableOptional = timeTableRepository.findById(timeTableID);

//            if (!timeTableOptional.isPresent()) throw new RuntimeException();
        } catch (Exception e) {
            System.out.println("Exception thrown at getTimeTable method " + timeTableID);
        }
        return timeTableOptional.get();
    }

    public List<TimeTable> getTimeTableByAcademicYear(String academicYear) {
        return timeTableRepository.findByAcademicYear(academicYear);
    }

    public List<TimeTable> getAllTimeTables() {
        return timeTableRepository.findAll();
    }

    public List<TimeTable> getFilteredTimeTables(Long programId, SemesterNumber semester) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFilteredTimeTables'");
    }
    public List<TimeTable> getAllTimeTable() {
        return timeTableRepository.findAll();
    }

}
