package BE.example.BE.service;

import org.springframework.stereotype.Service;

import BE.example.BE.domain.Company;
import BE.example.BE.repository.CompanyRepository;

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

}
