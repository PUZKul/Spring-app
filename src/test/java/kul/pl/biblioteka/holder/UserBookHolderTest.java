package kul.pl.biblioteka.holder;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserBookHolderTest {

  private static final String TITLE = "BookTitle";
  private static final int BOOK_ID = 1;
  @Test
  void builder_shouldCreateHistoryHolderObject(){
    //when
    UserBookHolder build = UserBookHolder.builder()
        .id(BOOK_ID)
        .title(TITLE)
        .build();

    //then
    assertThat(build.getId()).isEqualTo(BOOK_ID);
    assertThat(build.getTitle()).isEqualTo(TITLE);
  }

}