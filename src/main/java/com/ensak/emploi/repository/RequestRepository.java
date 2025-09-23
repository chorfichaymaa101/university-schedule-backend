package com.ensak.emploi.repository;

import com.ensak.emploi.model.Request;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository  // Optional but recommended
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("SELECT r FROM Request r WHERE r.programId = :programId")
    List<Request> findRequestsByProgramId(@Param("programId") Long programId);
    
    
    @Query("SELECT r FROM Request r WHERE r.program.name = :programName AND r.semester.id = :semesterId")
    List<Request> findByProgramNameAndSemesterId(@Param("programName") String programName, @Param("semesterId") Long semesterId);


}
