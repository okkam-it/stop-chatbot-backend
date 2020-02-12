package com.stop.api.controller;

import com.stop.api.service.BotService;
import com.stop.api.service.ChatRoomService;
import com.stop.dto.ChatDto;
import com.stop.dto.ChatRoomDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.server.ResponseStatusException;

@Controller
@CrossOrigin
public class ChatController {

  private static final Logger LOG = LoggerFactory.getLogger(ChatController.class);

  @Autowired
  private ChatRoomService chatRoomService;

  @Autowired
  private BotService botService;

  /**
   * Send a message to a bot, retrieve a response.
   * 
   * @param message a message from a user
   * @return bot response
   * @throws Exception if an error occurs
   */
  @MessageMapping("/bot/request/{chatRoomId}")
  @SendTo("/bot/response")
  public ChatDto greeting(@DestinationVariable Long chatRoomId, ChatDto message) throws Exception {
    LOG.debug("Websocket called");
    ChatRoomDto chatRoomDto = chatRoomService.findById(chatRoomId);
    if (chatRoomDto != null) {
      chatRoomService.saveChat(chatRoomDto.getId(), message);
      ChatDto response = botService.sendMessageToBot(chatRoomDto.getBotId(), message);
      chatRoomService.saveChat(chatRoomDto.getId(), response);
      return response;
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ChatRoom not found.");
    }
  }

}
