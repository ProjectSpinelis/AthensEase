package com.athensease.optimization;

import com.athensease.sights.Sight;
import java.util.List;
import java.util.ArrayList;

public class TrailHeadInclusion {

    public static List<Sight> findHotelStopPoints(List<Sight> sights) {
        List<Sight> hotelStopPoints = new ArrayList<>();
        double remainingTime = 480; // 8 hours in minutes

        for (int i = 0; i < sights.size(); i++) {
            Sight currentSight = sights.get(i);
            double travelTime = (i == 0)
                    ? currentSight.getDurationFromStartingPoint() // Travel time from start point for the first sight
                    : sights.get(i - 1).calculateDurationToSight(currentSight);
            double timeNeeded = travelTime + currentSight.getVisitTime();

            // Check if we need to return to the hotel after this sight
            if (remainingTime < timeNeeded + currentSight.getDurationToStartingPoint()) {
                hotelStopPoints.add(currentSight); // Add the current sight as the stop point
                remainingTime = 480; // Reset time for the next day
            }

            // Deduct time spent visiting the sight and traveling to it
            remainingTime -= timeNeeded;
        }

        return hotelStopPoints;
    }
}
