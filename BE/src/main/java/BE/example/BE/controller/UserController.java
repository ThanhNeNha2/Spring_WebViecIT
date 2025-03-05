package BE.example.BE.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import BE.example.BE.domain.User;
import BE.example.BE.service.UserService;

@RestController
public class UserController {

    private final UserService userSevice;

    public UserController(UserService userSevice) {
        this.userSevice = userSevice;
    }

    // CREATE
    @PostMapping("/user")
    public User createNewUser(@RequestBody User userPostman) {
        return this.userSevice.handleCreateUser(userPostman);
    }

    // DELETE
    @DeleteMapping("/user/{id}")
    public boolean deleteUser(@PathVariable long id) {
        return this.userSevice.handleDeleteUser(id);

    }
}
