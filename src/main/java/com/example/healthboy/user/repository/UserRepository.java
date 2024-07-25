package com.example.healthboy.user.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.healthboy.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @EntityGraph(value = "User.profile", type = EntityGraph.EntityGraphType.LOAD)
    User findByGoogleId(String googleId);

    @EntityGraph(value = "User.profile", type = EntityGraph.EntityGraphType.LOAD)
    User findByFacebookId(String facebookId);

    @EntityGraph(value = "User.profile", type = EntityGraph.EntityGraphType.LOAD)
    User findByGithubId(String githubId);
}
