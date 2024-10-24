package org.example.finman.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.finman.domain.ministration.Ministration;
import org.example.finman.domain.ministration.Permission;
import org.example.finman.repository.ministration.MinistrationRepository;
import org.example.finman.repository.ministration.PermissionRepository;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ServiceAccessAspect {
    private final PermissionRepository permissionRepository;
    private final MinistrationRepository ministrationRepository;

    @Before(value = "execution(* org.example.finman.service.ministration.MinistrationService.useService(..)) && args(userId, serviceId)", argNames = "userId,serviceId")
    public void checkServiceAccess(long userId, long serviceId) {
        Permission permission = permissionRepository.findByUserIdAndMinistrationId(userId, serviceId)
                .orElseThrow(() -> new IllegalStateException("No permission granted for this service"));

        if (!permission.isGranted()) {
            throw new IllegalStateException("Permission is not granted for this service");
        }

        Ministration service = permission.getMinistration();
        if (!service.isActive()) {
            throw new IllegalStateException("Service is not active");
        }
    }
}
