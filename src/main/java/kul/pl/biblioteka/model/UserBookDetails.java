package kul.pl.biblioteka.model;

public class UserBookDetails {
  private final long totalBooks;
  private final long currentBooks;

  public UserBookDetails(long totalBooks, long currentBooks) {
    this.totalBooks = totalBooks;
    this.currentBooks = currentBooks;
  }

  public long getTotalBooks() {
    return totalBooks;
  }

  public long getCurrentBook() {
    return currentBooks;
  }
}
