package com.ensak.emploi.repository;

import com.ensak.emploi.model.Person;
import com.ensak.emploi.model.Professor;
import com.ensak.emploi.wrapper.UserWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Person, Long> {

    @Query("SELECT u FROM Person u WHERE u.role = ?1")
    List<Person> findUsersByRole(String role);

    @Query("SELECT u FROM Person u WHERE u.role = ?1")
    Page<Person> findUsersByRoleByPage(String role, Pageable pageable);

    @Query("SELECT u FROM Person u WHERE u.role = 'PROF'")
    Page<Professor> findProfsByRoleByPage(Pageable pageable);

    @Query("SELECT e FROM Person e WHERE (e.name LIKE %?1%)  AND e.role = ?2")
    Page<Person> searchWithPagination(String keyword, String role, Pageable pageable);

    @Query("SELECT u FROM Person u WHERE u.role = ?1")
    List<Person> findAllByRole(String role);

    @Query("SELECT u FROM Person u WHERE u.role = ?2 AND (u.name LIKE %?1%)")
    List<Person> findByNom(String nom, String role);

    @Query("SELECT u FROM Person u WHERE u.email = ?1")
    Person findByEmailId(@Param("email") String email);

    @Query("select u FROM Person u where u.id=:id and u.role=:role  ")
    Person getUerById(@Param("id") Long id, @Param("role") String role);

    Optional<Person> findByEmail(String email);

}
