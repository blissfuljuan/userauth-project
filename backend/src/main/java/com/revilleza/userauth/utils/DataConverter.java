package com.revilleza.userauth.utils;

import com.revilleza.userauth.dto.UserResponse;
import com.revilleza.userauth.model.User;

public class DataConverter {

    public static UserResponse UserToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getMiddlename(),
                user.getEmail());
    }
}