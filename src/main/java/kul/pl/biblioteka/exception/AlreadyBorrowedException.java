package kul.pl.biblioteka.exception;

public class AlreadyBorrowedException extends RuntimeException{

  public AlreadyBorrowedException(String message) {
    super(message);
  }
}
