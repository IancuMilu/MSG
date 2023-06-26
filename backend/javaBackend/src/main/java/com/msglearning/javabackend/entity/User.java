package com.msglearning.javabackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = User.TABLE_NAME)
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    static final String TABLE_NAME = "user";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String gender;

    @Column
    private boolean admin;

    public String getFullName(){
        return firstName + " " + lastName;
    }

}
