package com.ensak.emploi.model;

import com.ensak.emploi.model.enums.Day;
import com.ensak.emploi.model.enums.Period;
import com.ensak.emploi.model.enums.SessionType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // This will be auto-generated

    @Column(name = "session_type", nullable = false)
    private SessionType sessionType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", nullable = false)
    private Person professorId;

    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private Module moduleId;

    @Column(name = "day", nullable = false)
    private Day day;

    @Column(name = "time", nullable = false)
    private Period time;

    @Column(name = "groupe")
    private String groupe;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private Class classId;

    @ManyToOne
    @JoinColumn(name = "timetable_id", nullable = false)
    private TimeTable timetableId;

    @Column(name = "capacity")
    private Long capacity;

    public void setClassId(Long classId) {
        this.classId = new Class();
        this.classId.setId(classId);
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = new Module();
        this.moduleId.setId(moduleId);
    }

    public void setProfessorId(Long professorId) {
        this.professorId = new Person();
        this.professorId.setId(professorId);
    }

    public void setTimetableId(Long timetableId) {
        this.timetableId = new TimeTable();
        this.timetableId.setId(timetableId);
    }

    public void setGroupe(String groupe) {

        this.groupe = groupe;
    }

    public void setTime(Period time) {
        this.time = time;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    // Getters pour récupérer les IDs
    public Long getClassId() {
        return this.classId != null ? this.classId.getId() : null;
    }

    public Long getProfessorId() {
        return this.professorId != null ? this.professorId.getId() : null;
    }

    public Long getModuleId() {
        return this.moduleId != null ? this.moduleId.getId() : null;
    }

    public Long getTimetableId() {
        return this.timetableId != null ? this.timetableId.getId() : null;
    }

    public TimeTable getTimeTable() {
        return timetableId;
    }

    public Period getTime() {
        return this.time;
    }

    public Day getDay() {
        return this.day;
    }

    public String getGroupe() {
        return this.groupe;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }

    public SessionType getSessionType() {
        return this.sessionType;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public Long getCapacity() {
        return this.capacity;
    }

    public Long getId() {
        return this.id;
    }
}
