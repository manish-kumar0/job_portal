package com.manish.jobportal.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.manish.jobportal.models.User;
import com.manish.jobportal.models.jobseeker.Certificate;

public interface CertificateRepository extends MongoRepository<Certificate, String> {
    List<Certificate> findByUser(User user);
}
