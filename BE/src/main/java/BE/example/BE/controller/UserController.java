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
import BE.example.BE.domain.dto.ResCreateUserDTO;
import BE.example.BE.domain.dto.ResUpdateUserDTO;
import BE.example.BE.domain.dto.ResUserDTO;
import BE.example.BE.domain.dto.ResultPaginationDTO;
import BE.example.BE.service.UserService;
import jakarta.validation.Valid;

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

    // GET BY ID
    @GetMapping("/users/{id}")
    public ResponseEntity<ResUserDTO> getByIdUser(@PathVariable("id") long id) throws IdInvalidException {
        User existingUser = this.userService.handleGetByIdUser(id);
        if (existingUser == null) {
            throw new IdInvalidException("ID " + id + " không tồn tại !");
        }
        return ResponseEntity.ok(this.userService.convertResGetById(existingUser));
    }

    // CREATE
    @PostMapping("/users")
    @ApiMessage("Create user")
    public ResponseEntity<ResCreateUserDTO> createNewUser(@Valid @RequestBody User userPostman)
            throws IdInvalidException {
        if (this.userService.checkEmailExits(userPostman.getEmail())) {
            throw new IdInvalidException("Email " + userPostman.getEmail() + " đã tồn tại !");
        }
        String hassPass = this.passwordEncoder.encode(userPostman.getPassword());
        userPostman.setPassword(hassPass);
        User user = this.userService.handleCreateUser(userPostman);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertRes(user));
    }

    // UPDATE
    @PutMapping("/users")
    public ResponseEntity<ResUpdateUserDTO> updateUser(@RequestBody User userUpdatePostman) throws IdInvalidException {
        User existingUser = this.userService.handleGetByIdUser(userUpdatePostman.getId());
        if (existingUser == null) {
            throw new IdInvalidException("ID " + userUpdatePostman.getId() + " không tồn tại !");
        }
        User userupdate = this.userService.handleUpdateUser(userUpdatePostman);
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.convertResUpdate(userupdate));
    }

    // DELETE
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) throws IdInvalidException {
        boolean deleted = this.userService.handleDeleteUser(id);

        if (deleted) {
            return ResponseEntity.ok("Xóa thành công");
        } else {
            throw new IdInvalidException("ID " + id + " không tồn tại !");
        }
    }

}
