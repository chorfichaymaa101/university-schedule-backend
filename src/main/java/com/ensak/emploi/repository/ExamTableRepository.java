package com.ensak.emploi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ensak.emploi.model.ExamTable;
import com.ensak.emploi.model.enums.SemesterNumber;
import java.util.Optional;

@Repository
public interface ExamTableRepository extends JpaRepository<ExamTable, Long> {
    List<ExamTable> findAll();
    List<ExamTable> findBySemester(SemesterNumber semester);
    ExamTable findById(long id);

    @Query("SELECT e FROM ExamTable e WHERE " +
       "(COALESCE(:academicYear, e.academicYear) = e.academicYear) AND " +
       "(COALESCE(:semester, e.semester) = e.semester) AND " +
       "(COALESCE(:id, e.id) = e.id)")

    List<ExamTable> findByFilters(
            @Param("academicYear") String academicYear,
            @Param("semester") SemesterNumber semester,
            @Param("id") Long id
    );

    @Query("SELECT COUNT(e) > 0 FROM ExamTable e WHERE e.semester = :semester AND e.academicYear = :academicYear")
    boolean existsBySemesterAndAcademicYear(@Param("semester") SemesterNumber semester, @Param("academicYear") String academicYear);

    @Query("SELECT e FROM ExamTable e WHERE e.semester = :semester AND e.academicYear = :academicYear")
    ExamTable findBySemesterAndAcademicYear(@Param("semester") SemesterNumber semester, @Param("academicYear") String academicYear);

    Optional<ExamTable> findByAcademicYearAndSemester(@Param("academicYear") String academicYear, @Param("semester") SemesterNumber semester);
}