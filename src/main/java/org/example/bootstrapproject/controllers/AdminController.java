package org.example.bootstrapproject.controllers;

import org.example.bootstrapproject.model.Person;
import org.example.bootstrapproject.services.AdminService;
import org.example.bootstrapproject.services.UserDataValidationService;
import org.example.bootstrapproject.validators.MyDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger LOGGER = LogManager.getLogger(AdminController.class.getName());

    private final AdminService adminService;
    private final MyDataValidator myDataValidator;
    private final UserDataValidationService userDataValidationService;


    @Autowired
    public AdminController(AdminService adminService, MyDataValidator myDataValidator, UserDataValidationService userDataValidationService) {
        this.adminService = adminService;
        this.myDataValidator = myDataValidator;
        this.userDataValidationService = userDataValidationService;
    }


    @GetMapping()
    public String showAllUsers(Model model, Principal principal) {
        Person person = adminService.findByEmail(principal.getName());
        model.addAttribute("currentUser", person);
        List<Person> listOfUsers = adminService.getAllUsers();
        model.addAttribute("listOfUsers", listOfUsers);
        model.addAttribute("person", new Person());
        model.addAttribute("allErrors", myDataValidator.getAllErrorsAsString());
        return "admin/users";
    }


    @GetMapping("/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("person", new Person());
        return "/admin/new";
    }


    @PostMapping("")
    public String createUser(@ModelAttribute @Valid Person user,
                             @RequestParam(value = "roles", required = false)
                             @Valid List<String> roles,
                             Model model) {

        String allErrors = userDataValidationService.validateUserData(user, roles, model);

        if (!allErrors.isEmpty()) {
            return "redirect:/admin";
        }
        adminService.create(user, roles);
        return "redirect:/admin";
    }



    @PostMapping("/user/edit")
    public String update(@ModelAttribute("person") @Valid Person person,
                         @RequestParam(value = "role", required = false) @Valid List<String> role,
                         Model model) {

        LOGGER.info(person.getPassword());
        LOGGER.info(person.getEmail());
        String allErrors = userDataValidationService.validateUserData(person, role, model);


        if (!allErrors.isEmpty()) {
            return "redirect:/admin";
        }

        adminService.updateUser(person, role);
        return "redirect:/admin";
    }

    @PostMapping("/user/delete")
    public String delete(@RequestParam Long id) {
        adminService.removeUser(id);
        return "redirect:/admin";
    }
}
