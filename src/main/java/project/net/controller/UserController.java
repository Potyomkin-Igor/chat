package project.net.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.net.model.User;
import project.net.service.UserService;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView helloMessage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("index");
        return model;
    }

    @GetMapping("/create")
    public ModelAndView getUserForm() {
        ModelAndView model = new ModelAndView();
        model.setViewName("createUser");
        model.addObject("user", new User());
        return model;
    }

    @PostMapping("/create")
    public ModelAndView create(@Valid User user, BindingResult result) {
        ModelAndView model = new ModelAndView();
        boolean userIsValid = userService.passwordValidation(user);
        if(result.hasErrors() && Objects.equals(userIsValid, Boolean.FALSE)){
            model.setViewName("createUser");
            model.addObject(user);
        } else {
            userService.saveUser(user);
            model.setViewName("usersChat");
            model.addObject("allUsers", userService.getAllUsers());
        }
        return model;
    }
}
