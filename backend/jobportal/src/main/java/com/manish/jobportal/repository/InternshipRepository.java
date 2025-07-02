package com.manish.jobportal.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.manish.jobportal.models.recruiter.Internship;

public interface InternshipRepository extends MongoRepository<Internship, String> {
    // findAllByRecruiterId
    List<Internship> findAllByRecruiterId(String recruiterId);
}
