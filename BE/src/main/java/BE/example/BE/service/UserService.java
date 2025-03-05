package BE.example.BE.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import BE.example.BE.domain.User;
import BE.example.BE.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create
    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    // Get
    public List<User> handleGetUser() {
        return this.userRepository.findAll();
    }

    // Get by id
    // *** C1
    // public Optional<User> handleGetByIdUser(long id) {
    // return this.userRepository.findById(id);
    // }
    // *** C2
    public User handleGetByIdUser(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }

    // update
    public User handleUpdateUser(User user) {

        User existingUser = this.handleGetByIdUser(user.getId());

        if (existingUser != null) {
            existingUser.setName(user.getName()); // Cập nhật từng thuộc tính
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser = this.userRepository.save(existingUser); // Lưu vào DB
        }
        return existingUser;
    }

    // delete
    public boolean handleDeleteUser(long id) {
        if (this.userRepository.existsById(id)) {
            this.userRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
