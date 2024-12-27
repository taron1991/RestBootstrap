package org.example.bootstrapproject.services;

import org.example.bootstrapproject.model.Person;
import org.example.bootstrapproject.repo.PersonRepository;
import org.example.bootstrapproject.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Optional;

/**
 * Сервис, предоставляющий информацию о пользователях для Spring Security.
 */
@Service
public class PersonDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    /**
     * Конструктор сервиса.
     *
     * @param personRepository Репозиторий для работы с пользователями.
     */
    @Autowired
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Загружает пользователя по его имени для Spring Security.
     *
     * @param email почта пользователя.
     * @return Объект UserDetails, представляющий информацию о пользователе.
     * @throws UsernameNotFoundException если пользователь не найден.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Person> personOptional = personRepository.findByEmailWithRoles(email);

        if (personOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }

        return new PersonDetails(personOptional.get());
    }
}
