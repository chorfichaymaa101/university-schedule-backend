package com.ensak.emploi.web;

import com.ensak.emploi.model.*;
import com.ensak.emploi.services.*;
import com.ensak.emploi.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.sql.Timestamp;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://10.0.2.2:1000"})
@RequestMapping("/api/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private NotificationsAdminService notificationsAdminService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService personService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private ProgramService programService;

    @Autowired
    private ClassService sessionClassService;

    @Autowired
    private SemesterTableService semesterService;

    @PutMapping("/notifications/mark-all-checked/{user_id}")
    public ResponseEntity<Map<String, String>> markNotificationsAsChecked(@PathVariable("user_id") Long userId) {
        try {
            String userRole = personService.getUserRoleById(userId);

            if ("Admin".equalsIgnoreCase(userRole)) {
                notificationsAdminService.markAllNotificationsAsChecked();
                return ResponseEntity.ok(Map.of("message", "All admin notifications marked as checked"));
            } else if ("Prof".equalsIgnoreCase(userRole) || "Student".equalsIgnoreCase(userRole)) {
                notificationService.markNotificationsAsCheckedForUser(userId);
                return ResponseEntity.ok(Map.of("message", "All user notifications marked as checked"));
            } else {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Invalid role for the user."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while marking notifications as checked."));
        }
    }

    @GetMapping("/notifications/unchecked-count/{user_id}")
    public ResponseEntity<?> getUncheckedNotificationsCount(@PathVariable("user_id") Long userId) {
        try {
            String userRole = personService.getUserRoleById(userId);

            int count;
            if ("Prof".equalsIgnoreCase(userRole) || "Student".equalsIgnoreCase(userRole)) {
                count = notificationService.getUncheckedNotificationsCountForUser(userId);
            } else if ("Admin".equalsIgnoreCase(userRole)) {
                count = notificationsAdminService.countUncheckedNotifications();
            } else {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Invalid role for the user."));
            }

            return ResponseEntity.ok(Map.of("uncheckedCount", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while fetching notifications count."));
        }
    }

    /*  @GetMapping("/{id}")
    public ResponseEntity<Request> getRequestById(@PathVariable Long id) {
        Optional<Request> request = requestService.getRequestById(id);
        return request.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    } */
    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<Map<String, Object>>> getRequestsByProfessorId(@PathVariable Long professorId) {
        List<Request> requests = requestService.getRequestsByProfessorId(professorId);

        if (requests.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Map<String, Object>> enrichedRequests = new ArrayList<>();
        for (Request request : requests) {
            Map<String, Object> enrichedRequest = new HashMap<>();
        
            // Check professorId
            enrichedRequest.put("professorId", request.getProfessorId() != null ? request.getProfessorId() : null);
        
            // Check moduleId
            enrichedRequest.put("module_id", request.getModuleId() != null ? request.getModuleId() : null);
            enrichedRequest.put("module_name", request.getModuleId() != null ? moduleService.getModuleNameById(request.getModuleId()) : null);
        
            // Check programId
            enrichedRequest.put("program_name", request.getProgramId() != null ? programService.getProgramNameById(request.getProgramId()) : null);
        
            // Check semesterId
            enrichedRequest.put("semester_name", request.getSemesterId() != null ? request.getSemesterId() + 1 : null);

        
            // Check classId
            Long classId = request.getClassId();
            enrichedRequest.put("class_name", classId != null ? sessionClassService.getClassNameById(classId) : null);
        
            // Add day, status, and time (assume these are always available)
            enrichedRequest.put("day", request.getDay());
            enrichedRequest.put("status", request.getStatus());
            enrichedRequest.put("time", request.getTime());
        
            // Check creationDate
            enrichedRequest.put("creationDate", request.getCreationDate() != null ? request.getCreationDate() : null);
        
            // Check newClassId
            Long newClassId = request.getNewClass();
            enrichedRequest.put("new_class_name", newClassId != null ? sessionClassService.getClassNameById(newClassId) : null);
        
            // Check new_time and end_new_time
            enrichedRequest.put("new_time", request.getNewTime() != null ? request.getNewTime() : null);
            enrichedRequest.put("end_new_time", request.getEndNewTime() != null ? request.getEndNewTime() : null);
        
            // Check old_time and end_old_time
            enrichedRequest.put("old_time", request.getTime() != null ? request.getTime() : null);
            enrichedRequest.put("end_old_time", request.getEndTime() != null ? request.getEndTime() : null);
        
            // Check old_day and new_day
            enrichedRequest.put("old_day", request.getOldDay() != null ? request.getOldDay() : null);
            enrichedRequest.put("new_day", request.getNewDay() != null ? request.getNewDay() : null);
        
            // Determine title
            String title = request.getNewDay() == null ? "Seance de Rattrapage" : "Changement de la seance d'Emplois";
            enrichedRequest.put("title", title);
        
            enrichedRequests.add(enrichedRequest);
        }
        

        return ResponseEntity.ok(enrichedRequests);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<Map<String, Object>>> getAllRequests() {

        List<Request> allRequests = requestService.getAllRequests();

        if (allRequests.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Map<String, Object>> enrichedRequests = new ArrayList<>();

        for (Request request : allRequests) {
            Map<String, Object> enrichedRequest = new HashMap<>();
            
            // Check and add requestId
            enrichedRequest.put("requestId", request.getRequestId());
            
            // Check and add professorId
            if (request.getProfessorId() != null) {
                enrichedRequest.put("professorId", request.getProfessorId());
                enrichedRequest.put("professor_name", personService.getProfessorNameById(request.getProfessorId()));
            } else {
                enrichedRequest.put("professorId", null);
                enrichedRequest.put("professor_name", null);
            }
        
            // Check and add moduleId
            if (request.getModuleId() != null) {
                enrichedRequest.put("module_id", request.getModuleId());
                enrichedRequest.put("module_name", moduleService.getModuleNameById(request.getModuleId()));
            } else {
                enrichedRequest.put("module_id", null);
                enrichedRequest.put("module_name", null);
            }
        
            // Check and add programId
            if (request.getProgramId() != null) {
                enrichedRequest.put("program_name", programService.getProgramNameById(request.getProgramId()));
            } else {
                enrichedRequest.put("program_name", null);
            }
        
            // Check and add semesterId
            if (request.getSemesterId() != null) {
                enrichedRequest.put("semester_name", request.getSemesterId() + 1);

            } else {
                enrichedRequest.put("semester_name", null);
            }
        
            // Check and add classId
            Long classId = request.getClassId();
            if (classId != null) {
                enrichedRequest.put("classId", classId);
                enrichedRequest.put("class_name", sessionClassService.getClassNameById(classId));
            } else {
                enrichedRequest.put("classId", null);
                enrichedRequest.put("class_name", null);
            }
        
            // Check and add day
            enrichedRequest.put("day", request.getDay());
        
            // Check and add status
            enrichedRequest.put("status", request.getStatus());
        
            // Check and add time
            enrichedRequest.put("time", request.getTime());
        
            // Check and add creationDate
            enrichedRequest.put("creationDate", request.getCreationDate());
        
            // Check and add newClass
            Long newClassId = request.getNewClass();
            if (newClassId != null) {
                enrichedRequest.put("new_class_name", sessionClassService.getClassNameById(newClassId));
            } else {
                enrichedRequest.put("new_class_name", null);
            }
        
            // Check and add newTime
            enrichedRequest.put("new_time", request.getNewTime());
        
            // Check and add endNewTime
            enrichedRequest.put("end_new_time", request.getEndNewTime());
        
            // Check and add oldTime
            enrichedRequest.put("old_time", request.getTime());
        
            // Check and add endOldTime
            enrichedRequest.put("end_old_time", request.getEndTime());
        
            // Check and add oldDay
            enrichedRequest.put("old_day", request.getOldDay());
        
            // Check and add newDay
            enrichedRequest.put("new_day", request.getNewDay());
        
            // Determine the title
            String title = request.getNewDay() == null ? "Seance de Rattrapage" : "Changement de la seance d'Emplois";
            enrichedRequest.put("title", title);
        
            enrichedRequests.add(enrichedRequest);
        }
        

        return ResponseEntity.ok(enrichedRequests);
    }

    @GetMapping("/student/{student_id}")
    public ResponseEntity<List<Map<String, Object>>> getRequestsForStudent(@PathVariable("student_id") Long studentId) {
        Long studentProgramId;
        Long studentSemesterId;
        String programName;

        Student student;
        try {
            student = studentService.findById(studentId);
            studentProgramId = student.getProgram().getId();
            
            //studentProgramId = personService.getProgramIdByPersonId(studentId);
            studentSemesterId = personService.getSemesterIdByPersonId(studentId);
            programName = programService.getProgramNameById(studentProgramId);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonList(Map.of("error", "Student not found")));
        }

        List<Request> filteredRequests = requestService.getRequestsByProgramNameAndSemesterId(programName, studentSemesterId);

        //List<Request> filteredRequests = requestService.getRequestsByProgramAndSemester(studentProgramId, studentSemesterId);
        if (filteredRequests.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Map<String, Object>> enrichedRequests = new ArrayList<>();
        for (Request request : filteredRequests) {
            Map<String, Object> enrichedRequest = new HashMap<>();
            
            // Check and add professorId
            if (request.getProfessorId() != null) {
                enrichedRequest.put("professorId", request.getProfessorId());
                enrichedRequest.put("professor_name", personService.getProfessorNameById(request.getProfessorId()));
            } else {
                enrichedRequest.put("professorId", null);
                enrichedRequest.put("professor_name", null);
            }
        
            // Check and add moduleId
            if (request.getModuleId() != null) {
                enrichedRequest.put("module_id", request.getModuleId());
                enrichedRequest.put("module_name", moduleService.getModuleNameById(request.getModuleId()));
            } else {
                enrichedRequest.put("module_id", null);
                enrichedRequest.put("module_name", null);
            }
        
            // Check and add programId
            if (request.getProgramId() != null) {
                enrichedRequest.put("program_name", programService.getProgramNameById(request.getProgramId()));
            } else {
                enrichedRequest.put("program_name", null);
            }
        
            // Check and add semesterId
            if (request.getSemesterId() != null) {
                enrichedRequest.put("semester_name", request.getSemesterId() + 1);
            } else {
                enrichedRequest.put("semester_name", null);
            }
        
            // Check and add classId
            Long classId = request.getClassId();
            enrichedRequest.put("class_name", classId != null ? sessionClassService.getClassNameById(classId) : null);
        
            // Add day, status, time, and end_old_time
            enrichedRequest.put("day", request.getDay());
            enrichedRequest.put("status", request.getStatus());
            enrichedRequest.put("time", request.getTime());
            enrichedRequest.put("end_old_time", request.getEndTime());
        
            // Check and add creationDate
            enrichedRequest.put("creationDate", request.getCreationDate());
        
            // Check and add newClass
            Long newClassId = request.getNewClass();
            enrichedRequest.put("new_class_name", newClassId != null ? sessionClassService.getClassNameById(newClassId) : null);
        
            // Add new_time, end_new_time, and old_time
            enrichedRequest.put("new_time", request.getNewTime());
            enrichedRequest.put("end_new_time", request.getEndNewTime());
            enrichedRequest.put("old_time", request.getTime());
        
            // Add old_day and new_day
            enrichedRequest.put("old_day", request.getOldDay());
            enrichedRequest.put("new_day", request.getNewDay());
        
            // Determine the title
            String title = request.getNewDay() == null ? "Seance de Rattrapage" : "Changement de la seance d'Emplois";
            enrichedRequest.put("title", title);
        
            enrichedRequests.add(enrichedRequest);
        }
        

        return ResponseEntity.ok(enrichedRequests);
    }

    @PostMapping()
    public ResponseEntity<Request> createRequest(@RequestBody Map<String, Object> requestBody) {
        try {
            Request request = new Request();

            Long professorId = ((Integer) requestBody.get("professorId")).longValue();
            Long moduleId = ((Integer) requestBody.get("moduleId")).longValue();
            Long programId = ((Integer) requestBody.get("programId")).longValue();

            Long sessionClassId = requestBody.get("sessionClassId") != null ? ((Integer) requestBody.get("sessionClassId")).longValue() : null;
            Long semesterId = requestBody.get("semesterId") != null ? ((Integer) requestBody.get("semesterId")).longValue() : null;


            String programName = programService.getProgramNameById(programId);

            request.setProgramName(programName);
            request.setProfessorId(professorId);
            request.setModuleId(moduleId);
            request.setProgramId(programId);
            request.setClassId(sessionClassId);
            request.setSemesterId(semesterId);

            String timeString = (String) requestBody.get("time");
            LocalTime time = LocalTime.parse(timeString);
            request.setTime(time);

            String endTimeString = (String) requestBody.get("endTime");
            LocalTime endTime = LocalTime.parse(endTimeString);
            request.setEndTime(endTime);

            String day = (String) requestBody.get("day");
            Date dayy = Date.valueOf(day);
            request.setDay(dayy);

            //request.setStatus((String) requestBody.get("status"));
            request.setStatus("Pending");

            //String creationDateString = (String) requestBody.get("creationDate");
            LocalDateTime currentDateTime = LocalDateTime.now();
            Timestamp creationDate = Timestamp.valueOf(currentDateTime); // Convert LocalDateTime to Timestamp
            request.setCreationDate(creationDate);

            Request savedRequest = requestService.saveRequest(request);

            notificationsAdminService.createNotificationsForRequest(savedRequest.getRequestId());

            return ResponseEntity.status(HttpStatus.CREATED).body(savedRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/new")
    public ResponseEntity<Request> createNewRequest(@RequestBody Map<String, Object> requestBody) {
        try {
            Request request = new Request();

            Long professorId = ((Integer) requestBody.get("professorId")).longValue();
            Long moduleId = ((Integer) requestBody.get("moduleId")).longValue();
            Long programId = ((Integer) requestBody.get("programId")).longValue();

            String programName = programService.getProgramNameById(programId);

            request.setProgramName(programName);

            Long classId = requestBody.get("sessionClassId") != null
                    ? ((Integer) requestBody.get("sessionClassId")).longValue() : null;

            Long semesterId = requestBody.get("semesterId") != null
                    ? ((Integer) requestBody.get("semesterId")).longValue() : null;

            String timeString = (String) requestBody.get("oldTime");
            LocalTime time = LocalTime.parse(timeString);

            String endTimeString = (String) requestBody.get("endOldTime");
            LocalTime endTime = LocalTime.parse(endTimeString);

            LocalDateTime currentDateTime = LocalDateTime.now();
            Timestamp creationDate = Timestamp.valueOf(currentDateTime); // Convert LocalDateTime to Timestamp

            String newDay = (String) requestBody.get("newDay");
            String oldDay = (String) requestBody.get("oldDay");
            Long oldClass = ((Integer) requestBody.get("oldClass")).longValue();
            Long newClass = ((Integer) requestBody.get("newClass")).longValue();

            String newTimeString = (String) requestBody.get("newTime");
            LocalTime newTime = LocalTime.parse(newTimeString);
            String endNewTimeString = (String) requestBody.get("endNewTime");
            LocalTime endNewTime = LocalTime.parse(endNewTimeString);

            request.setProfessorId(professorId);
            request.setModuleId(moduleId);
            request.setProgramId(programId);
            request.setTime(time);
            request.setEndTime(endTime);
            request.setOldDay(oldDay);
            request.setStatus((String) requestBody.get("status"));
            request.setCreationDate(creationDate);
            request.setClassId(oldClass);
            request.setSemesterId(semesterId);
            request.setNewClass(newClass);
            request.setNewDay(newDay);
            request.setNewTime(newTime);
            request.setEndNewTime(endNewTime);

            Request savedRequest = requestService.saveRequest(request);

            notificationsAdminService.createNotificationsForRequest(savedRequest.getRequestId());

            return ResponseEntity.status(HttpStatus.CREATED).body(savedRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{requestId}/status")
    public ResponseEntity<Map<String, String>> updateRequestStatus(
            //To update: When the status is refused, the professor should recieve the notification;
            //To update: When the status is approved, all users_id inserted to the notifications table except for the admin;

            @PathVariable Long requestId,
            @RequestBody Map<String, Object> requestBody) {

        String newStatus = (String) requestBody.get("status");
        if (newStatus == null || newStatus.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Status must not be empty."));
        }

        try {
            Object classIdObject = requestBody.get("classId");
            Long classId = null;

            if (classIdObject instanceof String) {
                classId = Long.parseLong((String) classIdObject);
            } else if (classIdObject instanceof Integer) {
                classId = ((Integer) classIdObject).longValue();
            }

            Request request = requestService.getRequestById(requestId);
            if (request == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Request not found."));
            }

            if ("Approved".equalsIgnoreCase(newStatus)) {
                if (request.getDay() != null && classId != null) {
                    requestService.updateClassId(requestId, classId);
                }
                requestService.updateClassId(requestId, classId);
                requestService.updateRequestStatus(requestId, newStatus);

                notificationService.createNotificationsForAllPersons(requestId);

                return ResponseEntity.ok(Map.of("message", "Request status updated successfully."));
            }
            if ("Refused".equalsIgnoreCase(newStatus)) {
                requestService.updateRequestStatus(requestId, newStatus);
                notificationService.createNotificationsForAllPersons(requestId);
                return ResponseEntity.ok(Map.of("message", "Request status updated successfully."));
            }

            if (classId == null && !"Approved".equalsIgnoreCase(newStatus) && !"Refused".equalsIgnoreCase(newStatus)) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "classId is required for status '" + newStatus + "'."));
            }

            requestService.updateClassId(requestId, classId);
            requestService.updateRequestStatus(requestId, newStatus);

            return ResponseEntity.ok(Map.of("message", "Request status updated successfully."));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid classId format. Must be an integer."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while updating the status."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        requestService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }
}
