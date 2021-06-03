package kul.pl.biblioteka.security;

import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;


class DefaultPreAuthenticationChecks implements UserDetailsChecker {

  public void check(UserDetails user) {
    if (!user.isAccountNonLocked()) {
      throw new LockedException("User account is locked");
    }
  }
}
