package com.enrico.activities.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name="activities")
public class Activity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="location")
    private String location;

    @Column(name = "date_from")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dateFrom;

    @Column(name = "date_to")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dateTo;

    @Column(name="free_places")
    private Integer freePlaces;

    @Column(name="booked_places")
    private Integer bookedPlaces;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "activities", cascade = { CascadeType.ALL })
    @JsonIgnore
    Set<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getFreePlaces() {
        return freePlaces;
    }

    public void setFreePlaces(Integer freePlaces) {
        this.freePlaces = freePlaces;
    }

    public Integer getBookedPlaces() {
        return bookedPlaces;
    }

    public void setBookedPlaces(Integer bookedPlaces) {
        this.bookedPlaces = bookedPlaces;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}
