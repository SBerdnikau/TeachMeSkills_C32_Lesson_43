package com.tms.controller;

import com.tms.exception.CreateException;
import com.tms.exception.DeleteException;
import com.tms.exception.GlogalExceptionHandler;
import com.tms.exception.UpdateException;
import com.tms.model.User;
import com.tms.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create")
    public String getUserCreatePage() {
        return "createUser";
    }

    @GetMapping("/edit/{id}")
    public User getUserUpdatePage(@PathVariable("id") Long userId, HttpServletResponse response) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); //404
           // return "innerError";
        }
        return user.get();
    }

    //Create
    @PostMapping
    public User createUser(@RequestBody User user, HttpServletResponse response) throws CreateException {
        Optional<User> createdUser = userService.createUser(user);
        if (createdUser.isEmpty()) {
            throw  new CreateException("User not created");
        }
        response.setStatus(HttpServletResponse.SC_CREATED);
        return createdUser.get();
    }

    //Read
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long userId, HttpServletResponse response) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); //404
        }
        response.setStatus(HttpServletResponse.SC_OK); //200
        return user.get();
    }

    //Update
    @PutMapping
    public User updateUser(@RequestBody User user) throws UpdateException {
        Optional<User> userUpdated = userService.updateUser(user);
        if (userUpdated.isEmpty()) {
          throw new UpdateException("User is not updated");
        }
        return user;
    }

    //Delete
    @DeleteMapping("/{id}")
    public HttpStatus deleteUser(@PathVariable("id") Long userId, HttpServletResponse response) throws DeleteException {
        Optional<User> userDeleted = userService.deleteUser(userId);
        if (userDeleted.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            throw new DeleteException("Cannot be deleted user with Id: " + userId);
        }
        return HttpStatus.OK;
    }

    //getAll
    @GetMapping("/all-users")
    public List<User> getUserListPage(HttpServletResponse response) {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            //return "innerError";
        }
        response.setStatus(HttpServletResponse.SC_OK);
        return users;
    }
}
