package com.ensak.emploi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensak.emploi.model.ExamTable;
import com.ensak.emploi.model.enums.SemesterNumber;
import com.ensak.emploi.repository.ExamTableRepository;
import com.ensak.emploi.model.enums.SemesterNumber;
import lombok.Data;

import java.util.Optional;

@Data
@Service
public class ExamTableService {

    @Autowired
    private ExamTableRepository examTableRepository;

    public ExamTable savetimeTable(ExamTable examTable) {
        ExamTable savedExamTable = examTableRepository.save(examTable);
        return savedExamTable;
    }

    public ExamTable getExamTableBySemseterYear(SemesterNumber semester, String academicYear) {
        return examTableRepository.findBySemesterAndAcademicYear(semester, academicYear);
    }

    public boolean examExist(SemesterNumber semester, String academicYear) {
        return examTableRepository.existsBySemesterAndAcademicYear(semester, academicYear);
    }

    public ExamTable getExamTable(String academicYear, SemesterNumber semester) {
        Optional<ExamTable> examTableOptional = examTableRepository.findByAcademicYearAndSemester(academicYear, semester);
        ExamTable examTable = null;
        if (examTableOptional.isPresent()) {
            examTable = examTableOptional.get();
        } else {
            System.out.println("ExamTable with the id " + academicYear + " " + semester + " doesn't exist");
        }
        return examTable;
    }

    public Long updateExamTable(ExamTable updatedExamTable) {
        ExamTable existingExamTable = null;
        try {
            Optional<ExamTable> examTableOptional = examTableRepository.findById(updatedExamTable.getId());

//            if (!timeTableOptional.isPresent()) throw new RuntimeException();
            existingExamTable = examTableOptional.get();
            // Update fields for TimeTable
            existingExamTable.setSemester(updatedExamTable.getSemester());
            existingExamTable.setAcademicYear(updatedExamTable.getAcademicYear());
            examTableRepository.save(existingExamTable);
        } catch (Exception e) {
            System.out.println("Exception thrown at updateTimeTable method");
        }
        return existingExamTable.getId();
    }

    public ExamTable getExamTableById(Long examId) {
        Optional<ExamTable> examTableOptional = examTableRepository.findById(examId);
        ExamTable examTable = null;
        if (examTableOptional.isPresent()) {
            examTable = examTableOptional.get();
        } else {
            System.out.println("ExamTable with the id " + examId + " doesn't exist");
        }
        return examTable;
    }

    public Long getExamTableId(String academicYear, SemesterNumber semester) {
        Optional<ExamTable> examTableOptional = examTableRepository.findByAcademicYearAndSemester(academicYear, semester);
        Long examTableId = null;
        if (examTableOptional.isPresent()) {
            examTableId = examTableOptional.get().getId();
        } else {
            System.out.println("ExamTable with the id " + academicYear + " " + semester + " doesn't exist");
        }
        return examTableId;
    }


    public List<ExamTable> getExamTableBySemester(SemesterNumber semester) {
        return examTableRepository.findBySemester(semester);
    }
    

    public List<ExamTable> findExamTables(String academicYear, SemesterNumber semester, Long id) {
        return examTableRepository.findByFilters(academicYear, semester, id);
    }
    public List<ExamTable> getAllExamTable() {
        return examTableRepository.findAll();
    }
    
}
