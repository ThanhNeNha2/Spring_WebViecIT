package BE.example.BE.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import BE.example.BE.domain.Company;
import BE.example.BE.service.CompanyService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // CREATE
    @PostMapping("/companies")
    public ResponseEntity<Company> createNewUser(@Valid @RequestBody Company companyPostman) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.companyService.handleCreateUser(companyPostman));
    }

    // GET
    @GetMapping("/companies")
    public ResponseEntity<List<Company>> getAllCompany() {
        return ResponseEntity.ok(this.companyService.handleGetAllCompanies());
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("id") long id) {
        Optional<Company> company = this.companyService.handleGetByIdCompany(id);

        if (company.isPresent()) {
            return ResponseEntity.ok(company.get()); // Return 200 OK with the company data
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 Not Found if company is not found
        }
    }

    // UPDATE
    @PutMapping("/companies")
    public ResponseEntity<Company> putMethodName(@RequestBody Company companyPostman) {
        return ResponseEntity.ok(this.companyService.handleUpdateCompany(companyPostman));
    }

    // DELETE
    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Boolean> deleteCompany(@PathVariable("id") long id) { // ✅ Thêm "id"
        boolean isDeleted = this.companyService.handleDeleteCompany(id);

        if (isDeleted) {
            return ResponseEntity.ok(true); // Thành công
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false); // Không tìm thấy
        }
    }

}
