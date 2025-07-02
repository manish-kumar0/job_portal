package com.manish.jobportal.repository.jobseeker;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.manish.jobportal.models.jobseeker.Profile;

public interface ProfileRepository extends MongoRepository<Profile, String> {
    boolean existsByUserId(String userId);
    Optional<Profile> findByUserId(String userId);
}
