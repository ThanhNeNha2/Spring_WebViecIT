package BE.example.BE.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import BE.example.BE.domain.User;
import BE.example.BE.service.UserService;
import BE.example.BE.service.error.IdInvalidException;
import jakarta.websocket.server.PathParam;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET ALL
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok(this.userService.handleGetUser());
    }

    // GET BY ID
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getByIdUser(@PathVariable long id) {
        return ResponseEntity.ok(this.userService.handleGetByIdUser(id));
    }

    // CREATE
    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@RequestBody User userPostman) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.handleCreateUser(userPostman));
    }

    // UPDATE
    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User userUpdatePostman) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.handleUpdateUser(userUpdatePostman));

    }

    // DELETE
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) throws IdInvalidException {
        if (id >= 1500) {
            throw new IdInvalidException("Id không đúng định dạng ");
        }
        boolean deleted = this.userService.handleDeleteUser(id);

        if (deleted) {
            return ResponseEntity.noContent().build(); // Trả về 204 No Content nếu xoá thành công
        } else {
            return ResponseEntity.notFound().build(); // Trả về 404 Not Found nếu không tìm thấy người dùng
        }
    }
}
