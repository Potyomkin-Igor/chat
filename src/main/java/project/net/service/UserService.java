package project.net.service;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import project.net.dao.PasswordResetDao;
import project.net.dao.RoleDao;
import project.net.dao.UserDao;
import project.net.model.PasswordResetToken;
import project.net.model.Role;
import project.net.model.User;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class UserService {
    private UserDao userDao;
    private RoleDao roleDao;
    private PasswordResetDao passwordResetDao;
    private MessageSource messages;

    @Autowired
    public UserService(UserDao userDao, RoleDao roleDao, PasswordResetDao passwordResetDao, MessageSource messages) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordResetDao = passwordResetDao;
        this.messages = messages;
    }

    public User findUserById(Long userId) {
        return userDao.findOne(userId);
    }

    public List<User> getAllUsers() {
        return (List<User>)userDao.findAll();
    }

    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    public User saveUser(User user) {
        if(Objects.isNull(findUserByEmail(user.getEmail())) && Objects.nonNull(user.getPassword())
                && Objects.equals(user.getConfirmPassword(), user.getPassword())) {
            String name = user.getFirstName().substring(0,1).toUpperCase();
            name = name + user.getFirstName().substring(1);
            user.setFirstName(name);
            Set<Role> roles = new HashSet<>();
            roles.add(roleDao.findByName("ROLE_USER"));
            user.setRoles(roles);
            return userDao.save(user);
        }
        return null;
    }

    public PasswordResetToken createToken(User user) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken myToken = new PasswordResetToken(token, user, LocalDate.now().plusDays(1));
        return passwordResetDao.save(myToken);
    }

    public String validateToken(String token, Locale local) {
        String result = null;
        PasswordResetToken passToken = passwordResetDao.findByToken(token);
        if (Objects.isNull(passToken)) {
            result = messages.getMessage("token.invalid", null, local);
        } else if (passToken.getExpiryDate().isBefore(LocalDate.now())) {
            result = messages.getMessage("date.expired", null, local);
        }
        return result;
    }

    public User changeUserPassword(User user, String password) {
        user.setPassword(password);
        return userDao.save(user);
    }
}
