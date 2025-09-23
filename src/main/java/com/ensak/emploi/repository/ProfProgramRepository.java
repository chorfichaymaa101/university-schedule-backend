package com.ensak.emploi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ensak.emploi.model.CreateProfessorRequest;
import com.ensak.emploi.model.ProfProgram;
import com.ensak.emploi.model.Professor;
import com.ensak.emploi.model.Program;
import org.springframework.data.domain.Pageable;

import jakarta.transaction.Transactional;

@Repository
public interface ProfProgramRepository extends JpaRepository<ProfProgram, Long> {
    // @Query("SELECT p.programId FROM ProfProgram p WHERE p.professorId.id =
    // :professorId")
    // List<Program> findProgramByProfessorId(Long professorId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ProfProgram pp WHERE pp.professorId.id = :professorId")
    void deleteByProfessorId(@Param("professorId") Long id);

    ProfProgram findProfProgramById(Long id);

    @Query("SELECT p.programName FROM ProfProgram pp JOIN pp.programId p WHERE pp.professorId = :professorId")
    List<String> findProgramNamesByProfessorId(@Param("professorId") Professor professorId);

}
