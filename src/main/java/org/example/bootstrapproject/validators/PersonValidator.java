package org.example.bootstrapproject.validators;

import org.example.bootstrapproject.model.Person;
import org.example.bootstrapproject.services.AdminService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import java.util.Optional;

@Component
public class PersonValidator implements Validator {


    private final AdminService adminService;

    public PersonValidator(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        Optional<Person> existingPerson = adminService.doesPersonExist(person.getEmail());

        if (existingPerson.isPresent()  && person.getId() != existingPerson.get().getId()) {
            String errMsg = String.format("Email %s is not unique", existingPerson.get().getEmail());
            errors.rejectValue("email", "duplicate.email", errMsg);
        }

        if (person.getAge() != null && person.getAge() < 0) {
            errors.rejectValue("age", "negative.number", "Age must be a non-negative number");
        }

        if (person.getFirstName() == null || person.getFirstName().trim().isEmpty()) {
            errors.rejectValue("firstName", "NotEmpty", "FirstName should not be empty");
        }

        if (person.getLastName() == null || person.getLastName().trim().isEmpty()) {
            errors.rejectValue("lastName", "NotEmpty", "LastName should not be empty");
        }

        if (person.getRoles() == null || person.getRoles().isEmpty()) {
            errors.rejectValue("roles", "NotEmpty", "The role should not be empty");
        }

    }
}
