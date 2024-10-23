package org.example.finman.controller.ministration;

import lombok.RequiredArgsConstructor;
import org.example.finman.dto.ministration.MinistrationDto;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")

    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activateMinistration(@PathVariable Long id, @RequestBody ActivateMinistration request) {
        ministrationService.setMinistrationActive(id, request.isActive());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/revoke-permission")
    public ResponseEntity<Void> revokePermission(@RequestBody RevokePermission request) {
        ministrationService.revokePermission(request.permissionId, request.userId);
        return ResponseEntity.noContent().build();
    }

    record RevokePermission(long userId, long permissionId) {
    }

    record ActivateMinistration(
            boolean isActive
    ) {
    }
}
