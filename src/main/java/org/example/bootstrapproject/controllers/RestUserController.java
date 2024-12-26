package org.example.bootstrapproject.controllers;

import org.example.bootstrapproject.model.Person;
import org.example.bootstrapproject.services.PeopleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class RestUserController {

    private final PeopleService peopleService;

    public RestUserController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }


    @GetMapping("/showAccount")
    public ResponseEntity<Person> showUserAccount(Principal principal) {
        System.out.println();
        Person person = peopleService.findUserByEmail(principal.getName()).get();
        System.out.println();
        return new ResponseEntity<>(person, HttpStatus.OK);
    }
}
