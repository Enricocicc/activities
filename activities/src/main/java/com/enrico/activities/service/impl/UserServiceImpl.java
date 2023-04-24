package com.enrico.activities.service.impl;

import com.enrico.activities.exception.ResourceNotFoundException;
import com.enrico.activities.model.Activity;
import com.enrico.activities.model.User;
import com.enrico.activities.repository.ActivityRepository;
import com.enrico.activities.repository.UserRepository;
import com.enrico.activities.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ActivityRepository activityRepository;
    private PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository,
                           ActivityRepository activityRepository,
                           PasswordEncoder passwordEncoder) {
        super();
        this.userRepository = userRepository;
        this.activityRepository = activityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        this.encodePassword(user);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public User updateUser(User user, long id) {
        User userToUpdate = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        return userRepository.save(userToUpdate);
    }

    @Override
    public void deleteUser(long id) {
        User userToDelete = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(userToDelete);
    }

    @Override
    public List<Activity> getAllActivitiesByUserId(long id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return activityRepository.findByUsers(existingUser);
    }

    @Override
    public String registerToActivity(long userId, long activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new ResourceNotFoundException("Activity", "id", activityId));
        String result;
        if (activity.getFreePlaces() > 0) {
            User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
            user.getActivities().add(activity);
            activity.getUsers().add(user);
            activity.setFreePlaces(activity.getFreePlaces() - 1);
            activity.setBookedPlaces(activity.getBookedPlaces() + 1);
            activityRepository.save(activity);
            userRepository.save(user);
            result = "User registered successfully to the Activity!";
        } else {
            result = "Cannot register the User. Activity is complete!";
        }
        return result;
    }

    @Override
    public String cancelFromActivity(long userId, long activityId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        String result = "Impossible to remove. User was not registered to this Activity!";
        for (Activity tempActivity: user.getActivities()) {
            if (tempActivity.getId() == activityId) {
                user.getActivities().remove(tempActivity);
                tempActivity.setFreePlaces(tempActivity.getFreePlaces() + 1);
                tempActivity.setBookedPlaces(tempActivity.getBookedPlaces() - 1);
                tempActivity.getUsers().remove(user);
                activityRepository.save(tempActivity);
                userRepository.save(user);
                result = "User correctly removed from the Activity!";
                break;
            }
        }
        return result;
    }

    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

}
