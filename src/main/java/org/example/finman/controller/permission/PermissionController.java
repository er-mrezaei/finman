package org.example.finman.controller.permission;

import lombok.RequiredArgsConstructor;
import org.example.finman.service.permission.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    @PostMapping("/grant")
    public ResponseEntity<Void> grantPermission(@RequestBody PermissionRequest request) {
        permissionService.grantPermission(request.userId(), request.serviceId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/revoke")
    public ResponseEntity<Void> revokePermission(@RequestBody PermissionRequest request) {
        permissionService.revokePermission(request.userId(), request.serviceId());
        return ResponseEntity.noContent().build();
    }

    record PermissionRequest(
            Long userId,
            Long serviceId
    ) {
    }
}
