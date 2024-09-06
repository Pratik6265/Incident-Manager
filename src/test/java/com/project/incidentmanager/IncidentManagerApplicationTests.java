package com.project.incidentmanager;

import com.project.incidentmanager.model.Incident;
import com.project.incidentmanager.service.IncidentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class IncidentServiceTests {

    @Autowired
    private IncidentService incidentService;

    @Test
    public void testCreateIncidentWithInvalidDate() {
        Incident incident = new Incident();
        incident.setTitle("Incident Title");
        incident.setDescription("Description");
        incident.setSeverity("High");
        incident.setIncidentDate(LocalDate.now().minusDays(31));

        assertThrows(IllegalArgumentException.class, () -> {
            incidentService.createIncident(incident);
        });
    }

    @Test
    public void testInvalidSeverity() {
        Incident incident = new Incident();
        incident.setTitle("Incident Title");
        incident.setDescription("Description");
        incident.setSeverity("Invalid");
        incident.setIncidentDate(LocalDate.now());

        assertThrows(IllegalArgumentException.class, () -> {
            incidentService.createIncident(incident);
        });
    }
}
