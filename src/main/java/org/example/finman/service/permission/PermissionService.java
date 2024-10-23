package org.example.finman.service.permission;

public interface PermissionService {
    void grantPermission(long userId, long serviceId);
    void revokePermission(long userId, long serviceId);
}
