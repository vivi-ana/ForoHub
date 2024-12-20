package alura.challenge.ForoHub.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Repository interface for managing users.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username.
     * @param name the name of the user to find
     * @return the UserDetails of the found user
     */
    UserDetails findByName(String name);
}
