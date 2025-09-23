package com.ensak.emploi.model;

import com.ensak.emploi.model.enums.SemesterNumber;

import java.util.List;

public class CreateModuleRequest {
    private Long id;
    private String programName;
    private List<String> moduleNames;
    private int semester;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public int getSemester() {
        return this.semester;
    }

    public void setSemester(int semesterNumber) {
        this.semester = semesterNumber;
    }

    @Override
    public String toString() {
        return "CreateModuleRequest{" +
                "programName='" + programName + '\'' +
                ", moduleNames=" + moduleNames +
                ", semester=" + semester +
                '}';
    }

    // Getter and Setter for name
    public String getprogramName() {
        return programName;
    }

    public void setprogramName(String name) {
        this.programName = name;
    }

    // Getter and Setter for programNames
    public List<String> getModuleNames() {
        return moduleNames;
    }

    public void setModuleNames(List<String> programNames) {
        this.moduleNames = programNames;
    }
}