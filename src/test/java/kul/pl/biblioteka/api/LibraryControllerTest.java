package kul.pl.biblioteka.api;

import com.sun.jdi.InternalException;
import kul.pl.biblioteka.service.LibraryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class LibraryControllerTest {

    private LibraryController underTest;

    @Mock
    private LibraryService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new LibraryController(service);
    }


    @Test
    void searchBooks_ShouldThrowIllegalArgumentException_When3ParametersAreNull() {
        // Given
        String title = null;
        String author = null;
        String publisher = null;


        assertThatThrownBy(() -> underTest.searchBooks(10, 0, title, author, publisher))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("At least one parameter (title, author or publisher) is required");
    }

}