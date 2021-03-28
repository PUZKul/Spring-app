package kul.pl.biblioteka.service;

import kul.pl.biblioteka.model.LibraryBook;
import kul.pl.biblioteka.repository.BookRepository;
import kul.pl.biblioteka.utils.LibraryPage;
import kul.pl.biblioteka.utils.SortSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static kul.pl.biblioteka.utils.Constants.LIMIT;

@Service
public class LibraryService {
    private final BookRepository bookRepository;

    @Autowired
    public LibraryService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<LibraryBook> getBooks(int offset, int limit, SortSetting sort, Sort.Direction direction){
        if(limit > LIMIT) throw new IllegalArgumentException(String.format("Given limit (%d) cannot be greater than %d", limit, LIMIT));
        Pageable pageable = new LibraryPage(offset, limit, direction, sort.toString());
        return bookRepository.findAll(pageable);
    }

    public Optional<LibraryBook> getBookById(int id){
        return bookRepository.findById(id);
    }
}
