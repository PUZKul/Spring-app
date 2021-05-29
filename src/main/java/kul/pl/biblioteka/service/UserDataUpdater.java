package kul.pl.biblioteka.service;

import kul.pl.biblioteka.holder.EditUserHolder;
import kul.pl.biblioteka.repository.LibraryUserRepository;
import kul.pl.biblioteka.security.PasswordConfig;
import kul.pl.biblioteka.utils.Validator;

import java.util.Objects;
import javax.persistence.EntityExistsException;

import static org.apache.logging.log4j.util.Strings.isEmpty;

class UserDataUpdater {
  private final LibraryUserRepository repository;
  private final LibraryUserRepository userRepository;

  UserDataUpdater(
      LibraryUserRepository repository, LibraryUserRepository userRepository) {
    this.repository = repository;
    this.userRepository = userRepository;
  }

  void update(EditUserHolder user, String username){
    var email = Objects.requireNonNullElse(user.getEmail(), "").trim();
    var password = Objects.requireNonNullElse(user.getNewPassword(), "").trim();
    var firstName = Objects.requireNonNullElse(user.getFirstName(),"").trim();
    var address = Objects.requireNonNullElse(user.getAddress(),"").trim();
    var phone = Objects.requireNonNullElse(user.getPhone(), "").trim();
    var lastName = Objects.requireNonNullElse(user.getLastName(), "").trim();
    if (!isEmpty(email)){
      updateEmail(email, username);
    }
    if(!isEmpty(password)){
      updatePassword(password, username);
    }
    if(!isEmpty(firstName)){
      updateFirstName(firstName, username);
    }
    if(!isEmpty(lastName)){
      updateLastName(lastName, username);
    }
    if(!isEmpty(address)){
      updateAddress(address, username);
    }
    if(!isEmpty(phone)){
      updatePhone(phone, username);
    }
  }

  private void updatePhone(String phone, String username) {
    if(!Validator.phone(phone)) throw new IllegalArgumentException("Given phone is invalid");
    repository.updatePhone(phone, username);
  }

  private void updateAddress(String address, String username) {
    if(!Validator.address(address)) throw new IllegalArgumentException("Given address is invalid");
    repository.updateAddress(address, username);
  }

  private void updateLastName(String lastName, String username) {
    if(!Validator.name(lastName)) throw new IllegalArgumentException("Given last name is invalid");
    repository.updateLastName(lastName, username);
  }

  private void updateFirstName(String firstName, String username) {
    if(!Validator.name(firstName)) throw new IllegalArgumentException("Given first name is invalid");
    repository.updateFirstName(firstName, username);
  }

  private void updatePassword(String newPassword, String username) {
    if (!Validator.password(newPassword))
      throw new IllegalArgumentException("Given password is invalid");
    repository.updatePassword(PasswordConfig.encoder().encode(newPassword), username);
  }

  private void updateEmail(String email, String username) {
    if (!Validator.email(email)) throw new IllegalArgumentException("Given email '%s' is invalid");
    if (userRepository.getUserByUsername(username).getEmail().equals(email))
      return;
    if (repository.isEmailExist(email) > 0)
      throw new EntityExistsException("User with given email already exist");
    repository.updateEmail(email, username);
  }
}
