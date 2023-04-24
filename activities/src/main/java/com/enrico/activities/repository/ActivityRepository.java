package com.enrico.activities.repository;

import com.enrico.activities.model.Activity;
import com.enrico.activities.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long>, ActivityRepositoryCustom {
    List<Activity> findByUsers(User user);
}
