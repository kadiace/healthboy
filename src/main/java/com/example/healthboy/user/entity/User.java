package com.example.healthboy.user.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.http.HttpStatus;

import com.example.healthboy.common.ApplicationException;
import com.example.healthboy.common.enums.SSOType;

import jakarta.persistence.*;

@Entity
@SQLDelete(sql = "UPDATE user SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@NamedEntityGraph(name = "User.profile", attributeNodes = @NamedAttributeNode("profile"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true)
    private String googleId;

    @Column(unique = true)
    private String facebookId;

    @Column(unique = true)
    private String githubId;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Profile profile;

    private LocalDateTime deletedAt;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTokenId(SSOType ssoType) {
        switch (ssoType) {
            case GOOGLE:
                return googleId;

            case FACEBOOK:
                return facebookId;

            case GITHUB:
                return githubId;
        }
        throw new ApplicationException("Invalid SSO Type", HttpStatus.BAD_REQUEST);
    }

    public void setTokenId(SSOType ssoType, String tokenId) {
        switch (ssoType) {
            case GOOGLE:
                this.googleId = tokenId;
                break;

            case FACEBOOK:
                this.facebookId = tokenId;
                break;

            case GITHUB:
                this.githubId = tokenId;
                break;
            default:
                break;
        }
    }

    public String getGoogleId() {
        return googleId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public String getGithubId() {
        return githubId;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    // Original Method
    public boolean isValid() {
        return this.email != null && !this.email.isEmpty() && this.profile != null && this.profile.isValid()
                && (this.googleId != null || this.facebookId != null || this.githubId != null);
    }
}
