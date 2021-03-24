package kul.pl.biblioteka.api;

import com.sun.istack.NotNull;
import kul.pl.biblioteka.model.LibraryBook;
import kul.pl.biblioteka.service.LibraryService;
import kul.pl.biblioteka.utils.SortSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("api/library/books")
public class LibraryController {
    private final LibraryService service;

    @Autowired
    public LibraryController(LibraryService service) {
        this.service = service;
    }

    @GetMapping()
    public List<LibraryBook> getBooks(@NotNull @RequestParam("limit") int limit, @NotNull @RequestParam("page") int page, @RequestParam("sort") String sort){
        int offset = page * limit;
        SortSetting sortSetting = SortSetting.valueOf(sort);
        return service.getBooks(sortSetting, offset, limit);
    }

}
