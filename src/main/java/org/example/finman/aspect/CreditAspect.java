package org.example.finman.aspect;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.example.finman.domain.ministration.Ministration;
import org.example.finman.domain.user.SimpleUser;
import org.example.finman.repository.ministration.MinistrationRepository;
import org.example.finman.repository.user.UserRepository;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CreditAspect {
    private final UserRepository userRepository;
    private final MinistrationRepository ministrationRepository;

    @AfterReturning(pointcut = "execution(* org.example.finman.service.ministration.MinistrationService.useService(..)) && args(userId, serviceId)", argNames = "userId,serviceId")
    public void reduceCreditAfterServiceUse(Long userId, Long serviceId) {
        SimpleUser user = (SimpleUser) userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Ministration service = ministrationRepository.findById(serviceId)
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));

        if (user.getCredit() >= service.getCostPerUse()) {
            user.setCredit(user.getCredit() - service.getCostPerUse());
            userRepository.save(user);
        } else {
            throw new IllegalStateException("Insufficient credit");
        }
    }
}
