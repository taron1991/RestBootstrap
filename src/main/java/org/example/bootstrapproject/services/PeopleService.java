package org.example.bootstrapproject.services;

import org.example.bootstrapproject.model.Person;
import org.example.bootstrapproject.repo.PeopleRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

/**
 * Сервис для работы с пользователями.
 */
@Service
@Transactional
public class PeopleService {

    private final PeopleRepository peopleRepository;

    /**
     * Конструктор сервиса.
     *
     * @param peopleRepository Репозиторий для работы с пользователями.
     */
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    /**
     * Загружает пользователя по имени пользователя.
     *
     * @param username Имя пользователя.
     * @return Объект Optional, содержащий информацию о пользователе, если найден.
     * @throws UsernameNotFoundException если пользователь не найден.
     */
    public Optional<Person> loadUserByUsername(String username) throws UsernameNotFoundException {
        return peopleRepository.findByFirstNameWithRoles(username);
    }
    public Optional<Person> findUserByEmail(String email) {
        return peopleRepository.findByEmailWithRoles(email);
    }

}
