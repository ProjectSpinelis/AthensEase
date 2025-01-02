package com.athensease.optimization;

import com.athensease.sights.Sight;
import com.athensease.sights.Trip;
import java.util.List;
import java.util.ArrayList;

public class TrailHeadInclusion {

    private static int nightsAtHotel;

    public static void addOneTraildHead(Trip trip, List<Sight> sights) {
        // Add one trailhead to the trip

        List<Sight> plan = new ArrayList<>();
        double remainingTime = 480; // Start with 480 minutes available each day - 8 hours

        Sight hotel = new Sight("Hotel", "Hotel", 0, 0, "Hotel", false);
        nightsAtHotel = 0;
        
        int j = 0;
        for (Sight sight : sights) {
            double timeNeeded = sight.getVisitTime() + sight.getDurationToStartingPoint();

            // Check if there is enough time to visit the sight and return to the hotel
            if (remainingTime < timeNeeded) {
                // End the current day and return to the hotel
                plan.add(hotel);
                nightsAtHotel += 1;
                remainingTime = 480; // Reset for the next day
            }

            // Add the sight to the plan
            plan.add(sight);
            remainingTime -= sight.getVisitTime(); // Deduct the used time
            if (j != 0) { // If it's not the first sight
                remainingTime -= sights.get(j-1).calculateDurationToSight(sights.get(j)); // Deduct the time to travel to the sight
            } else { // If it's the first sight
                remainingTime -= sight.getDurationFromStartingPoint(); // Deduct the time to travel to the first sight
            }
            j += 1;
        }

        int i =1;
        for (Sight sight : plan) {
            sight.setVisitOrder(i);
            i += 1;
        }

        trip.setOptimizedSights(plan);
    }
    public static int getNightsAtHotel() {
        return nightsAtHotel;
    }

    public static void setNightsAtHotel(int nights) {
        nightsAtHotel = nights;
    }
}
