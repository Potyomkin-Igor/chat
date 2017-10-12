package project.net.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.net.dao.PasswordResetDao;
import project.net.dao.RoleDao;
import project.net.dao.UserDao;
import project.net.model.PasswordResetToken;
import project.net.model.Role;
import project.net.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class UserService {
    private UserDao userDao;
    private RoleDao roleDao;
    private PasswordResetDao passwordResetDao;

    @Autowired
    public UserService(UserDao userDao, RoleDao roleDao, PasswordResetDao passwordResetDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordResetDao = passwordResetDao;
    }

    public User findUserById(Long userId) {
        return userDao.findOne(userId);
    }

    public List<User> getAllUsers() {
        List<User> result = (List<User>)userDao.findAll();
        return result;
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

    public User changeUserPassword(User user, String password) {
        String encodePassword = new BCryptPasswordEncoder().encode(password);
        user.setPassword(encodePassword);
        return userDao.save(user);
    }

    public User getUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    public void deleteToken(String token) {
       passwordResetDao.deleteByToken(token);
    }

    public boolean passwordValidation(User user) {
        User checkedUser = userDao.findUserByEmail(user.getEmail());
        return Objects.equals(user.getPassword(), user.getConfirmPassword()) && Objects.isNull(checkedUser);
    }
}
