package com.enrico.activities.controller;

import com.enrico.activities.model.Activity;
import com.enrico.activities.model.User;
import com.enrico.activities.service.ActivityService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ActivityController {

    private ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        super();
        this.activityService = activityService;
    }

    // build Create Activity REST API
    @PostMapping("/activities")
    public ResponseEntity<Activity> saveActivity(@RequestBody Activity activity){
        return new ResponseEntity<Activity>(activityService.saveActivity(activity), HttpStatus.CREATED);
    }

    // build GetAll Activities REST API
    @GetMapping("/activities")
    public List<Activity> getAllActivities() {
        return activityService.getAllActivities();
    }

    // build Get Activity by Id
    @GetMapping("/activities/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable("id") long id) {
        return new ResponseEntity<Activity>(activityService.getActivityById(id), HttpStatus.OK);
    }

    // build Update Activity REST API
    @PutMapping("/activities/{id}")
    public ResponseEntity<Activity> updateActivity(@RequestBody Activity activity, @PathVariable("id") long id){
        return new ResponseEntity<Activity>(activityService.updateActivity(activity, id), HttpStatus.OK);
    }

    // build Delete Activity REST API
    @DeleteMapping("/activities/{id}")
    public ResponseEntity<String> deleteActivity(@PathVariable("id") long id) {
        activityService.deleteActivity(id);
        return new ResponseEntity<String>("Activity deleted successfully!", HttpStatus.OK);
    }

    @GetMapping("/activities/filter")
    public List<Activity> getFilteredActivities(@RequestParam(value="name", required=false) String name,
                                                @RequestParam(value="date", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
                                                @RequestParam(value="available", required=false) boolean available) {
        System.out.println("getFilteredActivities. Name:" + name + ", Date: " + date + ", Available: " + available);
        return activityService.getFilteredActivities(name, date, available);
    }

    @GetMapping("/activities/{id}/users")
    public List<User> getAllUsersByActivityId(@PathVariable("id") long id) {
        return activityService.getAllUsersByActivityId(id);
    }

}
