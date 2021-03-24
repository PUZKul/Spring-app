package kul.pl.biblioteka.utils;

public enum SortSetting {
    TITLE ("title"),
    RATING ("rating"),
    YEAR ("year");

    private final String name;

    SortSetting(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
