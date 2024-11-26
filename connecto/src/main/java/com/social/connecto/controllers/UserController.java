package com.social.connecto.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.connecto.models.User;

@RestController
public class UserController {

    @GetMapping("/users")
    public List<User> getUsers() {

        List<User> users = new ArrayList<>();

        User user1 = new User(1, "Sahari", 23);
        User user2 = new User(2, "Suhas", 26);

        users.add(user1);
        users.add(user2);

        return users;

    }

}
