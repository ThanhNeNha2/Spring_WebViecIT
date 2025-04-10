package BE.example.BE.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import BE.example.BE.Util.annotation.ApiMessage;
import BE.example.BE.Util.error.IdInvalidException;
import BE.example.BE.domain.User;
import BE.example.BE.domain.dto.ResultPaginationDTO;
import BE.example.BE.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // GET ALL
    @ApiMessage("Fetch all user")
    @GetMapping("/users")
    public ResponseEntity<ResultPaginationDTO> getAllUser(
            @Filter Specification<User> spec, Pageable pageable) {

        return ResponseEntity.ok(this.userService.handleGetUser(spec, pageable));
    }

    // Pageable page = PageRequest.of(Integer.parseInt(current) - 1,
    // Integer.parseInt(pageSize));

    // GET BY ID
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getByIdUser(@PathVariable long id) {
        return ResponseEntity.ok(this.userService.handleGetByIdUser(id));
    }

    // CREATE
    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@RequestBody User userPostman) {
        String hassPass = this.passwordEncoder.encode(userPostman.getPassword());
        userPostman.setPassword(hassPass);
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
