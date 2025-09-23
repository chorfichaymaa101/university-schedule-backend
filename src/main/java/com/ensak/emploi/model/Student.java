package com.ensak.emploi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("STUDENT")
public class Student extends Person {

    @ManyToOne
    @JoinColumn(name = "program_id", nullable = true)
    private Program program;

    public Program getProgram() {
        return program;
    }

    // Setter for program
    public void setProgram(Program program) {
        this.program = program;
    }

}
