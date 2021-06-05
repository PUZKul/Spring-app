package kul.pl.biblioteka.repository;

import kul.pl.biblioteka.model.LibraryUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;
import javax.transaction.Transactional;

public interface LibraryUserRepository extends CrudRepository<LibraryUser, UUID> {

  @Query("SELECT u FROM LibraryUser u WHERE u.username = :username")
  LibraryUser getUserByUsername(@Param("username") String username);

  @Query(value = "SELECT COUNT(*) FROM users u WHERE email = ?", nativeQuery = true)
  int isEmailExist(String email);

  @Transactional
  @Modifying
  @Query(value = "UPDATE users SET email = ?, password = ? WHERE nick = ?", nativeQuery = true)
  int editUserData(String email, String password, String username);

  @Transactional
  @Modifying
  @Query(value = "UPDATE users SET email = ? WHERE nick = ?", nativeQuery = true)
  int updateEmail(String email, String username);

  @Transactional
  @Modifying
  @Query(value = "UPDATE users SET password = ? WHERE nick = ?", nativeQuery = true)
  int updatePassword(String password, String username);

  @Transactional
  @Modifying
  @Query(value = "UPDATE users SET first_name = ? WHERE nick = ?", nativeQuery = true)
  int updateFirstName(String firstName, String username);

  @Transactional
  @Modifying
  @Query(value = "UPDATE users SET last_name = ? WHERE nick = ?", nativeQuery = true)
  int updateLastName(String lastName, String username);

  @Transactional
  @Modifying
  @Query(value = "UPDATE users SET address = ? WHERE nick = ?", nativeQuery = true)
  int updateAddress(String address, String username);

  @Transactional
  @Modifying
  @Query(value = "UPDATE users SET phone = ? WHERE nick = ?", nativeQuery = true)
  int updatePhone(String phone, String username);

  @Query("SELECT u.username FROM LibraryUser u WHERE u.id = :id")
  String getUsername(@Param("id") UUID userId);

  @Transactional
  @Modifying
  @Query(value = "UPDATE users SET is_banned = true WHERE id = ?", nativeQuery = true )
  void setBanned(UUID userId);

  @Transactional
  @Modifying
  @Query(value = "UPDATE users SET warnings = warnings + 1 WHERE id=?", nativeQuery = true)
  void addWarning(UUID userId);

  @Query("SELECT u.maxBooks FROM LibraryUser u WHERE u.id = :userId")
  int getBookLimit(@Param("userId") UUID userId);

  @Transactional
  @Modifying
  @Query(value = "UPDATE users SET max_books = ? WHERE id = ?", nativeQuery = true )
  void changeBookLimit(int limit, UUID userId);

  @Transactional
  @Modifying
  @Query(value = "UPDATE users SET comment = ? WHERE id = ?", nativeQuery = true )
  void setComment(String comment, UUID userId);

  @Query("SELECT u FROM LibraryUser  u where u.username like :username%")
  Page<LibraryUser> findAllByName(@Param("username") String username, Pageable pageable);

  @Query(value = "SELECT u FROM LibraryUser u")
  Page<LibraryUser> findAll(Pageable pageable);

  @Query(value = "Select is_banned from users where nick = ?", nativeQuery = true)
  boolean isBanned(String username);

  @Transactional
  @Modifying
  @Query(value = "UPDATE users SET comment = ? WHERE nick = ?", nativeQuery = true )
  void setComment(String comment, String username);

  @Transactional
  @Modifying
  @Query(value = "update users set warnings = 0 where nick = ?", nativeQuery = true)
  void cleanWarnings(String username);

  @Transactional
  @Modifying
  @Query(value = "update users set is_banned = false where nick = ?", nativeQuery = true)
  void setUnBanned(String username);
}
