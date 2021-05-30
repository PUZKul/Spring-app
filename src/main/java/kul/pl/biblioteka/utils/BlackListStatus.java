package kul.pl.biblioteka.utils;

public enum BlackListStatus {
    BLOCKED("BLOCKED"),
    RELEASED("RELEASED");

    public final String label;
    BlackListStatus(String label) {
        this.label = label;
    }
}
