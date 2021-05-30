package kul.pl.biblioteka.repository;

import kul.pl.biblioteka.model.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {
}
