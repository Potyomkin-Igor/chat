package project.net.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class CustomUserDetailService implements UserDetailsService{

    private UserService userService;

    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        project.net.model.User user = userService.findUserByEmail(username);
        if (Objects.nonNull(user)) {
            Set<GrantedAuthority> authorities = new HashSet<>();
            user.getRoles().forEach(item -> {
                authorities.add(new SimpleGrantedAuthority(item.getName()));
            });
            return new User(user.getEmail(), user.getPassword(), authorities);
        }
        throw new UsernameNotFoundException("User " + username + " not found");
    }
}
