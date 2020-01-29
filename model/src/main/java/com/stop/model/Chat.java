package com.stop.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chat")
public class Chat {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Long id;
  private String message;
  private Date sendDate;
  private String type;
  private String user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chatroom_id", nullable = false)
  private ChatRoom chatRoom;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Date getSendDate() {
    return sendDate;
  }

  public void setSendDate(Date sendDate) {
    this.sendDate = sendDate;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public ChatRoom getChatRoom() {
    return chatRoom;
  }

  public void setChatRoom(ChatRoom chatRoom) {
    this.chatRoom = chatRoom;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

}
