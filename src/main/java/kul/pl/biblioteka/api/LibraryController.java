package kul.pl.biblioteka.api;

import kul.pl.biblioteka.model.BookCopy;
import kul.pl.biblioteka.model.LibraryBook;
import kul.pl.biblioteka.service.LibraryService;
import kul.pl.biblioteka.utils.SortSetting;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/library/books")
public class LibraryController {
    private final LibraryService service;

    @Autowired
    public LibraryController(LibraryService service) {
        this.service = service;
    }

    @GetMapping()
    public Page<LibraryBook> getBooks(@RequestParam(value = "limit") int limit,
                                      @RequestParam(value = "page") int page,
                                      @RequestParam(value = "sort") SortSetting sort,
                                      @RequestParam(value = "order", required = false) Sort.Direction direction)
    {
        int offset = page * limit;
        return service.getBooks(offset, limit, sort, Objects.requireNonNullElse(direction, Sort.Direction.ASC));
    }

    @GetMapping(path = "/id/{id}")
    public Optional<LibraryBook> getBookById(@PathVariable("id") long id){
        if(id < 0) throw new IllegalArgumentException("Id cannot not be less than 0");
        return service.getBookById(id);
    }

    @GetMapping(path = "/copies/available/{id}")
    public long availableCopies(@PathVariable("id") long bookId){
        if(bookId < 0) throw new IllegalArgumentException("Id cannot not be less than 0");
        return service.availableCopies(bookId);
    }

    @GetMapping(path = "/copies/{id}")
    public Page<BookCopy> getCopies(@PathVariable("id") long id){
        return service.getCopies(id);
    }


    @GetMapping(path = "/search")
    public Page<LibraryBook> searchBooks(@RequestParam(value = "limit") int limit,
                                         @RequestParam(value = "page") int page,
                                         @RequestParam(value = "title", required = false) String title,
                                         @RequestParam(value = "author", required = false) String author,
                                         @RequestParam(value = "publisher", required = false) String publisher){

        if(title==null && author == null && publisher == null)
            throw new IllegalArgumentException("At least one parameter (title, author or publisher) is required");

        int offset = page * limit;
        String titleQuery = Objects.requireNonNullElse(title, "");
        String authorQuery = Objects.requireNonNullElse(author, "");
        String publisherQuery = Objects.requireNonNullElse(publisher, "");

        return service.searchBooks(offset, limit, titleQuery, authorQuery, publisherQuery);
    }

    @GetMapping(path = "/discover/{limit}")
    public Page<LibraryBook> discoverBooks(@PathVariable("limit") int limit){
        return service.discoverBooks(limit);
    }




}
