package kul.pl.biblioteka.service;

import kul.pl.biblioteka.exception.ResourceNotFoundException;
import kul.pl.biblioteka.model.LibraryBook;
import kul.pl.biblioteka.model.PageHolder;
import kul.pl.biblioteka.repository.BookCopiesRepository;
import kul.pl.biblioteka.repository.BookRepository;
import kul.pl.biblioteka.utils.LibraryPage;
import kul.pl.biblioteka.utils.RandomPicker;
import kul.pl.biblioteka.utils.SortSetting;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static kul.pl.biblioteka.utils.Constants.LIMIT;
import static kul.pl.biblioteka.utils.SortSetting.*;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

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
        Pageable pageable = new LibraryPage(offset, limit, direction, sort.toString());
        return bookRepository.findAll(pageable);
    }

    public Page<LibraryBook> searchBooks(int offset, int limit, String titleQuery, String authorQuery, String publisherQuery){
        Pageable pageable = new LibraryPage(offset, limit, ASC, TITLE.toString());
        return bookRepository.searchBooks(titleQuery, authorQuery, publisherQuery, pageable);
    }

    public Page<LibraryBook> discoverBooks(int limit){
        Pageable pageable = new LibraryPage(0, 50, DESC, POPULARITY.toString());
        Page<LibraryBook> page = bookRepository.findAll(pageable);
        return new PageImpl<>(RandomPicker.pick(new ArrayList<>(page.getContent()), limit), pageable, limit);
    }

    public Optional<LibraryBook> getBookById(long bookId){
        Optional<LibraryBook> book = bookRepository.findById(bookId);
        if(book.isEmpty()) throw new ResourceNotFoundException("Book with given ID does not exist in database");
        else return book;
    }

    public long availableCopies(long bookId){
        if(!bookRepository.existsById(bookId)) throw new ResourceNotFoundException("Book with given ID does not exist in database");
        return copiesRepository.availableCopies(bookId);
    }
}
