package com.epam.training.ticketservice.configuration;

import com.epam.training.ticketservice.user.persistence.entity.User;
import com.epam.training.ticketservice.user.persistence.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Profile("!prod")
public class InMemoryDatabaseInitializer {


    private final UserRepository userRepository;

    public InMemoryDatabaseInitializer(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {

        User admin = new User("admin", "admin", User.Role.ADMIN);
        userRepository.save(admin);
    }
}
