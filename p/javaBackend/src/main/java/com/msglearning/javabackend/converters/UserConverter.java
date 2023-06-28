package com.msglearning.javabackend.converters;

import com.msglearning.javabackend.entity.User;
import com.msglearning.javabackend.to.UserTO;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public User convertToEntity(UserTO userTO) {
        return User.builder()
                .id(userTO.getId())
                .firstName(userTO.getFirstName())
                .lastName(userTO.getLastName())
                .email(userTO.getEmail())
                .password(userTO.getPassword())
                .gender(userTO.getGender())
                .admin(userTO.isAdmin())
                .build();
    }

    public UserTO convertToTO(User user) {
        return UserTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .gender(user.getGender())
                .admin(user.isAdmin())
                .build();
    }
}
