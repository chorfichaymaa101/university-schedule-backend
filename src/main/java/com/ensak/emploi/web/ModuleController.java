package com.ensak.emploi.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ensak.emploi.model.Module;
import com.ensak.emploi.repository.ModuleRepository;
import com.ensak.emploi.services.ModuleService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ModuleController {

    @Autowired
    private ModuleService moduleservice;

    @Autowired
    private ModuleRepository moduleRepository;

    @GetMapping("/module/{programId}")
    public List<Module> getModuleByProgram(@PathVariable("programId") final Long programId) {
        return moduleservice.getModuleByProgram(programId);

    }

    @GetMapping("/moduleById/{id}")
    public Module getModuleById(@PathVariable("id") final Long id) {
        return moduleservice.getModuleById(id);

    }

    @GetMapping("/moduleIdByName/{name}")
    public Long getModuleIdByName(@PathVariable String name) {
        return moduleRepository.findModuleIdByName(name);
    }

    @GetMapping("/getModules")
    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    @GetMapping("/modules")
    public ResponseEntity<List<Module>> getModules() {
        List<Module> modules = moduleservice.findAllModules(); // Service method to get all modules
        return ResponseEntity.ok(modules);
    }

    @PostMapping("/modules")
    public Module createModule(@RequestBody Module module) {
        return moduleservice.saveModule(module);
    }

    @PutMapping("/modules/{id}")
    public Module updateModule(@PathVariable Long id, @RequestBody Module updatedModule) {
        return moduleservice.updateModule(id, updatedModule);
    }

    @DeleteMapping("/modules/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return moduleservice.deleteModule(id);
    }

    @GetMapping("/api/modules")
    public ResponseEntity<List<Module>> getAlllModules() {
        List<Module> modules = moduleservice.findAllModules();  // Service method to get all modules
        return ResponseEntity.ok(modules);
    }
}
