package uni.travelguide.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @RequestMapping(path = {"/index"}, method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home/index.html");
        return modelAndView;
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
