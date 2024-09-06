package com.project.incidentmanager.repostory;

import com.project.incidentmanager.model.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IncidentRepository extends JpaRepository<Incident,Long> {
    List<Incident> findBySeverityAndIncidentDateBetween(String severity, LocalDate startDate, LocalDate endDate);

}
