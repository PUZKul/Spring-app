package kul.pl.biblioteka.service;

import lombok.RequiredArgsConstructor;

import kul.pl.biblioteka.exception.ResourceNotFoundException;
import kul.pl.biblioteka.holder.BookCopyHolder;
import kul.pl.biblioteka.model.BookCopy;
import kul.pl.biblioteka.model.LibraryBook;
import kul.pl.biblioteka.repository.BookCopiesRepository;
import kul.pl.biblioteka.repository.BookRepository;
import kul.pl.biblioteka.repository.UserBookRepository;
import kul.pl.biblioteka.utils.LibraryPage;
import kul.pl.biblioteka.utils.RandomPicker;
import kul.pl.biblioteka.utils.SortSetting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static kul.pl.biblioteka.utils.Helper.addDaysFromToday;
import static kul.pl.biblioteka.utils.SortSetting.POPULARITY;
import static kul.pl.biblioteka.utils.SortSetting.TITLE;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
public class LibraryService {

  private final BookRepository bookRepository;
  private final BookCopiesRepository copiesRepository;
  private final UserBookRepository userBookRepository;

  public Page<LibraryBook> getBooks(
      int offset, int limit, SortSetting sort, Sort.Direction direction) {
    Pageable pageable = new LibraryPage(offset, limit, direction, sort.toString());
    return bookRepository.findAll(pageable);
  }

  public Page<LibraryBook> searchBooks(
      int offset, int limit, String titleQuery, String authorQuery, String publisherQuery) {
    Pageable pageable = new LibraryPage(offset, limit, ASC, TITLE.toString());
    return bookRepository.searchBooks(titleQuery, authorQuery, publisherQuery, pageable);
  }

  public Page<LibraryBook> discoverBooks(int limit) {
    Pageable pageable = new LibraryPage(0, 50, DESC, POPULARITY.toString());
    Page<LibraryBook> page = bookRepository.findAll(pageable);
    return new PageImpl<>(
        RandomPicker.pick(new ArrayList<>(page.getContent()), limit), pageable, limit);
  }

  public Optional<LibraryBook> getBookById(long bookId) {
    Optional<LibraryBook> book = bookRepository.findById(bookId);
    if (book.isEmpty())
      throw new ResourceNotFoundException("Book with given ID does not exist in database");
    else
      return book;
  }

  public long availableCopies(long bookId) {
    if (!bookRepository.existsById(bookId))
      throw new ResourceNotFoundException("Book with given ID does not exist in database");
    return copiesRepository.availableCopies(bookId);
  }

  public List<BookCopyHolder> getCopies(long bookId) {
    List<BookCopy> copies = copiesRepository.getCopiesByBookId(bookId);
    return createBookCopyHolder(copies);
  }

  private List<BookCopyHolder> createBookCopyHolder(List<BookCopy> books) {
    return books.stream()
        .map(e -> new BookCopyHolder(
            e.getId(),
            e.getBookId(),
            e.isBorrowed(),
            e.isAccess(),
            e.getCode(),
            expectedDate(e.isBorrowed(), e.getId())))
        .collect(toList());
  }

  private Date expectedDate(boolean isBorrowed, long id) {
    Date expectedDate = userBookRepository.getExpectedDate(id);
    if(isBorrowed && expectedDate == null) return addDaysFromToday(30);
    return expectedDate;
  }
}
