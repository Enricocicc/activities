package com.enrico.activities.repository;

import com.enrico.activities.model.Activity;

import java.util.Date;
import java.util.List;

public interface ActivityRepositoryCustom {
    List<Activity> findActivitiesByNameDateAndAvailability(String name, Date date, boolean available);
}
