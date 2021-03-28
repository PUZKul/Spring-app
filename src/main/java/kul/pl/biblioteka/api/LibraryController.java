package kul.pl.biblioteka.api;

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
    public Optional<LibraryBook> getBookById(@PathVariable("id") int id){
        return service.getBookById(id);
    }
}
