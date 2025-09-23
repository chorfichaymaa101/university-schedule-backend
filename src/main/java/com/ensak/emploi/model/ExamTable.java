package com.ensak.emploi.model;

import java.util.List;

import com.ensak.emploi.model.enums.SemesterNumber;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "examtable")
public class ExamTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "academic_year", nullable = false)
    private String academicYear;

    @Column(name = "semester", nullable = false)
    private SemesterNumber semester;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setAcademicYear(String academicyear) {
        this.academicYear = academicyear;
    }

    public String getAcademicYear() {
        return this.academicYear;
    }

    public void setSemester(SemesterNumber semester) {
        this.semester = semester;
    }

    public SemesterNumber getSemester() {
        return this.semester;
    }

}
