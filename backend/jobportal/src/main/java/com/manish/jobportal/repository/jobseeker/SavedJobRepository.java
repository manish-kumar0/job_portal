package com.manish.jobportal.repository.jobseeker;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.manish.jobportal.models.jobseeker.SavedJob;


public interface SavedJobRepository extends MongoRepository<SavedJob, String> {
    Optional<SavedJob> findByUserId(String userId);
}
