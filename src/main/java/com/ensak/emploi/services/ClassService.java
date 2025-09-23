package com.ensak.emploi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ensak.emploi.constents.EmploiConstants;
import com.ensak.emploi.model.Class;
import com.ensak.emploi.model.enums.ClassType;
import com.ensak.emploi.repository.ClassRepository;
import com.ensak.emploi.utils.EmploiUtils;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    public List<Class> getClassByTypeCpacity(final ClassType type, final Long capacity) {
        return classRepository.findClassByTypeCapacity(type, capacity);
    }

    public Class getClassById(Long id) {
        return classRepository.findClassById(id);
    }

    public List<Class> findAllClasses() {
        return classRepository.findAll();
    }

    public Optional<Class> findById(Long id) {
        return Optional.empty();
    }

    public String getClassNameById(Long classId) {
        return classRepository.findById(classId)
                .map(Class::getClassname)
                .orElse("Unknown Class");
    }

    public Class saveClass(Class classe) {
        return classRepository.save(classe);
    }

    public Class updateClass(Long id, Class classe) {
        classe.setId(id);
        return classRepository.save(classe);
    }

    public ResponseEntity<String> deleteClass(Long id) {
        try {
            Optional optional = classRepository.findById(id);
            if (!optional.isEmpty()) {
                classRepository.deleteById(id);
                return EmploiUtils.getResponeEntity("Class is deleted successfully", HttpStatus.OK);
            }
            return EmploiUtils.getResponeEntity("Class id doesn't exist", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EmploiUtils.getResponeEntity(EmploiConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
