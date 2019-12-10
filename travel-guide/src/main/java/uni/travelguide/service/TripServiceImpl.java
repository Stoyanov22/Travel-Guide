package uni.travelguide.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
        return getTripsByUserId(userId);
    }

    @Override
    public void createTrip(Trip trip) {
        tripRepository.save(trip);
    }

    @Override
    public Trip getTrip(int tripId) {
        return tripRepository.getOne(tripId);
    }

    @Override
    public void updateTrip(int tripId) {
    }

    @Override
    public void removeTrip(int tripId) {
        tripRepository.deleteById(tripId);
    }

    private List<Trip> getTripsByUserId(long userId){
        Query q = entityManager.createNativeQuery("SELECT T.ID, T.NAME, T.COUNTRY_ID, T.USER_ID FROM TRIP T WHERE user_id=?1");
        q.setParameter(1, userId);
        List<Object> results = q.getResultList();
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
