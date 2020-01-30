package com.stop.test;

import com.stop.model.Bot;
import com.stop.model.Branch;
import com.stop.model.Chat;
import com.stop.model.ChatRoom;
import com.stop.model.User;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class ChatRoomTest extends BaseRepositoryTest {

  @Test
  public void createChatroom() {
    User user = userRepository.findById(1L).get();
    Bot bot = botRepository.findById(2L).get();
    Branch branch = branchRepository.findById(4L).get();
    ChatRoom chatRoom = new ChatRoom();
    chatRoom.setBot(bot);
    chatRoom.setBranch(branch);
    chatRoom.setUser(user);
    chatRoom.setCreated(new Date());
    ChatRoom saved = chatRoomRepository.save(chatRoom);
    Assert.assertTrue(saved.getId() > 0);

    Chat chat = new Chat();
    chat.setChatRoom(saved);
    chat.setMessage("Hello!");
    chat.setSendDate(new Date());
    chat.setType("newmsg");
    chatRepository.save(chat);

    PageRequest page = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "sendDate"));
    List<Chat> chats = chatRepository.findAllByChatRoom(saved, page);
    Assert.assertEquals(1, chats.size());
  }

}
