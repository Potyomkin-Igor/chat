package project.net.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.net.model.User;
import project.net.model.dto.GenericResponse;
import project.net.service.MailService;
import project.net.service.SecurityService;
import project.net.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Objects;

@Controller
public class PasswordResetController {

    private String EMAIL_PAGE = "/reset/email";
    private String EMAIL_SENT_PAGE = "emailSent";

    private SecurityService securityService;
    private UserService userService;
    private MessageSource messages;
//    private JavaMailSender mailSender;
    private MailService mailService;

    @Autowired
    public PasswordResetController(SecurityService securityService,
                                   UserService userService,
                                   MessageSource messages,
                                   MailService mailService) {
        this.securityService = securityService;
        this.userService = userService;
        this.messages = messages;
        this.mailService = mailService;
    }

    @GetMapping(value = "/resetPasswordEmail")
    public String getEmailPage() {
        return EMAIL_SENT_PAGE;
    }

    @PostMapping(value = "/email/sentMail")
    public ModelAndView getResetPasswordPage(@RequestParam("email") String email, HttpServletRequest request, Locale locale) {
        ModelAndView model = new ModelAndView("emailSent");
        User user = userService.findUserByEmail(email);
        if (Objects.nonNull(user)) {
            String token = userService.createToken(user).getToken();
            mailService.sendResetTokenEmail(request, locale, token, user);
//            mailSender.send(constructResetTokenEmail(getAppUrl(request), token, locale, user));
        }
//        return new GenericResponse(messages.getMessage("message.resetPasswordEmail", null, request.getLocale()));
        return model;
    }

    @PutMapping(value = "/user/changePassword")
    public HttpStatus savePassword(Locale local, @RequestBody String password, @RequestParam String token) {
        String validationResult = securityService.validateToken(token, local);
        if (Objects.nonNull(validationResult)) {
            return HttpStatus.BAD_REQUEST;
        } else {
            User user = securityService.getByPasswordTokenByToken(token).getUser();
            userService.changeUserPassword(user, password);
            securityService.deleteToken(token);
        }
        return HttpStatus.OK;
    }

    public SimpleMailMessage constructResetTokenEmail(String contextPath, String token, final Locale locale, User user) {
        final String url = contextPath + "/user/changePassword?id=" + user.getId()+"&token=" + token;
        final String message = messages.getMessage("message.resetPassword", null, locale);
        return cunstructEmail("Reset Password", message + "\r\n" + url, user);
    }

    public SimpleMailMessage cunstructEmail(String subject, String body, User user) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setSubject(subject);
        mail.setText(body);
        mail.setTo(user.getEmail());
        return mail;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
