package com.ensak.emploi.model;

import com.ensak.emploi.model.enums.SemesterNumber;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "program")
// @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "program_name", nullable = false)
    private String programName;

    @Column(name = "semester", nullable = false)
    private SemesterNumber semester;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setSemester(SemesterNumber semester) {
        this.semester = semester;
    }

    public SemesterNumber getSemester() {
        return this.semester;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getProgramName() {
        return this.programName;
    }

}
