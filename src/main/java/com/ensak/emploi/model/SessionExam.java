package com.ensak.emploi.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;

@Data
@Entity
@Table(name = "sessionexam")
public class SessionExam {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    @JsonProperty("professorId")
    private Person professorId;

    @ManyToOne
    @JoinColumn(name = "program_id", nullable = false)
    @JsonProperty("programId")
    private Program programId;

    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    @JsonProperty("moduleId")
    private Module moduleId;

    @JsonFormat(pattern = "HH:mm") // Format for JSON serialization and deserialization
    @Column(name = "exam_period_debut", nullable = false)
    private LocalTime examPeriodDebut;

    @JsonFormat(pattern = "HH:mm") // Format for JSON serialization and deserialization
    @Column(name = "exam_period_fin", nullable = false)
    private LocalTime examPeriodFin;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    @JsonProperty("classId")
    private Class classId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "day", nullable = false)
    private LocalDate day;

    @ManyToOne
    @JoinColumn(name = "exam_calendar_id", nullable = false)
    @JsonProperty("examCalendarId")
    private ExamTable examCalendarId;

    @Column(name = "capacity")
    private Long capacity;

    // Getters et setters modifiés pour gérer les ID comme des objets
    public void setClassId(Long classId) {
        this.classId = new Class();
        this.classId.setId(classId);
    }

    public void setProfessorId(Long professorId) {
        this.professorId = new Person();
        this.professorId.setId(professorId);
    }

    public void setProfessor(Person professor) {
        this.professorId = professor;
    }

    public void setExamCalendarId(Long examCalendarId) {
        this.examCalendarId = new ExamTable();
        this.examCalendarId.setId(examCalendarId);
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = new Module();
        this.moduleId.setId(moduleId);
    }

    public void setProgramId(Long programId) {
        this.programId = new Program();
        this.programId.setId(programId);
    }

    public void setExamPeriodDebut(LocalTime examperioddebut) {
        this.examPeriodDebut = examperioddebut;
    }

    public void setExamPeriodFin(LocalTime examPeriodFin) {
        this.examPeriodFin = examPeriodFin;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    // Getters pour récupérer les IDs
    public Long getClassId() {
        return this.classId != null ? this.classId.getId() : null;
    }

    public Long getId() {
        return this.id;
    }

    public Long getProfessorId() {
        return this.professorId != null ? this.professorId.getId() : null;
    }

    public Person getProfessor() {
        return this.professorId != null ? this.professorId : null;
    }

    public Long getProgramId() {
        return this.programId != null ? this.programId.getId() : null;
    }

    public Long getModuleId() {
        return this.moduleId != null ? this.moduleId.getId() : null;
    }

    public Long getExamCalendarId() {
        return this.examCalendarId != null ? this.examCalendarId.getId() : null;
    }

    public LocalDate getDay() {
        return this.day;
    }

    public LocalTime getExamPeriodDebut() {
        return this.examPeriodDebut;
    }

    public LocalTime getExamPeriodFin() {
        return this.examPeriodFin;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public Long getCapacity() {
        return this.capacity;
    }

}
