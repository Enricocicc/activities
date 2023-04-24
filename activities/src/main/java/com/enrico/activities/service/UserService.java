package com.enrico.activities.service;

import com.enrico.activities.model.Activity;
import com.enrico.activities.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUsers();

    User getUserById(long id);

    User updateUser(User user, long id);

    void deleteUser(long id);

    List<Activity> getAllActivitiesByUserId(long userId);

    String registerToActivity(long userId, long activityId);

    String cancelFromActivity(long userId, long activityId);

}
