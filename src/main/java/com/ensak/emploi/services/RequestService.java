package com.ensak.emploi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensak.emploi.model.Request;
import com.ensak.emploi.repository.RequestRepository;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    public Request saveRequest(Request request) {
        return requestRepository.save(request);
    }

    // Get a specific request by ID
    //public Optional<Request> getRequestById(int id) {
    //  return requestRepository.findById(id);
    //}
    public Request getRequestById(Long requestId) {
        // Fetch request from the repository
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
    }

    public List<Request> getRequestsByProfessorId(Long professorId) {
        List<Request> allRequests = requestRepository.findAll();
        // Filter requests by professorId
        return allRequests.stream()
                .filter(request -> request.getProfessorId() == professorId)
                .toList();
    }

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    @Autowired
    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public List<Request> getRequestsByProgramId(Long programId) {
        return requestRepository.findRequestsByProgramId(programId);
    }

    // Update an existing request
    public Request updateRequest(Long id, Request updatedRequest) {
        Optional<Request> existingRequest = requestRepository.findById(id);
        if (existingRequest.isPresent()) {
            updatedRequest.setRequestId(id);
            return requestRepository.save(updatedRequest);
        }
        throw new RuntimeException("Request with ID " + id + " not found.");
    }

    public void updateRequestStatus(Long requestId, String newStatus) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found with ID: " + requestId));

        request.setStatus(newStatus);

        requestRepository.save(request);
    }

    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }

    public void updateClassId(Long requestId, Long classId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        request.setClassId(classId);

        requestRepository.save(request);
    }

    
    public List<Request> getRequestsByProgramNameAndSemesterId(String programName, Long semesterId) {
        return requestRepository.findByProgramNameAndSemesterId(programName, semesterId);
    }
    

}
