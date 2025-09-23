package com.ensak.emploi.services;

import com.ensak.emploi.constents.EmploiConstants;
import com.ensak.emploi.model.*;
import com.ensak.emploi.model.enums.SemesterNumber;
import com.ensak.emploi.repository.ProgramRepository;
import com.ensak.emploi.repository.UserRepository;
import com.ensak.emploi.utils.EmploiUtils;
import com.ensak.emploi.wrapper.UserWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements IUserService {

    private UserRepository userRepository;
    @Autowired
    private ProgramRepository programRepository;

    // TODO: ADD PROGRAM ID FOR STUDENT
    private boolean validaSignUpMap(Map<String, String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("program_id") && requestMap.containsKey("email")) {
            return true;
        }
        return false;
    }

    private Student getUserFromMap(Map<String, String> requestMap) {
        Student user = new Student();
        user.setName(requestMap.get("name"));
        user.setEmail(requestMap.get("email"));
        Long program_id = Long.parseLong(requestMap.get("program_id"));
        Program program = new Program();
        program.setId(program_id);
        System.out.println(program.getProgramName());
        user.setProgram(program);
        return user;
    }

    // TODO: signup only for students
    @Override
    public ResponseEntity<String> signUp(CreateStudent request) {
        try {
            if (request.getProgramName() == null || request.getSemesterId() == null) {
                return ResponseEntity.badRequest()
                        .body("Aucun programme sélectionné. L'etudiant ne peut pas être sauvegardé.");
            }
            int semester = (int) (request.getSemesterId() + 1);
            Program programs = programRepository.findByTitle(request.getProgramName(),
                    SemesterNumber.valueOf("S" + semester));
            System.out.println(programs.getProgramName());
            if (programs == null) {
                return ResponseEntity.badRequest()
                        .body("Le programme spécifiés n'existent pas dans la base de données.");
            }
            Person person = userRepository.findByEmailId(request.getEmail());
            if (Objects.isNull(person)) {
                Student student = new Student();
                student.setName(request.getName());
                student.setEmail(request.getEmail());
                student.setProgram(programs);
                student.setSemesterId(request.getSemesterId());
                student = userRepository.save(student);
                System.out.println("Successfully  Registered.");
                return ResponseEntity.ok("Successfully  Registered.");
            } else {
                // System.out.println("Email already exits.");
                return ResponseEntity.badRequest().body("Email already exits.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // System.out.println(CafeConstants.SOMETHING_WENT_WRONG);
        return ResponseEntity.badRequest().body(EmploiConstants.SOMETHING_WENT_WRONG);
    }

    @Override
    public ResponseEntity<List<Person>> getAllUser() {
        // TODO
        try {
            if (true) {
                return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteUser(Long id) {
        try {
            Optional optional = userRepository.findById(id);
            if (!optional.isEmpty()) {
                userRepository.deleteById(id);
                return EmploiUtils.getResponeEntity("User is deleted successfully", HttpStatus.OK);
            }
            return EmploiUtils.getResponeEntity("User id doesn't exist", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EmploiUtils.getResponeEntity(EmploiConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // TODO: when there is no use to the id
    @Override
    public ResponseEntity<Person> getUserById(Long id, String role) {
        try {
            System.out.println("id" + id);
            return new ResponseEntity<>(userRepository.getUerById(id, role), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new Person(), HttpStatus.INTERNAL_SERVER_ERROR);

        // return (User) userRepository.findById(id).orElseThrow(() -> new
        // RuntimeException("Le User ne avec l'id " + id + " n'existe pas!"));
    }

    @Override
    public Person updateUser(Long id, Person Person) {
        Person.setId(id);
        return (Person) userRepository.save(Person);
    }

    @Override
    public List<Person> getUsers(String role) {
        return userRepository.findUsersByRole(role);
    }

    @Override
    public Person addUser(Person Person) {
        return (Person) userRepository.save(Person);
    }

    @Override
    public Page<Person> searchUsers(String keyword, Pageable pageable, String role) {
        return userRepository.searchWithPagination(keyword, role, pageable);
    }

    @Override
    public List<Person> getAllUsers(String role) {
        return userRepository.findAllByRole(role);
    }

    @Override
    public List<Person> findUserByName(String name, String role) {
        return userRepository.findByNom(name, role);

    }

    @Override
    public Person findByEmailId(String email) {
        return userRepository.findByEmailId(email);
    }

    public Optional<Person> findById(Long id) {
        return Optional.empty();
    }

    public String getProfessorNameById(Long profId) {
        return userRepository.findById(profId)
                .map(Person::getName)
                .orElse("Unknown Professor");
    }

    /*
     * public Long getProgramIdByPersonId(Long personId) {
     * Person person = userRepository.findById(personId)
     * .orElseThrow(() -> new IllegalArgumentException("Person not found with id: "
     * + personId));
     * return person.getProgramId();
     * }
     */
    public List<Person> getAllPersons() {
        return userRepository.findAll();
    }

    public String getUserRoleById(Long userId) {
        Person person = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return person.getRole();
    }

    public Long getSemesterIdByPersonId(Long personId) {
        return userRepository.findById(personId)
                .map(Person::getSemesterId)
                .orElseThrow(() -> new IllegalArgumentException("Person not found"));
    }

}
