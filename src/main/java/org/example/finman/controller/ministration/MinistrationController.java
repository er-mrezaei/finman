package org.example.finman.controller.ministration;

import lombok.RequiredArgsConstructor;
import org.example.finman.dto.ministration.MinistrationDto;
import org.example.finman.dto.ministration.ServiceUsageReportDto;
import org.example.finman.service.ministration.MinistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ministrations")
@RequiredArgsConstructor
public class MinistrationController {

    private final MinistrationService ministrationService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<MinistrationDto> getAllMinistrations() {
        return ministrationService.getAllMinistrations();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public MinistrationDto getMinistrationById(@PathVariable Long id) {
        return ministrationService.getMinistrationById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public MinistrationDto createMinistration(@RequestBody MinistrationDto ministrationDto) {
        return ministrationService.createMinistration(ministrationDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public MinistrationDto updateMinistration(@PathVariable Long id, @RequestBody MinistrationDto ministrationDto) {
        return ministrationService.updateMinistration(id, ministrationDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMinistration(@PathVariable Long id) {
        ministrationService.deleteMinistration(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activate")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> activateMinistration(@PathVariable Long id, @RequestBody ActivateMinistration request) {
        ministrationService.setMinistrationActive(id, request.isActive());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/report")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<ServiceUsageReportDto> getServiceUsageReport(@PathVariable Long id) {
        return ministrationService.getServiceUsageReport(id);
    }

    @GetMapping("/report/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<ServiceUsageReportDto> getAllServiceUsageReports() {
        return ministrationService.getAllServiceUsageReports();
    }

    record RevokePermission(long userId, long permissionId) {
    }

    record ActivateMinistration(
            boolean isActive
    ) {
    }
}
