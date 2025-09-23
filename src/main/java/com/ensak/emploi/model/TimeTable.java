package com.ensak.emploi.model;

import java.util.List;

import com.ensak.emploi.model.enums.SemesterNumber;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Transient;

@Entity
@Data
@Table(name = "timetable")

public class TimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "program_id", nullable = false)
    private Program programId;

    @Column(name = "academic_year", nullable = false)
    private String academicYear;

    public String getProgramName() {
        return programId != null ? programId.getProgramName() : null;
    }

    public SemesterNumber getSemester() {
        return programId != null ? programId.getSemester() : null;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public Long getProgramId() {
        return this.programId != null ? this.programId.getId() : null;
    }

    public Program getProgram() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = new Program();
        this.programId.setId(programId);
    }

    public void setAcademicYear(String academicyear) {
        this.academicYear = academicyear;
    }

    public String getAcademicYear() {
        return this.academicYear;
    }

}
