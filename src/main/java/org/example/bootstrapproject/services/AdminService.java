package org.example.bootstrapproject.services;


import org.example.bootstrapproject.model.Person;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<Person> getAllUsers();

    Person findUserByFirstName(String firstName);

    void updateUser(Person person);

    void removeUser(Long id);

    Person findOneById(Long id);

    Person findByEmail(String email);

    void create(Person person);

    Optional<Person> doesPersonExist(String emil);
}