package kul.pl.biblioteka.service;

import kul.pl.biblioteka.holder.EditUserHolder;
import kul.pl.biblioteka.repository.LibraryUserRepository;
import kul.pl.biblioteka.utils.Validator;

import javax.persistence.EntityExistsException;

import static kul.pl.biblioteka.utils.Helper.isNullOrEmpty;

class UserDataUpdater {
  private final LibraryUserRepository repository;

  UserDataUpdater(LibraryUserRepository repository) {
    this.repository = repository;
  }

  void update(EditUserHolder user, String username){
    var email = user.getEmail().trim();
    var password = user.getNewPassword().trim();
    var firstName = user.getFirstName().trim();
    var address = user.getAddress().trim();
    var phone = user.getPhone().trim();
    var lastName = user.getLastName().trim();
    if (!isNullOrEmpty(email)){
      updateEmail(email, username);
    }
    if(!isNullOrEmpty(password)){
      updatePassword(password, username);
    }
    if(!isNullOrEmpty(firstName)){
      updateFirstName(firstName, username);
    }
    if(!isNullOrEmpty(lastName)){
      updateLastName(lastName, username);
    }
    if(!isNullOrEmpty(address)){
      updateAddress(address, username);
    }
    if(!isNullOrEmpty(phone)){
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
    repository.updatePassword(newPassword, username);
  }

  private void updateEmail(String email, String username) {
    if (!Validator.email(email)) throw new IllegalArgumentException("Given email '%s' is invalid");
    if (repository.isEmailExist(email) > 0)
      throw new EntityExistsException("User with given email already exist");
    repository.updateEmail(email, username);
  }
}
