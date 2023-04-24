package com.enrico.activities.service.impl;

import com.enrico.activities.exception.ResourceNotFoundException;
import com.enrico.activities.model.Activity;
import com.enrico.activities.model.User;
import com.enrico.activities.repository.ActivityRepository;
import com.enrico.activities.repository.UserRepository;
import com.enrico.activities.service.ActivityService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    private ActivityRepository activityRepository;
    private UserRepository userRepository;


    public ActivityServiceImpl(ActivityRepository activityRepository,
                               UserRepository userRepository) {
        super();
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Activity saveActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    @Override
    public Activity getActivityById(long id) {
        return activityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Activity", "id", id));
    }

    @Override
    public Activity updateActivity(Activity activity, long id) {
        Activity activityToUpdate = activityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Activity", "id", id));
        activityToUpdate.setName(activity.getName());
        activityToUpdate.setLocation(activity.getLocation());
        activityToUpdate.setDateFrom(activity.getDateFrom());
        activityToUpdate.setDateTo(activity.getDateTo());
        activityToUpdate.setFreePlaces(activity.getFreePlaces());
        activityToUpdate.setBookedPlaces(activity.getBookedPlaces());
        return activityRepository.save(activityToUpdate);
    }

    @Override
    public void deleteActivity(long id) {
        Activity activityToDelete = activityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Activity", "id", id));
        activityRepository.delete(activityToDelete);
    }

    @Override
    public List<Activity> getFilteredActivities(String name, Date date, boolean available) {
        return activityRepository.findActivitiesByNameDateAndAvailability(name, date, available);
    }

    @Override
    public List<User> getAllUsersByActivityId(long id) {
        Activity existingActivity = activityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Activity", "id", id));
        return userRepository.findByActivities(existingActivity);
    }

}
