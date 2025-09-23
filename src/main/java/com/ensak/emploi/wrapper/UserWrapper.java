package com.ensak.emploi.wrapper;

import com.ensak.emploi.model.Program;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWrapper {
    private Long id;
    private String name;
    private String email;
    private Boolean authenticated;
    private String role;

    public UserWrapper(Long id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
