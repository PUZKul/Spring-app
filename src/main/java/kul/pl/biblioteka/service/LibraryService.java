package kul.pl.biblioteka.service;

import kul.pl.biblioteka.model.LibraryBook;
import kul.pl.biblioteka.repository.BookCopiesRepository;
import kul.pl.biblioteka.repository.BookRepository;
import kul.pl.biblioteka.utils.LibraryPage;
import kul.pl.biblioteka.utils.SortSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static kul.pl.biblioteka.utils.Constants.LIMIT;

@Service
public class LibraryService {
    private final BookRepository bookRepository;
    private final BookCopiesRepository copiesRepository;

    @Autowired
    public LibraryService(BookRepository bookRepository, BookCopiesRepository copiesRepository) {
        this.bookRepository = bookRepository;
        this.copiesRepository = copiesRepository;
    }

    public Page<LibraryBook> getBooks(int offset, int limit, SortSetting sort, Sort.Direction direction){
        if(limit > LIMIT) throw new IllegalArgumentException(String.format("Given limit (%d) cannot be greater than %d", limit, LIMIT));
        Pageable pageable = new LibraryPage(offset, limit, direction, sort.toString());
        return bookRepository.findAll(pageable);
    }

    public Optional<LibraryBook> getBookById(long bookId){
        return bookRepository.findById(bookId);
    }

    public Page<LibraryBook> searchBooks(int offset, int limit, String titleQuery, String authorQuery, String publisherQuery){
        Pageable pageable = new LibraryPage(offset, limit, Sort.Direction.ASC, SortSetting.TITLE.toString());
        return bookRepository.searchBooks(titleQuery, authorQuery, publisherQuery, pageable);
    }

    public long availableCopies(long bookId){
       return copiesRepository.availableCopies(bookId);
    }
}
