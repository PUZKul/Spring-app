package kul.pl.biblioteka.service;

import kul.pl.biblioteka.model.LibraryBook;
import kul.pl.biblioteka.repository.BookRepository;
import kul.pl.biblioteka.utils.LibraryPage;
import kul.pl.biblioteka.utils.SortSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {
    private final BookRepository bookRepository;

    @Autowired
    public LibraryService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<LibraryBook> getBooks(SortSetting sort, int page, int limit){
        Sort sortMethod = Sort.by(sort.toString());
        Pageable pageable = new LibraryPage(page * limit, limit, sortMethod);

        return bookRepository.findAll(pageable).toList();
    }
}
