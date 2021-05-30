package kul.pl.biblioteka.schedule;

import lombok.RequiredArgsConstructor;

import kul.pl.biblioteka.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class SchedulerConfiguration {

  private final LibraryUserRepository userRepository;
  private final ReservationRepository reservationRepository;
  private final UserBookRepository userBookRepository;
  private final BookCopiesRepository copiesRepository;
  private final BlackListRepository blackListRepository;

  @Bean
  Scheduler scheduler(){
    return new TaskScheduler(
        userRepository,
        reservationRepository,
        userBookRepository,
        copiesRepository,
        blackListRepository
    );
  }
}
