package com.stop.dto;

import java.util.Date;

public class Chat {

  private MessageType type;
  private String content;
  private String sender;
  private String receiver;
  private Date sendDate;

  public enum MessageType {
    CHAT, JOIN, LEAVE
  }

  public MessageType getType() {
    return type;
  }

  public void setType(MessageType type) {
    this.type = type;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public Date getSendDate() {
    return sendDate;
  }

  public void setSendDate(Date sendDate) {
    this.sendDate = sendDate;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }
}
