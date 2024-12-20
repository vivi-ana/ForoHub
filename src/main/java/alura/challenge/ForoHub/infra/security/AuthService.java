package alura.challenge.ForoHub.infra.security;

import alura.challenge.ForoHub.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service class for managing user authentication.
 */
@Service
public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    /**
     * Loads a user by their username.
     * @param userName the username of the user
     * @return the UserDetails of the user
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepository.findByName(userName);
    }
}