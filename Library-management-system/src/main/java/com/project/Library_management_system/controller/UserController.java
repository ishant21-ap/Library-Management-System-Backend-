package com.project.Library_management_system.controller;

import com.project.Library_management_system.dto.UserRequest;
import com.project.Library_management_system.model.Book;
import com.project.Library_management_system.model.User;
import com.project.Library_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addStudent")
    public User addStudent(@RequestBody UserRequest userRequest) {
        return userService.addStudent(userRequest);
    }

    @PostMapping("/addAdmin")
    public User addAdmin(@RequestBody UserRequest userRequest) {
        return null;
    }

    @GetMapping("/filter")
    public List<User> filter(@RequestParam("filterBy") String filterBy,
                             @RequestParam("operator") String operator,
                             @RequestParam("values") String values) {
        return userService.filter(filterBy, operator, values);
    }
}
