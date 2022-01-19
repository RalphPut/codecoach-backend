package com.switchfully.teamair.codecoach.domain.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    UUID userId;
    @Column(name = "email")
    String email;
    @Column(name = "password")
    String password;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;
    @Column(name = "company")
    String company;
    @Column(name = "image_url")
    String imageUrl;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id_fk"),
            inverseJoinColumns = @JoinColumn(name = "role_id_fk"))
    private Set<Role> roles;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "coach_details_id")
    CoachDetails coachDetails;

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        roles.remove(role);
    }

    public User setEmail(@Email String email) {
        if (email == null) {
            return this;
        }
        this.email = email;
        return this;
    }

    public User setPassword(String password) {
        if (password == null) {
            return this;
        }
        this.password = password;
        return this;
    }

    public User setFirstName(String firstName) {
        if (firstName == null) {
            return this;
        }
        this.firstName = firstName;
        return this;
    }

    public User setLastName(String lastName) {
        if (lastName == null) {
            return this;
        }
        this.lastName = lastName;
        return this;
    }

    public User setCompany(String company) {
        if (company == null) {
            return this;
        }
        this.company = company;
        return this;
    }

    public User setImageUrl(String imageUrl) {
        if (imageUrl == null) {
            return this;
        }
        this.imageUrl = imageUrl;
        return this;
    }

    public User setCoachDetails(CoachDetails coachDetails) {
        if (coachDetails == null) {
            return this;
        }
        this.coachDetails = coachDetails;
        return this;
    }

    public User removeCoachDetails() {
        this.coachDetails = null;
        return this;
    }
}
