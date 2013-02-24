package org.antbear.tododont.backend.security.dao;

import org.antbear.tododont.backend.security.entity.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {

    boolean isExistingUser(final String email);

    void createUser(final CustomUserDetails user);

    CustomUserDetails loadUserByRegistrationToken(final String registrationToken);

    void enableUser(final String email);

    void updatePasswordResetToken(final String email, final String passwordResetToken);

    void updatePassword(final CustomUserDetails user);

    void deleteUser(final String email);
}
