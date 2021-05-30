package kul.pl.biblioteka.model;

import lombok.Builder;
import lombok.Getter;

import kul.pl.biblioteka.utils.MessageStatus;
import kul.pl.biblioteka.utils.MessageType;

import java.util.Date;
import javax.persistence.*;

@Entity
@Getter
@Table(name = "messages")
public class Message {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String author;
  private MessageType title;
  private String message;
  private Date dateIssued;
  private MessageStatus status;

  public Message() {
  }

  @Builder
  public Message(
      String author, MessageType title, String message, Date dateIssued,
      MessageStatus status) {
    this.author = author;
    this.title = title;
    this.message = message;
    this.dateIssued = dateIssued;
    this.status = status;
  }
}
