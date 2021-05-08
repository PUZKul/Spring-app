package kul.pl.biblioteka.holder.creator;

import kul.pl.biblioteka.holder.UserBookHolder;
import kul.pl.biblioteka.model.UserBook;

import java.util.Collection;

//@RequiredArgsConstructor
//@Slf4j
public class HistoryHolderCreator implements Creator<UserBookHolder, UserBook>{

//  private final UserLibraryService service;
//
  @Override
  public Collection<UserBookHolder> create(Collection<UserBook> collection) {
    return null;
   // return collection.stream().map(this::apply).collect(Collectors.toList());
  }
//
//  private String getBookTitle(long id){
//    return service.getBookTitle(id);
//  }
//
//  private String getBookImage(long id){
//    return service.getBookImage(id);
//  }
//
//  private HistoryHolder apply(UserHistory e) {
//    long bookId = e.getBookCopy().getBookId();
//    return HistoryHolder.builder()
//        .id(e.getId())
//        .bookCopyId(e.getBookCopy().getId())
//        .userId(e.getUserId())
//        .bookId(bookId)
//        .dateIssued(e.getDateIssued())
//        .dateReturn(e.getDateReturn())
//        .title(getBookTitle(bookId))
//        .imageUrl(getBookImage(bookId))
//        .build();
//  }
}
