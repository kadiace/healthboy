package com.example.healthboy.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.healthboy.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByGoogleId(String googleId);

    User findByFacebookId(String facebookId);

    User findByGithubId(String githubId);
}
