package uni.travelguide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uni.travelguide.model.Country;
import uni.travelguide.model.Trip;
import uni.travelguide.model.User;
import uni.travelguide.service.CountryService;
import uni.travelguide.service.TripService;
import uni.travelguide.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class RestTripController {

    @Autowired
    TripService tripService;

    @Autowired
    CountryService countryService;

    @Autowired
    UserService userService;

    @GetMapping(value = {"/rest"})
    public ModelAndView trips() {
        ModelAndView model = new ModelAndView();
        List<Country> countries = countryService.findAll();
        model.addObject("countries", countries);
        model.setViewName("rest/trips");
        return model;
    }

    @GetMapping(path = "/rest/getTrips")
    public @ResponseBody List<Trip> getTrips(HttpSession session) {
        User user = userService.findUserByEmail(getCurrentUserEmail());
        List<Trip> trips = tripService.findByUserId(user.getId());
        return trips;
    }

    @PostMapping(path = "/rest/createTrip")
    public int createTrip(@RequestParam(value = "name") String name, @RequestParam(value = "countryId") String countryId){
        try{
            Trip trip = new Trip();
            trip.setName(name);
            int countryIdInt = Integer.parseInt(countryId);
            Country country = countryService.findById(countryIdInt);
            trip.setCountry(country);
            User user = userService.findUserByEmail(getCurrentUserEmail());
            trip.setUser(user);
            trip = tripService.createTrip(trip);
            return trip.getId();
        }
        catch (Exception e){
            return 0;
        }
    }

    @GetMapping(path = "/rest/editTrip")
    public Trip editTrip(@RequestParam(value = "id") String id){
        try{
            Trip trip = tripService.getTrip(Integer.valueOf(id));
            return trip;
        }
        catch (Exception e){
            return null;
        }
    }

    @PostMapping(path = "/rest/editTrip")
    public int editTrip(@RequestParam(value = "id") String id, @RequestParam(value = "name") String name, @RequestParam(value = "countryId") String countryId){
        try{
            Trip trip = tripService.getTrip(Integer.parseInt(id));
            trip.setName(name);
            int countryIdInt = Integer.parseInt(countryId);
            Country country = countryService.findById(countryIdInt);
            trip.setCountry(country);
            tripService.updateTrip(trip);
            return 1;
        }
        catch (Exception e){
            return 0;
        }
    }

    @PostMapping(path = "/rest/deleteTrip")
    public int deleteTrip(@RequestParam(value = "id") String id){
        try{
            Trip trip = tripService.getTrip(Integer.valueOf(id));
            if(trip.getUser().getEmail().equals(getCurrentUserEmail())){
                tripService.removeTrip(Integer.valueOf(id));
            }
            return 1;
        }
        catch (Exception e){
            return 0;
        }
    }

    private String getCurrentUserEmail() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                username = ((UserDetails) authentication.getPrincipal()).getUsername();
            } else {
                username = (String) authentication.getPrincipal();
            }
        }
        return username;
    }
}
