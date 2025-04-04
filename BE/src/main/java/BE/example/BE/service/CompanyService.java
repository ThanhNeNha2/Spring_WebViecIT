package BE.example.BE.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import BE.example.BE.domain.Company;
import BE.example.BE.repository.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    // Create
    public Company handleCreateUser(Company company) {
        return this.companyRepository.save(company);
    }

    // GET
    public List<Company> handleGetAllCompanies() {
        return this.companyRepository.findAll();
    }

    // GET BY ID
    public Optional<Company> handleGetByIdCompany(long id) {
        return this.companyRepository.findById(id);
    }

    // UPDATE
    public Company handleUpdateCompany(Company company) {
        Optional<Company> exitCompany = this.companyRepository.findById(company.getId());
        if (exitCompany.isPresent()) {
            Company updatedCompany = exitCompany.get();
            updatedCompany.setName(company.getName());
            updatedCompany.setDescription(company.getDescription());
            updatedCompany.setAddress(company.getAddress());
            updatedCompany.setLogo(company.getLogo());
            return this.companyRepository.save(updatedCompany);
        } else {
            throw new EntityNotFoundException("Company with ID " + company.getId() + " not found.");
        }
    }

    // DELETE
    public boolean handleDeleteCompany(long id) {
        if (this.companyRepository.existsById(id)) {
            this.companyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Tìm ngư
}
