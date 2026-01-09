package com.triple_stack.route_in_backend.batch;

import com.triple_stack.route_in_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WithdrawScheduler {
    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteExpiredUser() {
        userRepository.deleteUser();
    }
}
