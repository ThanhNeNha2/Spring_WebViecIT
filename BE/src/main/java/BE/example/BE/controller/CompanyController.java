package BE.example.BE.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import BE.example.BE.domain.Company;
import BE.example.BE.service.CompanyService;
import jakarta.validation.Valid;

@RestController
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // CREATE
    @PostMapping("/company")
    public ResponseEntity<Company> createNewUser(@Valid @RequestBody Company companyPostman) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.companyService.handleCreateUser(companyPostman));
    }
}
