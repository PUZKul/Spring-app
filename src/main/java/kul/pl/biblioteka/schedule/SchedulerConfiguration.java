package kul.pl.biblioteka.schedule;

import lombok.RequiredArgsConstructor;

import kul.pl.biblioteka.repository.BookCopiesRepository;
import kul.pl.biblioteka.repository.LibraryUserRepository;
import kul.pl.biblioteka.repository.ReservationRepository;
import kul.pl.biblioteka.repository.UserBookRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class SchedulerConfiguration {

  private final LibraryUserRepository userRepository;
  private final ReservationRepository reservationRepository;
  private final UserBookRepository userBookRepository;
  private final BookCopiesRepository copiesRepository;

  @Bean
  Scheduler scheduler(){
    return new TaskScheduler(
        userRepository,
        reservationRepository,
        userBookRepository,
        copiesRepository
    );
  }
}
