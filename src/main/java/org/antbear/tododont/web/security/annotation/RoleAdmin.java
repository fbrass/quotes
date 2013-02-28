package org.antbear.tododont.web.security.annotation;

import javax.annotation.security.RolesAllowed;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Admin role annotation evaluated by Spring Security.
 */
@RolesAllowed("hasRole('ROLE_ADMIN')")
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleAdmin {
}
