package com.ensak.emploi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ensak.emploi.model.TimeTable;
import com.ensak.emploi.model.enums.SemesterNumber;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {

    @Query("SELECT COUNT(t) > 0 FROM TimeTable t WHERE t.programId.id = :programId AND t.academicYear = :academicYear")
    boolean existsTimeTableByProgramId(@Param("programId") Long programId, @Param("academicYear") String academicYear);

    @Query("SELECT t FROM TimeTable t WHERE t.programId.id = :programId AND t.academicYear = :academicYear")
    TimeTable findByProgramAndAcademicYear(@Param("programId") Long programId, @Param("academicYear") String academicYear);
    List<TimeTable> findByProgramId(Long programId);
    List<TimeTable> findByAcademicYear(String academicYear);

    @Query("SELECT t FROM TimeTable t WHERE t.programId.id = :programId and t.academicYear = :academicYear")
    TimeTable findTimeTableByProgramIdAndAcademicYear(@Param("programId") Long programId, @Param("academicYear") String academicYear);

    Boolean existsTimeTableByProgramId_IdAndAcademicYear(Long programId, String academicYear);

}