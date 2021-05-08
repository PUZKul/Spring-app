package kul.pl.biblioteka.utils;

public enum ReservationState {
  WAITING("WAITING"),
  CANCELED("CANCELED"),
  BORROWED("BORROWED");

  public final String label;
  ReservationState(String label) {
    this.label = label;
  }
}
