package kul.pl.biblioteka.repository;

import kul.pl.biblioteka.model.Message;
import kul.pl.biblioteka.utils.MessageStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface MessageRepository extends JpaRepository<Message, Long>{

  @Query(value = "SELECT * from messages where title LIKE 'LIMIT'  and status LIKE 'PENDING'", nativeQuery = true)
  Page<Message> findAllRequests(Pageable pageable);

  @Query(value = "SELECT * from messages where title LIKE 'LIMIT' and author = ? and status LIKE 'PENDING'", nativeQuery = true)
  Page<Message> findAllRequestsByUsername(String username, Pageable pageable);

  @Transactional
  @Modifying
  @Query("UPDATE Message m set m.status = :status where m.id = :id")
  void changeStatus(@Param("status") MessageStatus status, @Param("id") long id);
}
