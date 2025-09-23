package com.ensak.emploi.model;

import com.ensak.emploi.model.enums.ClassType;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "class")
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class")
    private String classname;

    @Column(name = "capacity")
    private Long capacity;

    @Column(name = "type", nullable = false)
    private ClassType type;

    public Class() {
    }

    public Class(String class_name, Long capacity, ClassType type) {
        this.classname = class_name;
        this.capacity = capacity;
        this.type = type;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassname() {
        return this.classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public Long getCapacity() {
        return this.capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public ClassType getType() {
        return this.type;
    }

    public void setType(ClassType type) {
        this.type = type;
    }

}
