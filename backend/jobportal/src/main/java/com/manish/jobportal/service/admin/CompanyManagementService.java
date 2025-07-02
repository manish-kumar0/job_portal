package com.manish.jobportal.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manish.jobportal.models.recruiter.Company;
import com.manish.jobportal.models.Status;
import com.manish.jobportal.repository.CompanyRepository;

import java.util.List;

@Service
public class CompanyManagementService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company updateCompanyStatus(String companyId, Status newStatus) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        company.setStatus(newStatus);
        return companyRepository.save(company);
    }
}
