package de.spqrinfo.quotes.backend.security.dao;

import de.spqrinfo.quotes.backend.security.entity.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Date;
import java.util.List;

public interface CustomUserDetailsService extends UserDetailsService {

    boolean isExistingUser(final String email);

    void createUser(final CustomUserDetails user);

    CustomUserDetails loadUserByRegistrationToken(final String registrationToken);

    List<UserDetails> loadUsersWithMissingActivation();

    List<UserDetails> loadUsersWithMissingPasswordReset();

    void enableUser(final String email);

    void clearPasswordResetToken(final String email);

    void updatePasswordResetToken(final String email, final String passwordResetToken);

    void updatePasswordResetToken(final String email, final String passwordResetToken, final Date passwordResetRequestedAt);

    void updatePassword(final CustomUserDetails user);

    void deleteUser(final String email);
}
