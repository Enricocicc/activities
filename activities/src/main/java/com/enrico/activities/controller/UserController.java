package com.enrico.activities.controller;

import com.enrico.activities.model.Activity;
import com.enrico.activities.model.User;
import com.enrico.activities.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    // build Create User REST API
    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.CREATED);

    }

    // build GetAll Users REST API
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // build Get User by Id
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        return new ResponseEntity<User>(userService.getUserById(id), HttpStatus.OK);
    }

    // build Update User REST API
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") long id){
        return new ResponseEntity<User>(userService.updateUser(user, id), HttpStatus.OK);
    }

    // build Delete User REST API
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return new ResponseEntity<String>("User deleted successfully!", HttpStatus.OK);
    }

    @GetMapping("/users/{id}/activities")
    public List<Activity> getAllActivitiesByUserId(@PathVariable("id") long id) {
        return userService.getAllActivitiesByUserId(id);
    }
    @PutMapping("/users/{userId}/register/{activityId}")
    public ResponseEntity<String> registerToActivity(@PathVariable("userId") long userId,
                                                     @PathVariable("activityId") long activityId) {
        return new ResponseEntity<String>(userService.registerToActivity(userId, activityId), HttpStatus.OK);
    }

    @PutMapping("/users/{userId}/cancel/{activityId}")
    public ResponseEntity<String> cancelFromActivity(@PathVariable("userId") long userId,
                                                     @PathVariable("activityId") long activityId) {
        return new ResponseEntity<String>(userService.cancelFromActivity(userId, activityId), HttpStatus.OK);
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/process_register")
    public String processRegister(@RequestBody User user) {
        userService.saveUser(user);
        return "register_success";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
