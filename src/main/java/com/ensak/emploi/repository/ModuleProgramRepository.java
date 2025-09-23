package com.ensak.emploi.repository;

import com.ensak.emploi.model.ModuleProgram;
import com.ensak.emploi.model.Program;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

@Repository
public interface ModuleProgramRepository extends JpaRepository<ModuleProgram, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM ModuleProgram mp WHERE mp.programId.id = :programId")
    void deleteByProgramId(@Param("programId") Long id);

    @Query("SELECT p.moduleName FROM ModuleProgram pp JOIN pp.moduleId p WHERE pp.programId = :programId")
    List<String> findModuleNamesByProgramId(@Param("programId") Program programId);
}
