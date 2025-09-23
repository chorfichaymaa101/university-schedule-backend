package com.ensak.emploi.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ensak.emploi.model.SessionExam;

@Repository
public interface SessionExamRepository extends JpaRepository<SessionExam, Long> {

    @Query("SELECT COUNT(s) > 0 FROM SessionExam s WHERE s.day = :day")
    boolean existsByDay(@Param("day") LocalDate day);

    @Query("SELECT COUNT(s) > 0 FROM SessionExam s WHERE s.day = :day AND :examPeriodDebut <= s.examPeriodDebut And s.examPeriodDebut <= :examPeriodFin AND s.classId.id = :classId")
    boolean existsByDayAndExamPeriodDebutAndClassId(@Param("day") LocalDate day,
            @Param("examPeriodDebut") LocalTime examPeriodDebut,
            @Param("examPeriodFin") LocalTime examPeriodFin,
            @Param("classId") Long classId);

    //le cas si on l'exam a une periode qui n'est pas égale à une autre mais incluse avec la meme heure de début
    @Query("SELECT COUNT(s) > 0 FROM SessionExam s WHERE s.day = :day AND :examPeriodDebut <= s.examPeriodFin And s.examPeriodFin <= :examPeriodFin AND s.classId.id = :classId")
    boolean existsByDayAndExamPeriodFinAndClassId(@Param("day") LocalDate day,
            @Param("examPeriodDebut") LocalTime examPeriodDebut,
            @Param("examPeriodFin") LocalTime examPeriodFin,
            @Param("classId") Long classId);

    @Query("SELECT COUNT(s) > 0 FROM SessionExam s WHERE s.programId.id = :programId AND s.moduleId.id = :moduleId AND s.day = :day")
    boolean existsByProgramAndModule(@Param("moduleId") Long moduleId, @Param("programId") Long programId, @Param("day") LocalDate day);

    @Query("SELECT COUNT(s) > 0 FROM SessionExam s WHERE s.programId.id = :programId AND s.moduleId.id = :moduleId AND s.examCalendarId.id = :examCalendarId")
    boolean existsByProgramAndModuleByExamCalender(@Param("moduleId") Long moduleId, @Param("programId") Long programId, @Param("examCalendarId") Long examCalendarId);

    @Query("SELECT s FROM SessionExam s WHERE s.examCalendarId.id = :examCalendarId")
    List<SessionExam> existsByExamCalendarId(@Param("examCalendarId") Long examCalendarId);

    List<SessionExam> findByExamCalendarId_Id(Long examCalendarId);

    // Fetch a specific session exam using session_exam_id and exam_id
    @Query("SELECT s FROM SessionExam s WHERE s.id = :sessionExamId AND s.examCalendarId.id = :examCalendarId")
    SessionExam findSessionExamBySessionExamIdAndExamId(
            @Param("sessionExamId") Long sessionExamId,
            @Param("examCalendarId") Long examCalendarId
    );
}
