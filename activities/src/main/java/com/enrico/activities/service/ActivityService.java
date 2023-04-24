package com.enrico.activities.service;

import com.enrico.activities.model.Activity;
import com.enrico.activities.model.User;

import java.util.Date;
import java.util.List;

public interface ActivityService {

    Activity saveActivity(Activity activity);

    List<Activity> getAllActivities();

    Activity getActivityById(long id);

    Activity updateActivity(Activity activity, long id);

    void deleteActivity(long id);

    List<Activity> getFilteredActivities(String name, Date date, boolean available);

    List<User> getAllUsersByActivityId(long activityId);

}
