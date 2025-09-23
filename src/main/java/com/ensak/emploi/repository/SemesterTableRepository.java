package com.ensak.emploi.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ensak.emploi.model.SemesterTable;

@Repository
public interface SemesterTableRepository extends JpaRepository<SemesterTable, Long> {

    @Query("SELECT COUNT(s) > 0 FROM SemesterTable s WHERE s.dateDebut = :dateDebut AND s.dateFin = :dateFin")
    boolean existsByDateDebutAndDateFin(@Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin);

    @Query("SELECT s FROM SemesterTable s WHERE s.year = :year")
    List<SemesterTable> existsByYear(@Param("year") String year);

    List<SemesterTable> findAllByYear(String year);

}
