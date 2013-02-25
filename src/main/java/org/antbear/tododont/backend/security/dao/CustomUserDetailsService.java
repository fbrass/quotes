package org.antbear.tododont.backend.security.dao;

import org.antbear.tododont.backend.security.entity.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface CustomUserDetailsService extends UserDetailsService {

    boolean isExistingUser(final String email);

    void createUser(final CustomUserDetails user);

    CustomUserDetails loadUserByRegistrationToken(final String registrationToken);

    List<UserDetails> loadUsersByMissingActivation();

    void enableUser(final String email);

    void updatePasswordResetToken(final String email, final String passwordResetToken);

    void updatePassword(final CustomUserDetails user);

    void deleteUser(final String email);
}
