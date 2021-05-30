package kul.pl.biblioteka.utils;

public enum MessageType {
  LIMIT("Request for increasing the limit"),
  MESSAGE("Message"),
  BANNED("Banned user");

  public final String label;
  MessageType(String label) {
    this.label = label;
  }
}
