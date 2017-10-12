package project.net.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import project.net.model.User;
import project.net.model.dto.DtoUser;
import project.net.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class LoginController {

    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView showLoginForm(String error, String logout) {
        ModelAndView model = new ModelAndView("login");
        model.addObject(new DtoUser());
        if (error != null) {
            model.addObject("error",
                    "Username or password is incorrect");
        }
        if (logout != null) {
            model.addObject("message",
                    "Logged out successfully");
        }
        return model;
    }

    @PostMapping("/login")
    public ModelAndView checkLogin(@Valid DtoUser dtoUser, BindingResult result) {
        ModelAndView model = new ModelAndView();
        if(result != null) {
            model.setViewName("/logon");
            model.addObject(new DtoUser());
        }
        User user = userService.findUserByEmail(dtoUser.getEmail());
        if(user != null) {
            if(user.getPassword().equals(dtoUser.getPassword())){
                model.setViewName("/");
            }
        }
        return model;
    }

    @GetMapping("/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
}
