package com.switchfully.teamair.codecoach.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "roles")
public class Role {


    @Id
    @GeneratedValue
    @Column(name = "role_id")
    int id;
    @Enumerated(EnumType.STRING)
    @Column(name = "role_status")
    RoleStatus authority;
    @ManyToMany(mappedBy="roles", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<User> users;

    public Role(RoleStatus authority) {
        this.authority = authority;
    }

    public void add(User user){
        users.add(user);
    }

}
