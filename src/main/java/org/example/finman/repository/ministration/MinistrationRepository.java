package org.example.finman.repository.ministration;

import org.example.finman.domain.ministration.Ministration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MinistrationRepository extends JpaRepository<Ministration, Long> {
}