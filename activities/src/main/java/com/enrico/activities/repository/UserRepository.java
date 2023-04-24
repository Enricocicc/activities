package com.enrico.activities.repository;

import com.enrico.activities.model.Activity;
import com.enrico.activities.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>  {

    User findByEmail(String email);
    List<User> findByActivities(Activity activity);
}
