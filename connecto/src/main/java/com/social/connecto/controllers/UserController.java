package com.social.connecto.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.connecto.models.User;
import com.social.connecto.servicelayers.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public void registerUser(@RequestBody User user) {
        userService.registerUser(user);
    }

    @GetMapping("/findById/{userId}")
    public Optional<User> findUserById(@PathVariable int userId) {
        return userService.findUserById(userId);
    }

    @GetMapping("/findByEmail/{email}")
    public Optional<User> findUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }

    @PutMapping("/follow/{userid1}/{userid2}")
    public Optional<User> followUser(@PathVariable int userid1, @PathVariable int userid2) {
        return userService.followUser(userid1, userid2);
    }

    @GetMapping("/search/{query}")
    public List<User> searchUser(@PathVariable String query) {
        List<User> users = userService.searchUser(query);
        return users;
    }

    @PutMapping("/update")
    public User update(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping("/findAll")
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @DeleteMapping("/delete/{userid}")
    public void deleteUser(@PathVariable int userid) {
        userService.deleteUser(userid);
    }

}
