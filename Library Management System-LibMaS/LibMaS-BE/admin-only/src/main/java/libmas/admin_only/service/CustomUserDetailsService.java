package libmas.admin_only.service;

import libmas.admin_only.entity.UserDetail;
import libmas.admin_only.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDetailRepository userDetailRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetail ud = userDetailRepository.findByMail(username);
        if (Objects.nonNull(ud)) {
            return new User(ud.getMail(), ud.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
