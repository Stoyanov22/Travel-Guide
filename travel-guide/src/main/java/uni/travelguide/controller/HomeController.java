package uni.travelguide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uni.travelguide.model.User;
import uni.travelguide.service.UserService;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping(value= {"/index"}, method=RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView model = new ModelAndView();
        model.setViewName("home/index");
        return model;
    }

    @RequestMapping(path = {"/about"}, method = RequestMethod.GET)
    public ModelAndView about() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home/about.html");
        return modelAndView;
    }

    @RequestMapping(path = {"/api"}, method = RequestMethod.GET)
    public ModelAndView api() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home/api.html");
        return modelAndView;
    }
}
