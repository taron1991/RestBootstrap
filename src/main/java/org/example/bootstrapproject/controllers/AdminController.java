package org.example.bootstrapproject.controllers;

import org.example.bootstrapproject.model.Person;
import org.example.bootstrapproject.model.Role;
import org.example.bootstrapproject.services.AdminService;
import org.example.bootstrapproject.services.RoleService;
import org.example.bootstrapproject.validators.PersonValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/api/admin")
public class AdminController {


    private final AdminService adminService;
    private final RoleService roleService;
    private final PersonValidator personValidator;

    public AdminController(AdminService adminService, RoleService roleService, PersonValidator personValidator) {
        this.adminService = adminService;
        this.roleService = roleService;
        this.personValidator = personValidator;
    }


    @GetMapping("/showAccount")
    public ResponseEntity<Person> showInfoUser(Principal principal) {
        System.out.println(principal.getName());
        return new ResponseEntity<>(adminService.findByEmail(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<Person>> getAllUsers() {
        return new ResponseEntity<>(adminService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Person> getUserById(@PathVariable("id") Long id) {
        Person person = adminService.findOneById(id);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<Collection<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.getRoles(), HttpStatus.OK);
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<Collection<Role>> getRole(@PathVariable("id") Long id) {
        return new ResponseEntity<>(adminService.findOneById(id).getRoles(), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<?> addNewUser(@RequestBody @Valid Person newUser, BindingResult bindingResult) {

        personValidator.validate(newUser, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }

        adminService.create(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid Person updatedPerson, BindingResult bindingResult) {

        personValidator.validate(updatedPerson, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }

        adminService.updateUser(updatedPerson);
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        adminService.removeUser(id);
        return new ResponseEntity<>("User with id " + id + " was deleted", HttpStatus.OK);
    }
}
