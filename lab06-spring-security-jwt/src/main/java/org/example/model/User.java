package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String role;
    @ManyToOne(cascade=CascadeType.ALL)
    private Hotel hotel;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = "USER";
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", hotel=" + hotel +
                '}';
    }
}