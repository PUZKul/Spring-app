package kul.pl.biblioteka.service;

import kul.pl.biblioteka.model.BookCopy;
import kul.pl.biblioteka.repository.BookCopiesRepository;
import kul.pl.biblioteka.repository.BookRepository;
import kul.pl.biblioteka.repository.UserBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LibraryServiceTest {

    LibraryService service;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookCopiesRepository copiesRepository;

    @Mock
    private UserBookRepository historyRepository;

    @BeforeEach
    public void before(){
        MockitoAnnotations.openMocks(this);
        this.service = new LibraryService(bookRepository, copiesRepository, historyRepository);
    }


    @Test
    void getCopies_shouldReturnHolderWithDate(){
        //given
       // LibraryBook book = new LibraryBook(1, "Title", "author", 5, 50, 120, LocalDate.now(), "publisher", "url");
        BookCopy copy1 = new BookCopy();


    }

    @Test
    public void getBooks_shouldReturn2Books_whenGivenRequestIs2Books() throws ParseException {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
//        LibraryBook book = new LibraryBook(10, "harry", "ralong", 4.5, 50, 120, formatter.parse("01.02.2009"), "publisher", "url");
//        LibraryBook book2 = new LibraryBook(11, "Lorry", "ralong", 4.2, 43, 125, formatter.parse("01.02.2019"), "publisher", "url");
//        List<LibraryBook> libraryBooks = Arrays.asList(book, book2);
//
//        Sort sortMethod = Sort.by("title");
//        Pageable pageable = new LibraryPage(0, 2, sortMethod);
//
//        given(bookRepository.findAll(Mockito.any(PageRequest.class)).toList()).willReturn(libraryBooks);
//
//        List<LibraryBook> books = service.getBooks(SortSetting.TITLE, 0, 2);
//        assertEquals(2, books.size());
//        assertEquals(libraryBooks, books);
    }
}