package com.ensak.emploi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import java.sql.Time;
import java.util.Date;
import java.time.LocalTime;

@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "professor_id")
    private Long professorId;

    @Column(name = "program_name")
    private String programName;

    @Column(name = "module_id")
    private Long moduleId;

    @Column(name = "program_id")
    private Long programId;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "new_time")
    private LocalTime newTime;

    @Column(name = "end_new_time")
    private LocalTime endNewTime;

    @Column(name = "\"day\"")
    private Date day;

    @Column(name = "status")
    private String status;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "class_id")
    private Long classId;

    @Column(name = "semester_id")
    private Long semesterId;

    @Column(name = "new_day")
    private String newDay;

    @Column(name = "old_day")
    private String oldDay;

    @Column(name = "new_class")
    private Long newClass;

    public Request() {
    }

    public Request(LocalTime time, String status, Date creationDate, Date day, Long professorId, Long moduleId, Long programId, Long classId, Long semesterId) {
        this.time = time;
        this.status = status;
        this.creationDate = creationDate;
        this.day = day;
        this.professorId = professorId;
        this.moduleId = moduleId;
        this.programId = programId;
        this.classId = classId;
        this.semesterId = semesterId;
    }

    public Long getRequestId() {
        return Id;
    }

    public void setRequestId(Long requestId) {
        this.Id = requestId;
    }


    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }
    
    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    public Long getNewClass() {
        return newClass;
    }

    public void setNewClass(Long new_class) {
        this.newClass = new_class;
    }

    public String getNewDay() {
        return newDay;
    }

    public void setNewDay(String new_day) {
        this.newDay = new_day;
    }

    public LocalTime getNewTime() {
        return newTime;
    }

    public LocalTime getEndNewTime() {
        return endNewTime;
    }

    public void setEndNewTime(LocalTime endNewTime) {
        this.endNewTime = endNewTime;
    }

    public void setNewTime(LocalTime new_time) {
        this.newTime = new_time;
    }

    public String getOldDay() {
        return oldDay;
    }

    public void setOldDay(String old_day) {
        this.oldDay = old_day;
    }
}
