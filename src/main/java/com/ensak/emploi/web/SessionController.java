package com.ensak.emploi.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ensak.emploi.model.Session;
import com.ensak.emploi.model.enums.Day;
import com.ensak.emploi.model.enums.Period;
import com.ensak.emploi.repository.ModuleRepository;
import com.ensak.emploi.repository.SessionRepository;
import com.ensak.emploi.services.SessionService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SessionController {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private ModuleRepository moduleRepository;

    @PostMapping("/session")
    public Session createsession(@RequestBody Session session) {
        return sessionService.saveSession(session);
    }

    @GetMapping("/session/{timeTableId}/{day}/{time}")
    public Session getSessionByTimeTableByDayByTime(@PathVariable("timeTableId") Long timeTableId, @PathVariable("day") Day day, @PathVariable("time") Period time) {
        Session session = sessionService.getSessionByTimeTableByDayByTime(timeTableId, day, time);
        return session;
    }

    @GetMapping("/getSessions")
    public List<Session> getSessions(@RequestParam(value = "timeTableId") Long timeTableId) {
        return sessionService.getSessionsByTimeTableId(timeTableId);
    }

    @GetMapping("/getSession")
    public Session getSession(@RequestParam Long session_id, @RequestParam Long timeTableId) {
        return sessionService.getSessionBySessionIdAndTimeTableId(session_id, timeTableId);
    }

    @PutMapping("/updateSession")
    public void updateSession(@RequestBody Session session) {
        try {
            sessionService.updateSession(session);
            System.out.println("Session Updated");
        } catch (Exception e) {
            System.out.println("Exception thrown at updateSession method" + e);
        }
        //return session.getSession_id();
    }

    @DeleteMapping("/deleteSession")
    public Long deleteSession(@RequestBody Session session) {
        try {
            sessionService.deleteSession(session);
        } catch (Exception e) {
            System.out.println("Exception thrown at deleteSession method" + e);
        }
        return session.getTimetableId();
    }

}
