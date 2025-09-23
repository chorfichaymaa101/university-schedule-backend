package com.ensak.emploi.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensak.emploi.model.SemesterTable;
import com.ensak.emploi.model.enums.ClassType;
import com.ensak.emploi.repository.SemesterTableRepository;

import lombok.Data;

import com.ensak.emploi.model.ExamTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensak.emploi.model.SemesterTable;
import com.ensak.emploi.model.enums.SemesterNumber;
import com.ensak.emploi.repository.SemesterTableRepository;

import lombok.Data;

@Data
@Service
public class SemesterTableService {

    @Autowired
    private SemesterTableRepository semesterTableRepository;

    public SemesterTable saveSemesterTable(SemesterTable semesterTable) {

        if (semesterTableRepository.existsByDateDebutAndDateFin(
                semesterTable.getDateDebut(),
                semesterTable.getDateFin())) {
            throw new IllegalArgumentException("Cette date est déjà choisi pour cette année.");
        }

        return semesterTableRepository.save(semesterTable);
    }

    public List<SemesterTable> getSemesterTableByYear(final String year) {
        return semesterTableRepository.existsByYear(year);
    }

    public List<SemesterTable> getSemesterTable(String year) {
        List<SemesterTable> semesterTables = semesterTableRepository.findAllByYear(year);
        if (semesterTables.isEmpty()) {
            System.out.println("No SemesterTables found for the year " + year);
        }
        return semesterTables;
    }

    public void updateTimeTable(SemesterTable updatedSemesterTable) {
        SemesterTable existingSemesterTable = null;
        try {
            Optional<SemesterTable> semesterTableOptional = semesterTableRepository.findById(updatedSemesterTable.getId());

//            if (!timeTableOptional.isPresent()) throw new RuntimeException();
            existingSemesterTable = semesterTableOptional.get();
            // Update fields for TimeTable
            existingSemesterTable.setDesignation(updatedSemesterTable.getDesignation());
            existingSemesterTable.setDateDebut(updatedSemesterTable.getDateDebut());
            existingSemesterTable.setDateFin(updatedSemesterTable.getDateFin());

            semesterTableRepository.save(existingSemesterTable);
        } catch (Exception e) {
            System.out.println("Exception thrown at updateTimeTable method: " + e);
        }
        //return existingSemesterTable.getId();
    }

    public SemesterTable getSemesterTableById(Long semesterId) {
        Optional<SemesterTable> semesterTableOptional = semesterTableRepository.findById(semesterId);
        SemesterTable semesterTable = null;
        if (semesterTableOptional.isPresent()) {
            semesterTable = semesterTableOptional.get();
        } else {
            System.out.println("SemesterTable with the id " + semesterId + " doesn't exist");
        }
        return semesterTable;
    }

    public List<SemesterTable> getAllSemesterTables() {
        return semesterTableRepository.findAll();
    }

    public List<SemesterTable> findAllSemesters() {
        return (List<SemesterTable>) semesterTableRepository.findAll();
    }

    public Optional<SemesterTable> findById(Long id) {
        return semesterTableRepository.findById(id);
    }

    public Long getSemesterNameById(Long semesterId) {
        return semesterTableRepository.findById(semesterId)
                .map(SemesterTable::getSemesterId)
                .orElse((long) -1);
    }
    /* 
    public Long getSemesterNameById(Long semesterId) {
        return semesterTableRepository.findById(semesterId)
                .map(SemesterTable::getSemesterId)
                .orElse((long) -1);
    }
     */

}
