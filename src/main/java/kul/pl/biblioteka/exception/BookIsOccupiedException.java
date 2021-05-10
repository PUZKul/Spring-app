package kul.pl.biblioteka.exception;

public class BookIsOccupiedException extends RuntimeException{

  public BookIsOccupiedException(String message) {
    super(message);
  }
}
