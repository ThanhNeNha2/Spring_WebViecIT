package BE.example.BE.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import BE.example.BE.Util.error.IdInvalidException;
import BE.example.BE.domain.User;
import BE.example.BE.domain.dto.Meta;
import BE.example.BE.domain.dto.ResCreateUserDTO;
import BE.example.BE.domain.dto.ResUpdateUserDTO;
import BE.example.BE.domain.dto.ResUserDTO;
import BE.example.BE.domain.dto.ResultPaginationDTO;
import BE.example.BE.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE
    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    // Check email
    public boolean checkEmailExits(String email) {
        return this.userRepository.existsByEmail(email);
    }

    // responseCreate
    public ResCreateUserDTO convertRes(User user) {
        ResCreateUserDTO resCreate = new ResCreateUserDTO();
        resCreate.setId(user.getId());
        resCreate.setName(user.getName());
        resCreate.setEmail(user.getEmail());
        resCreate.setAge(user.getAge());
        resCreate.setGender(user.getGender());
        resCreate.setAddress(user.getAddress());
        resCreate.setCreatedAt(user.getCreatedAt());
        return resCreate;
    }

    // GET
    public ResultPaginationDTO handleGetUser(Specification<User> specification, Pageable pageable) {
        Page<User> userPage = this.userRepository.findAll(specification, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        Meta meta = new Meta();

        // Trang hiện tại (bắt đầu từ 0 nên cần +1)
        meta.setPage(pageable.getPageNumber() + 1);
        // Số phần tử mỗi trang
        meta.setPageSize(pageable.getPageSize());
        // Tổng số trang
        meta.setPages(userPage.getTotalPages());
        // Tổng số phần tử
        meta.setTotal(userPage.getTotalElements());

        rs.setMeta(meta);

        List<ResUserDTO> listUserDto = userPage.getContent().stream()
                .map(item -> new ResUserDTO(
                        item.getId(),
                        item.getName(),
                        item.getEmail(),
                        item.getAge(),
                        item.getGender(),
                        item.getAddress(),
                        item.getCreatedAt(),
                        item.getUpdatedAt()))
                .collect(Collectors.toList());

        rs.setResult(listUserDto);
        return rs;
    }

    // GET BY ID

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

    // responseGetById
    public ResUserDTO convertResGetById(User user) {
        ResUserDTO resGetById = new ResUserDTO();
        resGetById.setId(user.getId());
        resGetById.setName(user.getName());
        resGetById.setEmail(user.getEmail());
        resGetById.setAge(user.getAge());
        resGetById.setGender(user.getGender());
        resGetById.setAddress(user.getAddress());
        resGetById.setUpdatedAt(user.getUpdatedAt());
        resGetById.setCreatedAt(user.getCreatedAt());
        return resGetById;
    }

    // UPDATE
    public User handleUpdateUser(User user) {
        User existingUser = this.handleGetByIdUser(user.getId());
        if (existingUser != null) {
            existingUser.setName(user.getName()); // Cập nhật từng thuộc tính
            existingUser.setAge(user.getAge()); // Cập nhật từng thuộc tính
            existingUser.setGender(user.getGender()); // Cập nhật từng thuộc tính
            existingUser.setAddress(user.getAddress()); // Cập nhật từng thuộc tính
            existingUser = this.userRepository.save(existingUser); // Lưu vào DB
        }
        return existingUser;
    }

    // responseUpdate
    public ResUpdateUserDTO convertResUpdate(User user) {
        ResUpdateUserDTO resUpdate = new ResUpdateUserDTO();
        resUpdate.setId(user.getId());
        resUpdate.setName(user.getName());
        resUpdate.setAge(user.getAge());
        resUpdate.setGender(user.getGender());
        resUpdate.setAddress(user.getAddress());
        resUpdate.setUpdatedAt(user.getUpdatedAt());
        return resUpdate;
    }

    // DELETE
    public boolean handleDeleteUser(long id) {
        if (this.userRepository.existsById(id)) {
            this.userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Tìm người dùng thông qua email
    public User HandleGetUserByUserName(String username) {
        return this.userRepository.findByEmail(username);
    }

}
