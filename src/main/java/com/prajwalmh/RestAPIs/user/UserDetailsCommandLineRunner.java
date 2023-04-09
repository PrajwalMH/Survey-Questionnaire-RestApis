package com.prajwalmh.RestAPIs.user;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner {

    public UserDetailsCommandLineRunner(UserDetailsRepository repository) {
        super();
        this.repository = repository;
    }

    private Logger logger= LoggerFactory.getLogger(getClass());

    private UserDetailsRepository repository;


    @Override
    public void run(String... args) throws Exception {
        repository.save(new UserDetails("root","Admin"));
        repository.save(new UserDetails("prajwal","Admin"));
        repository.save(new UserDetails("ram","User"));

        List<UserDetails> users = repository.findByRole("Admin");

        users.forEach(user -> logger.info(user.toString()));
    }
}
