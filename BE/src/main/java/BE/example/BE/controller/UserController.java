package BE.example.BE.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import BE.example.BE.domain.User;
import BE.example.BE.service.UserService;
import jakarta.websocket.server.PathParam;

@RestController
public class UserController {

    private final UserService userSevice;

    public UserController(UserService userSevice) {
        this.userSevice = userSevice;
    }

    // GET ALL
    @GetMapping("/user")
    public List<User> getAllUser() {
        return this.userSevice.handleGetUser();
    }

    // GET BY ID
    @GetMapping("/user/{id}")
    public User getByIdUser(@PathVariable long id) {
        return this.userSevice.handleGetByIdUser(id);
    }

    // CREATE
    @PostMapping("/user")
    public User createNewUser(@RequestBody User userPostman) {
        return this.userSevice.handleCreateUser(userPostman);
    }

    // UPDATE
    @PutMapping("/user")
    public User updateUser(@RequestBody User userUpdatePostman) {
        return this.userSevice.handleUpdateUser(userUpdatePostman);
    }

    // DELETE
    @DeleteMapping("/user/{id}")
    public boolean deleteUser(@PathVariable long id) {
        return this.userSevice.handleDeleteUser(id);
    }
}
