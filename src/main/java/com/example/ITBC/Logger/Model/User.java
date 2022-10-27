package com.example.ITBC.Logger.Model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder(toBuilder = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRoles role;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @Column(name = "user_logs")
    private List<Log> userLogs = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User otherUser)) return false;
        return Objects.equals(getUsername(), otherUser.getUsername());
    }

    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }
}
