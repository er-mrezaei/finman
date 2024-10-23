package org.example.finman.repository.ministration;

import org.example.finman.domain.ministration.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}