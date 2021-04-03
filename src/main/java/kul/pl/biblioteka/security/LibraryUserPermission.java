package kul.pl.biblioteka.security;

public enum LibraryUserPermission {
    // permissions for each table in database (for reading and writing)

    BOOKS_READ("books:read"),
    BOOKS_WRITE("books:write"),
    BOOK_COPY_READ("book_copy:read"),
    BOOK_COPY_WRITE("book_copy:write"),
    BORROW_READ("borrow:read"),
    BORROW_WRITE("borrow:write"),
    RESERVATION_READ("reservation:read"),
    RESERVATION_WRITE("reservation:write"),
    USER_READ("users:read"),
    USER_WRITE("users:write"),
    BLACKLIST_READ("blacklist:read"),
    BLACKLIST_WRITE("blacklist:write");

    private final String permission;
    LibraryUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
