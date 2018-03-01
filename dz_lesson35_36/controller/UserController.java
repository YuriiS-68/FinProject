package dz_lesson35_36.controller;

import dz_lesson35_36.exception.BadRequestException;
import dz_lesson35_36.model.User;
import dz_lesson35_36.service.UserService;

public class UserController {

    public static User registerUser(User user)throws Exception{
        if (user == null)
            throw new BadRequestException("This " + user + " is not exist");

        return UserService.registerUser(user);
    }
}
