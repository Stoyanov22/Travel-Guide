package uni.travelguide.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import uni.travelguide.model.Trip;
import uni.travelguide.repository.TripRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Service("tripService")
public class TripServiceImpl implements TripService {

    @Autowired
    TripRepository tripRepository;

    @Autowired
    CountryService countryService;

    @Autowired
    UserService userService;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public List<Trip> findByUserId(long userId) {
        Query q = entityManager.createNativeQuery("SELECT T.ID, T.NAME, T.COUNTRY_ID, T.USER_ID FROM TRIP T WHERE user_id=?1");
        q.setParameter(1, userId);
        List<Object> results = q.getResultList();
        List<Trip> trips = convertQueryObjectToTripList(results);
        return trips;
    }

    @Override
    public Trip createTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    public Trip getTrip(int tripId) {
        return tripRepository.findById(tripId).get();
    }

    @Override
    @Transactional
    public void updateTrip(Trip trip) {
        Query q = entityManager.createNativeQuery("update Trip t set t.name=?1, t.country_id=?2 where t.id=?3");
        q.setParameter(1, trip.getName());
        q.setParameter(2, trip.getCountry().getId());
        q.setParameter(3, trip.getId());
        q.executeUpdate();
    }

    @Override
    public void removeTrip(int tripId) {
        tripRepository.deleteById(tripId);
    }

    @Override
    public List<Trip> searchByName(String text, long userId) {
        Query q = entityManager.createNativeQuery("SELECT * FROM TRIP T WHERE T.NAME LIKE ?1 AND T.USER_ID=?2");
        q.setParameter(1, "%"+text+"%");
        q.setParameter(2, userId);
        List<Object> results = q.getResultList();
        List<Trip> trips = convertQueryObjectToTripList(results);
        return trips;
    }

    private List<Trip> convertQueryObjectToTripList(List<Object> results){
        List<Trip> trips = new ArrayList<>();
        for(Object result : results){
            Trip trip = new Trip();
            Object[] objectParams = (Object[])result;
            trip.setId(Integer.parseInt(objectParams[0].toString()));
            trip.setName(objectParams[1].toString());
            trip.setCountry(countryService.findById(Long.parseLong(objectParams[2].toString())));
            trip.setUser(userService.findUserById(Integer.parseInt(objectParams[3].toString())));
            trips.add(trip);
        }
        return trips;
    }
}
