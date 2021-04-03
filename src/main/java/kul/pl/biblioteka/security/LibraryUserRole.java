package kul.pl.biblioteka.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static kul.pl.biblioteka.security.LibraryUserPermission.*;

public enum LibraryUserRole {
    USER(Stream.of(BORROW_READ, BORROW_WRITE, RESERVATION_READ, RESERVATION_WRITE, BOOKS_READ, USER_READ, USER_WRITE, BOOK_COPY_READ).collect(Collectors.toSet())),
    ADMIN(Stream.of(BORROW_READ, BORROW_WRITE, RESERVATION_READ, RESERVATION_WRITE, BOOKS_READ, BOOKS_WRITE, USER_READ, USER_WRITE, BOOK_COPY_READ, BOOK_COPY_WRITE, BLACKLIST_READ, BLACKLIST_WRITE).collect(Collectors.toSet()));

    private final Set<LibraryUserPermission> permissions;

    LibraryUserRole(Set<LibraryUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<LibraryUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
