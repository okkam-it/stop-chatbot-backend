package com.stop.dto;

import java.util.Date;

public class ChatDto implements Comparable<ChatDto> {

  private Long chatRoomId;
  private String type;
  private String message;
  private Date sendDate;
  private String user;

  public enum MessageType {
    CHAT, JOIN, LEAVE
  }

  public Long getChatRoomId() {
    return chatRoomId;
  }

  public void setChatRoomId(Long chatRoomId) {
    this.chatRoomId = chatRoomId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Date getSendDate() {
    return sendDate;
  }

  public void setSendDate(Date sendDate) {
    this.sendDate = sendDate;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  @Override
  public int compareTo(ChatDto o) {
    return sendDate.compareTo(o.getSendDate());
  }

}
