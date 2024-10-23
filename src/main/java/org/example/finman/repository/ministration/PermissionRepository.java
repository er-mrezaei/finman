package org.example.finman.repository.ministration;

import org.example.finman.domain.ministration.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByUserIdAndMinistrationId(long userId, long ministrationId);
}