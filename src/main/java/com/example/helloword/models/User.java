package com.example.helloword.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @JsonProperty(value = "id")
    @Column("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @JsonProperty(value = "name")
    @Column("name")
    String name;

    @JsonProperty(value = "email")
    @Column("email")
    String email;

    @JsonProperty(value = "password")
    @Column("password")
    String password;

    @JsonProperty(value = "createAt")
    @Column("create_at")
    String createAt;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createAt='" + createAt + '\'' +
                '}';
    }
}
