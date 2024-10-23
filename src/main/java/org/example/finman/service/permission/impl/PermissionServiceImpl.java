package org.example.finman.service.permission.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.finman.domain.ministration.Ministration;
import org.example.finman.domain.ministration.Permission;
import org.example.finman.domain.user.SimpleUser;
import org.example.finman.repository.ministration.MinistrationRepository;
import org.example.finman.repository.ministration.PermissionRepository;
import org.example.finman.repository.user.SimpleUserRepository;
import org.example.finman.service.permission.PermissionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final SimpleUserRepository simpleUserRepository;
    private final MinistrationRepository ministrationRepository;

    @Override
    public void grantPermission(long userId, long serviceId) {
        SimpleUser user = (SimpleUser) simpleUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Ministration service = ministrationRepository.findById(serviceId)
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));

        Permission permission = new Permission();
        permission.setUser(user);
        permission.setMinistration(service);
        permission.setGranted(true);
        permission.setUsageCount(0);
        permissionRepository.save(permission);
    }

    @Override
    public void revokePermission(long userId, long serviceId) {
        Permission permission = permissionRepository.findByUserIdAndMinistrationId(userId, serviceId)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found"));
        permission.setGranted(false);
        permissionRepository.save(permission);
    }
}

