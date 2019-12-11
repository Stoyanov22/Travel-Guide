package uni.travelguide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uni.travelguide.model.Country;
import uni.travelguide.model.Trip;
import uni.travelguide.model.User;
import uni.travelguide.service.CountryService;
import uni.travelguide.service.TripService;
import uni.travelguide.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TripController {

    @Autowired
    TripService tripService;

    @Autowired
    CountryService countryService;

    @Autowired
    UserService userService;

    @RequestMapping(value = {"/trips"}, method = RequestMethod.GET)
    public ModelAndView trips() {
        ModelAndView model = new ModelAndView();
        User user = userService.findUserByEmail(getCurrentUserEmail());
        List<Trip> trips = tripService.findByUserId(user.getId());
        model.addObject("trips", new ArrayList<>(trips));
        model.setViewName("trip/trips");
        return model;
    }

    @RequestMapping(value = {"/createTrip"}, method = RequestMethod.GET)
    public ModelAndView createTrip() {
        ModelAndView model = new ModelAndView();
        List<Country> countries = countryService.findAll();
        Trip trip = new Trip();
        model.addObject("countries", countries);
        model.addObject("trip", trip);
        model.setViewName("trip/createTrip");
        return model;
    }

    @RequestMapping(value = {"/createTrip"}, method = RequestMethod.POST)
    public ModelAndView createTrip(@Valid Trip trip, @RequestParam("countryId") String countryId) {
        ModelAndView model = new ModelAndView();
        int countryIdInt = Integer.parseInt(countryId);
        Country country = countryService.findById(countryIdInt);
        trip.setCountry(country);
        User user = userService.findUserByEmail(getCurrentUserEmail());
        trip.setUser(user);
        tripService.createTrip(trip);
        List<Trip> trips = tripService.findByUserId(user.getId());
        model.addObject("trip", new ArrayList<>(trips));
        model.setViewName("home/index");
        return model;
    }

    @RequestMapping(value = {"/editTrip/{id}"}, method = RequestMethod.GET)
    public ModelAndView editTrip(@PathVariable(value="id") int id) {
        ModelAndView model = new ModelAndView();
        List<Country> countries = countryService.findAll();
        Trip trip = tripService.getTrip(id);
        model.addObject("countries", countries);
        model.addObject("trip", trip);
        model.setViewName("trip/editTrip");
        return model;
    }

    @RequestMapping(value = {"/editTrip/{id}"}, method = RequestMethod.POST)
    public ModelAndView editTrip(@Valid Trip trip, @PathVariable(value="id") int id, @RequestParam("countryId") String countryId ) {
        ModelAndView model = new ModelAndView();
        int countryIdInt = Integer.parseInt(countryId);
        Country country = countryService.findById(countryIdInt);
        trip.setCountry(country);
        trip.setId(id);
        tripService.updateTrip(trip);
        model.setViewName("home/index");
        return model;
    }

    @RequestMapping(value = {"/deleteTrip/{id}"}, method = RequestMethod.GET)
    public ModelAndView deleteTrip(@PathVariable(value="id") int id) {
        ModelAndView model = new ModelAndView();
        Trip trip = tripService.getTrip(id);
        if(trip.getUser().getEmail().equals(getCurrentUserEmail())){
            tripService.removeTrip(id);
        }
        model.setViewName("home/index");
        return model;
    }

    @RequestMapping(value = {"/searchResult"}, method = RequestMethod.POST)
    public ModelAndView search(@RequestParam("searchInput") String searchInput ) {
        ModelAndView model = new ModelAndView();
        User user = userService.findUserByEmail(getCurrentUserEmail());
        List<Trip> trips = tripService.searchByName(searchInput, user.getId());
        model.addObject("trips", new ArrayList<>(trips));
        model.setViewName("trip/searchResult");
        return model;
    }

    private String getCurrentUserEmail(){
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
