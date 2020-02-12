package com.stop.test;

import com.stop.dto.ChatDto;
import com.stop.dto.ChatRoomDto;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ChatRoomTest extends BaseRepositoryTest {

  @Test
  public void createChatroom() {
    ChatRoomDto chatRoom = new ChatRoomDto();
    chatRoom.setBotId(2L);
    chatRoom.setBranchId(4L);
    chatRoom.setUserId(1L);
    ChatRoomDto saved = chatRoomService.createChatRoom(chatRoom);
    Assert.assertTrue(saved.getId() > 0);

    ChatDto chat = new ChatDto();
    chat.setMessage("Hello!");
    chat.setType("newmsg");
    chat.setSendDate(new Date());
    chatRoomService.saveChat(saved.getId(), chat);

    List<ChatDto> chats = chatRoomService.findChats(saved.getId(), 10);
    Assert.assertEquals(1, chats.size());
  }

}
