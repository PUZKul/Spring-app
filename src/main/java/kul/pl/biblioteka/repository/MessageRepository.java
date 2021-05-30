package kul.pl.biblioteka.repository;

import kul.pl.biblioteka.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, Long>{

  @Query(value = "SELECT * from messages where title LIKE 'Request%' ", nativeQuery = true)
  Page<Message> findAllRequests(Pageable pageable);

  @Query(value = "SELECT * from messages where title LIKE 'Request%' and autor = ?", nativeQuery = true)
  Page<Message> findAllRequestsByUsername(String username, Pageable pageable);
}
