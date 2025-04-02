package BE.example.BE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BE.example.BE.domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
