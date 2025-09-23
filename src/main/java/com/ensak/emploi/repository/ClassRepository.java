package com.ensak.emploi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ensak.emploi.model.Class;
import com.ensak.emploi.model.enums.ClassType;

import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {

    @Query("SELECT c FROM Class c WHERE c.type = :type AND c.capacity >= :capacity")
    List<Class> findClassByTypeCapacity(@Param("type") ClassType type, @Param("capacity") Long capacity);

    @Query("SELECT c FROM Class c WHERE c.id = :id")
    Class findClassById(@Param("id") Long id);

    String getClassNameById(Long id);

    //Optional<Class> findClassById(Long id);
}
