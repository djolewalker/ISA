package com.ftnisa.isa.model.user;

import com.ftnisa.isa.repository.UserRepository;

import javax.persistence.*;

@Entity
@Table(name = "isa_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", nullable = false)
    private String email;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User u = (User) o;
        return id != null && id.equals(u.getId());
    }

    @Override
    public String toString() {
        return "Course [id=" + id + ", email=" + email + "]";
    }
}
