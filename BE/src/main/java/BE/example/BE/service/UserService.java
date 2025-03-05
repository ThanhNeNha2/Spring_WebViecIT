package BE.example.BE.service;

import org.springframework.stereotype.Service;

import BE.example.BE.domain.User;
import BE.example.BE.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    public boolean handleDeleteUser(long id) {
        if (this.userRepository.existsById(id)) {
            this.userRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
