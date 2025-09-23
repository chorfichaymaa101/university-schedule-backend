package com.ensak.emploi.repository;

import com.ensak.emploi.model.Session;
import com.ensak.emploi.model.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ensak.emploi.model.Session;
import com.ensak.emploi.model.enums.Day;
import com.ensak.emploi.model.enums.Period;
import com.ensak.emploi.model.enums.SemesterNumber;
import com.ensak.emploi.model.enums.SessionType;

import jakarta.transaction.Transactional;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

        @Query("SELECT COUNT(s) > 0 FROM Session s WHERE s.classId.id = :classId AND s.timetableId.programId.semester = :semester AND s.timetableId.academicYear = :academicYear AND s.day = :day AND s.time = :time")
        boolean existsByClassAndProgramAndAcademicYearAndDayAndTime(
                        @Param("classId") Long classId,
                        @Param("semester") SemesterNumber semester,
                        @Param("academicYear") String academicYear,
                        @Param("day") Day day,
                        @Param("time") Period time);

        @Query("SELECT COUNT(s) > 0 FROM Session s WHERE s.moduleId.id = :moduleId AND s.sessionType = :sessionType AND s.timetableId.academicYear = :academicYear")
        boolean existsByModuleIdBySessiontype(
                        @Param("moduleId") Long moduleId,
                        @Param("sessionType") SessionType sessionType,
                        @Param("academicYear") String academicYear);

        @Query("SELECT s FROM Session s WHERE s.timetableId.id = :timetableId AND s.day = :day AND s.time = :time")
        Session existsByTimeTableIdByDayByTime(@Param("timetableId") Long timetableId, @Param("day") Day day,
                        @Param("time") Period time);

        @Query("SELECT s FROM Session s WHERE s.id = :session_id AND s.timetableId.id = :timeTableId")
        Session findBySessionIdAndTimetableId(@Param("session_id") Long sessionId,
                        @Param("timeTableId") Long timeTableId);

        // @Query("SELECT s FROM Session s WHERE s.session_id = :sessionId AND
        // s.timetableId = :timeTableId")
        void delete(Session session);

        List<Session> findByTimetableId_Id(Long timetableId_id);

        Session findSessionById(Long id);

        @Modifying
        @Transactional
        @Query("DELETE FROM Session pp WHERE pp.professorId.id = :professorId")
        void deleteByProfessorId(@Param("professorId") Long id);
}
