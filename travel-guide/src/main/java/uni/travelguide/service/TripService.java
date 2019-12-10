package uni.travelguide.service;

import uni.travelguide.model.Trip;
import java.util.List;

public interface TripService {

    List<Trip> findByUserId(long id);

    void createTrip(Trip trip);

    Trip getTrip(int tripId);

    void updateTrip(Trip trip);

    void removeTrip(int tripId);

    List<Trip> searchByName(String text, long userId);
}
