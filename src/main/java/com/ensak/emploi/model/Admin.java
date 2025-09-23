package com.ensak.emploi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Data
@DiscriminatorValue("ADMIN")
public class Admin extends Person {

}
