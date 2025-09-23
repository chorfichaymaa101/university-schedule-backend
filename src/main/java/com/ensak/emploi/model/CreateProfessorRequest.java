package com.ensak.emploi.model;

import java.util.List;

public class CreateProfessorRequest {
    private Long id;
    private String name;
    private String email;
    private List<String> programNames;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for programNames
    public List<String> getProgramNames() {
        return programNames;
    }

    public void setProgramNames(List<String> programNames) {
        this.programNames = programNames;
    }

    public CreateProfessorRequest() {
        this.id = id;
        this.name = name;
        this.programNames = programNames;
        this.email = email;
    }
}
