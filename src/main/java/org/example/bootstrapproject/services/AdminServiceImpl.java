package org.example.bootstrapproject.services;

import org.example.bootstrapproject.model.Person;
import org.example.bootstrapproject.repo.PersonRepository;
import org.example.bootstrapproject.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;


import java.util.*;

/**
 * Реализация сервиса для администраторских операций с пользователями.
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Конструктор сервиса.
     *
     * @param personRepository Репозиторий для работы с пользователями.
     * @param roleRepository   Репозиторий для работы с ролями.
     * @param passwordEncoder  кодировка пароля.
     */
    @Autowired
    public AdminServiceImpl(PersonRepository personRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Получает список всех пользователей.
     *
     * @return Список объектов Person.
     */
    @Override
    public List<Person> getAllUsers() {
        return personRepository.findAllWithRoles();
    }

    /**
     * Находит пользователя по имени.
     *
     * @param name Имя пользователя.
     * @return Объект Person, представляющий пользователя.
     * @throws UsernameNotFoundException если пользователь не найден.
     */
    @Override
    public Person findUserByFirstName(String name) {
        Optional<Person> user = personRepository.findByFirstNameWithRoles(name);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User " + name + " not found");
        return user.get();
    }

    public Optional<Person> doesPersonExist(String email) {
        return personRepository.findByEmailWithRoles(email);
    }


    /**
     * Удаляет пользователя по ID.
     *
     * @param id ID пользователя для удаления.
     */
    @Override
    public void removeUser(Long id) {
        personRepository.delete(personRepository.getById(id));
    }

    /**
     * Находит пользователя по ID.
     *
     * @param id ID пользователя.
     * @return Объект Person, представляющий пользователя.
     * @throws UsernameNotFoundException если пользователь не найден.
     */
    @Override
    public Person findOneById(Long id) {
        Optional<Person> user = personRepository.findByPersonIdWithRoles(id);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found");
        return user.get();
    }

    @Override
    public Person findByEmail(String email) {
        Optional<Person> user = personRepository.findByEmailWithRoles(email);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found");
        return user.get();
    }

    public void create(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personRepository.save(person);
    }

    public void updateUser(Person updatedPerson) {

        Person existingPerson = personRepository.findById(updatedPerson.getId())
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + updatedPerson.getId()));

        existingPerson.setAge(updatedPerson.getAge());
        existingPerson.setEmail(updatedPerson.getEmail());
        existingPerson.setFirstName(updatedPerson.getFirstName());
        existingPerson.setLastName(updatedPerson.getLastName());
        existingPerson.setLastName(updatedPerson.getLastName());
        existingPerson.setPassword(passwordEncoder.encode(updatedPerson.getPassword()));
        existingPerson.setRoles(updatedPerson.getRoles());
        personRepository.save(existingPerson);
    }
}
