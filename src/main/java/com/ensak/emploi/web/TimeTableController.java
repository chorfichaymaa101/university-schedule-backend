package com.ensak.emploi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ensak.emploi.model.TimeTable;
import com.ensak.emploi.repository.ProgramRepository;
import com.ensak.emploi.repository.TimeTableRepository;
import com.ensak.emploi.services.TimeTableService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TimeTableController {

    @Autowired
    private TimeTableService timeTableService;
    @Autowired
    private ProgramRepository programRepository;
    @Autowired
    private TimeTableRepository timeTableRepository;

    @PostMapping("/timeTable")
    public TimeTable createtimeTable(@RequestBody TimeTable timeTable) {
        return timeTableService.savetimeTable(timeTable);
    }

    @GetMapping("/counttimeTable/{programId}/{academicYear}")
    public ResponseEntity<Boolean> checkIfExists(@PathVariable Long programId, @PathVariable String academicYear) {
        boolean exists = timeTableService.timeTableExist(programId, academicYear);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/timetable/{programId}/{academicYear}")
    public TimeTable gettimeTableBySemseterYear(@PathVariable Long programId, @PathVariable String academicYear) {
        return timeTableService.getTimeTableByProgramYear(programId, academicYear);
    }

    @GetMapping("/getTimeTable")
    public TimeTable getTimeTable(@RequestParam(value = "timeTableId") Long timeTableId) {
        //get Time table by ID
        //Provide Time table (timeTableID - programID - Semestre ....) to Angular
        return timeTableService.getTimeTable(timeTableId);
    }

//    @GetMapping("/timetable/{programId}/{academicYear}")
//    public TimeTable getTimeTableByProgramAcademicYear(@PathVariable Long programId, @PathVariable String academicYear){
//        return timeTableRepository.findTimeTableByProgramIdAndAcademicYear(programId, academicYear);
//    }
    @PutMapping("/updatetimetable")
    public Long updateTimeTable(@RequestBody TimeTable timeTable) {
        try {
            timeTableService.updateTimeTable(timeTable);
        } catch (Exception e) {
            System.out.println("Exception thrown at updateTimeTable method" + e);
        }
        return timeTable.getId();
    }
    @GetMapping("/timetable")
    public Iterable<TimeTable> getAlltimeTable() {
        return timeTableService.getAllTimeTable();
    }

}
