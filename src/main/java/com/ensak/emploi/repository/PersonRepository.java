package com.ensak.emploi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ensak.emploi.model.Person;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT p FROM Person p JOIN ProfProgram prp ON p.id = prp.professorId WHERE prp.programId.id = :programId")
    List<Person> findProfByProgram(@Param("programId") Long programId);

    @Query("SELECT p FROM Person p WHERE p.id = :id")
    Person findProfByid(@Param("id") Long id);

    Optional<Person> findPersonById(Long profId);
}
