package kul.pl.biblioteka.utils;

public enum MessageStatus {
  PENDING("PENDING"),
  READ("READ"),
  REFUSED("REFUSED"),
  APPROVED("APPROVED");

  public final String label;
  MessageStatus(String label) {
    this.label = label;
  }
}
