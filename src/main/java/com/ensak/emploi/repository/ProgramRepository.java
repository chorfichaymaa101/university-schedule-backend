package com.ensak.emploi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ensak.emploi.model.Program;
import com.ensak.emploi.model.enums.SemesterNumber;
import java.util.Optional;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {

    @Query("SELECT p FROM Program p WHERE p.semester = :semester")
    List<Program> existsBySemester(@Param("semester") SemesterNumber semester);

    @Query("SELECT p FROM Program p WHERE p.id = :id")
    Program findProgramById(@Param("id") Long id);

    @Query("SELECT p FROM Program p")
    List<Program> findAllPrograms();

    @Query("SELECT p FROM Program p WHERE p.programName IN :programName")
    List<Program> findAllByTitle(List<String> programName);

    @Query("SELECT p FROM Program p WHERE p.programName =:programName AND p.semester =:semester")
    Program findByTitle(String programName, SemesterNumber semester);
    // Program findProgramById(Long programId);

    @Query("SELECT DISTINCT p FROM Program p GROUP BY p.programName")
    List<Program> findDistinctPrograms();


}
