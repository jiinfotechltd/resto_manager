package com.jiinfotech.restomanager.db.user;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(name = "is_email_verified", nullable = false)
    private Boolean isEmailVerified = false;

    @Column(name = "profile_pic", length = 255)
    private String profilePic;

    @Column(name = "profile_bg", length = 255)
    private String profileBg;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "is_trail_user", nullable = false)
    private Boolean isTrailUser = true;

    @Column(length = 15)
    private String contact;

    @Column(length = 15)
    private String contact1;

    @Column(name = "is_contact_verified", nullable = false)
    private Boolean isContactVerified = false;

    @Column(name = "is_enable", nullable = false)
    private Boolean isEnable = true;

    public User() {
    }

    public User(Integer uid, String email, String password, Boolean isEmailVerified, String profilePic, String profileBg, LocalDateTime createdAt, String firstName, String lastName, Boolean isDeleted, LocalDateTime deletedAt, Boolean isTrailUser, String contact, String contact1, Boolean isContactVerified, Boolean isEnable) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.isEmailVerified = isEmailVerified;
        this.profilePic = profilePic;
        this.profileBg = profileBg;
        this.createdAt = createdAt;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
        this.isTrailUser = isTrailUser;
        this.contact = contact;
        this.contact1 = contact1;
        this.isContactVerified = isContactVerified;
        this.isEnable = isEnable;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsEmailVerified() {
        return isEmailVerified;
    }

    public void setIsEmailVerified(Boolean isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getProfileBg() {
        return profileBg;
    }

    public void setProfileBg(String profileBg) {
        this.profileBg = profileBg;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Boolean getIsTrailUser() {
        return isTrailUser;
    }

    public void setIsTrailUser(Boolean isTrailUser) {
        this.isTrailUser = isTrailUser;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContact1() {
        return contact1;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public Boolean getIsContactVerified() {
        return isContactVerified;
    }

    public void setIsContactVerified(Boolean isContactVerified) {
        this.isContactVerified = isContactVerified;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }
}