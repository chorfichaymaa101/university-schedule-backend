package com.ensak.emploi.model;

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
@Table(name = "moduleprogram")
public class ModuleProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "program_id", nullable = false) // Foreign key column
    private Program programId;

    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false) // Foreign key column
    private Module moduleId;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setProgramId(Long programId) {
        this.programId = new Program();
        this.programId.setId(programId);

    }

    public Long getProgramId() {
        return this.programId != null ? this.programId.getId() : null;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = new Module();
        this.moduleId.setId(moduleId);

    }

    public Long getModuleId() {
        return this.moduleId != null ? this.moduleId.getId() : null;
    }

}
