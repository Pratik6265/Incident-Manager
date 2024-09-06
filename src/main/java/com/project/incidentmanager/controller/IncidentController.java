package com.project.incidentmanager.controller;

import jakarta.servlet.http.HttpServletRequest;
import  org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.incidentmanager.model.Incident;
import com.project.incidentmanager.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    private static final Logger logger = LoggerFactory.getLogger(IncidentController.class);

    @Autowired
    private IncidentService incidentService;

    @PostMapping
    public Incident createIncident(@RequestBody Incident incident) {
        return incidentService.createIncident(incident);
    }

    @PutMapping("/{id}")
    public Incident updateIncident(@PathVariable Long id, @RequestBody Incident incident) {
        return incidentService.updateIncident(id, incident);
    }

    @GetMapping
    public List<Incident> listIncidents(
            @RequestParam(required = false) String severity,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        return incidentService.listIncidents(severity, startDate, endDate);
    }

    @GetMapping("/{id}")
    public Incident getIncident(@PathVariable Long id) {
        return incidentService.getIncidentById(id);
    }

    @PostMapping("with-logs")
    public Incident createIncident(@RequestBody Incident incident, HttpServletRequest request) {
        logRequestDetails(request);
        return incidentService.createIncident(incident);
    }

    private void logRequestDetails(HttpServletRequest request) {
        logger.info("Request - {} {} from IP: {}", request.getMethod(), request.getRequestURI(), request.getRemoteAddr());
    }
}
