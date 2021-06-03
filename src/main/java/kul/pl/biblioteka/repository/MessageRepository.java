package kul.pl.biblioteka.repository;

import kul.pl.biblioteka.model.Message;
import kul.pl.biblioteka.utils.MessageStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface MessageRepository extends JpaRepository<Message, Long>{

  @Query(value = "SELECT * from messages where title LIKE 'LIMIT' ", nativeQuery = true)
  Page<Message> findAllRequests(Pageable pageable);

  @Query(value = "SELECT * from messages where title LIKE 'LIMIT' and author = ?", nativeQuery = true)
  Page<Message> findAllRequestsByUsername(String username, Pageable pageable);

  @Transactional
  @Modifying
  @Query(value = "UPDATE messages set status=? where id = ?", nativeQuery = true)
  void changeStatus(MessageStatus status, long id);
}
