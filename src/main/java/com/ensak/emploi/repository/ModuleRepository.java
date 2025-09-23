package com.ensak.emploi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import com.ensak.emploi.model.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    @Query("SELECT m FROM Module m JOIN ModuleProgram mp ON m.id = mp.moduleId WHERE mp.programId.id = :programId")
    List<Module> findModuleByProgram(@Param("programId") Long programId);

    @Query("SELECT m FROM Module m WHERE m.id = :id")
    Module findModuleById(@Param("id") Long id);

    // Optional<Module> findModuleNameById(Long moduleId); // Corrected method
    // signature
    Module findModuleNameById(Long moduleId); // Corrected method signature

    // Optional<Module> findModuleById(Long id);
    @Query("SELECT m.id FROM Module m WHERE m.moduleName = :name")
    Long findModuleIdByName(String name);

    @Query("SELECT m FROM Module m WHERE m.moduleName IN :moduleName")
    List<Module> findAllByTitle(List<String> moduleName);
}
