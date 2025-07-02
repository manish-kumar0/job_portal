package com.manish.jobportal.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.manish.jobportal.models.jobseeker.Experience;

public interface ExperienceRepository extends MongoRepository<Experience, String> {
    List<Experience> findByUserId(String userId);
}
