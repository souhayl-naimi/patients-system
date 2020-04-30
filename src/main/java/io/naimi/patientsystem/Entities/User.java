package io.naimi.patientsystem.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User implements Serializable {
    @Id
    private String username;
    private String password;
    private Boolean enabled;
    @ManyToMany
    @JoinTable(name="USERS_ROLES")
    private Collection<Role> roles;
}
