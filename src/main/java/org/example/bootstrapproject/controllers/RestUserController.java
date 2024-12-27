package org.example.bootstrapproject.controllers;

import org.example.bootstrapproject.model.Person;
import org.example.bootstrapproject.services.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class RestUserController {

    private final PersonService personService;

    public RestUserController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping("/showAccount")
    public ResponseEntity<Person> showUserAccount(Principal principal) {
        System.out.println();
        Person person = personService.findUserByEmail(principal.getName()).get();
        System.out.println();
        return new ResponseEntity<>(person, HttpStatus.OK);
    }
}
