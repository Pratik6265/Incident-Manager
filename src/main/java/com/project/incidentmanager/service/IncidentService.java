package com.project.incidentmanager.service;

import com.project.incidentmanager.model.Incident;
import com.project.incidentmanager.repostory.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    public Incident createIncident(Incident incident) {
        validateIncident(incident);
        return incidentRepository.save(incident);
    }

    public Incident updateIncident(Long id, Incident updatedIncident) {
        Optional<Incident> incidentOpt = incidentRepository.findById(id);
        if (incidentOpt.isPresent()) {
            Incident existingIncident = incidentOpt.get();
            existingIncident.setStatus(updatedIncident.getStatus());
            existingIncident.setNotes(updatedIncident.getNotes());
            return incidentRepository.save(existingIncident);
        } else {
            throw new RuntimeException("Incident not found");
        }
    }

    public List<Incident> listIncidents(String severity, LocalDate startDate, LocalDate endDate) {
        return incidentRepository.findBySeverityAndIncidentDateBetween(severity, startDate, endDate);
    }

    public Incident getIncidentById(Long id) {
        return incidentRepository.findById(id).orElseThrow(() -> new RuntimeException("Incident not found"));
    }

    private void validateIncident(Incident incident) {
        if (!incident.getSeverity().matches("Low|Medium|High")) {
            throw new IllegalArgumentException("Invalid severity level");
        }
        LocalDate now = LocalDate.now();
        if (incident.getIncidentDate().isBefore(now.minusDays(30)) || incident.getIncidentDate().isAfter(now)) {
            throw new IllegalArgumentException("Incident date should be within the past 30 days and not in the future");
        }
        if (incident.getTitle().length() < 10) {
            throw new IllegalArgumentException("Incident title must be at least 10 characters");
        }
    }


}
