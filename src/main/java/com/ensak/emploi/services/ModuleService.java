package com.ensak.emploi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ensak.emploi.constents.EmploiConstants;
import com.ensak.emploi.model.Module;
import com.ensak.emploi.repository.ModuleProgramRepository;
import com.ensak.emploi.repository.ModuleRepository;
import com.ensak.emploi.repository.ProgramRepository;
import com.ensak.emploi.utils.EmploiUtils;

@Service
public class ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    public Module saveModule(Module module) {
        return moduleRepository.save(module);
    }

    public Module updateModule(Long id, Module module) {
        module.setId(id);
        return moduleRepository.save(module);
    }

    public ResponseEntity<String> deleteModule(Long id) {
        try {
            Optional optional = moduleRepository.findById(id);
            if (!optional.isEmpty()) {
                moduleRepository.deleteById(id);
                return EmploiUtils.getResponeEntity("Module is deleted successfully", HttpStatus.OK);
            }
            return EmploiUtils.getResponeEntity("Module id doesn't exist", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EmploiUtils.getResponeEntity(EmploiConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public List<Module> getModuleByProgram(final Long programid) {
        return moduleRepository.findModuleByProgram(programid);
    }

    public Module getModuleById(final Long id) {
        return moduleRepository.findModuleById(id);
    }

    public String getModuleNameById(Long moduleId) {
        Module moduleOptional = moduleRepository.findModuleNameById(moduleId);
        String moduleName = null;
        if (moduleOptional != null) {
            moduleName = moduleOptional.getModuleName();
        } else {
            System.out.println("Module with the id " + moduleId + " doesn't exist");
        }
        return moduleName;
    }

    public List<Module> findAllModules() {
        return moduleRepository.findAll();
    }

    public Optional<Module> findById(Long id) {
        return moduleRepository.findById(id);
    }
    /*
     * public String getModuleNameById(Long moduleId) {
     * return moduleRepository.findById(moduleId)
     * .map(Module::getModuleName)
     * .orElse("Unknown Module");
     * }
     */
}
