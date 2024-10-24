package org.example.finman.repository.ministration;

import org.example.finman.domain.ministration.Ministration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MinistrationRepository extends JpaRepository<Ministration, Long> {
    List<Ministration> findByIsActiveTrue();
}