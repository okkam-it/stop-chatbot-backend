package com.stop.dto;

public class ChatRoomDto {

  private Long id;
  private Long botId;
  private Long branchId;
  private Long userId;
  private boolean typing;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getBotId() {
    return botId;
  }

  public void setBotId(Long botId) {
    this.botId = botId;
  }

  public Long getBranchId() {
    return branchId;
  }

  public void setBranchId(Long branchId) {
    this.branchId = branchId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public boolean isTyping() {
    return typing;
  }

  public void setTyping(boolean typing) {
    this.typing = typing;
  }

}
