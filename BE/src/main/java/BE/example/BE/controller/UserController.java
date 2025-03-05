package BE.example.BE.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/user/create")
    public String CreateNewUser() {
        return "create user";
    }
}
