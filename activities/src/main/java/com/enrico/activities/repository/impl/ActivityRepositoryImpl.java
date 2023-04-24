package com.enrico.activities.repository.impl;

import com.enrico.activities.model.Activity;
import com.enrico.activities.repository.ActivityRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ActivityRepositoryImpl implements ActivityRepositoryCustom {

    @Autowired
    EntityManager em;

    @Override
    public List<Activity> findActivitiesByNameDateAndAvailability(String name, Date date, boolean available) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Activity> cq = cb.createQuery(Activity.class);

        Root<Activity> activity = cq.from(Activity.class);
        List<Predicate> predicates = new ArrayList<>();
        if (name != null) {
            predicates.add(cb.equal(activity.get("name"), name));
        }
        if (date != null) {
            String pattern = "yyyy-MM-dd";
            DateFormat df = new SimpleDateFormat(pattern);
            String dateAsString = df.format(date);
            predicates.add(cb.equal(cb.function("DATE_FORMAT",
                    String.class,activity.get("dateFrom"),
                    cb.literal("%Y-%m-%d")), dateAsString));
        }
        if (available) {
            Date now = new Date();
            predicates.add(cb.greaterThan(activity.<Date>get("dateTo"), now));
            predicates.add(cb.gt(activity.get("freePlaces"),0));
        }
        cq.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(cq).getResultList();
    }

}
