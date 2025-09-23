package com.ensak.emploi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "profprogram")
public class ProfProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program programId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person professorId;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Program getProgram() {
        return programId;
    }

    public void setProgram(Program programId) {
        this.programId = programId;
    }

    public Person getProfessor() {
        return professorId;
    }

    public void setProfessor(Person professorId) {
        this.professorId = professorId;
    }
}
