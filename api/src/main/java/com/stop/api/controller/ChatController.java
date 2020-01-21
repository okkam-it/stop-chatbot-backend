package com.stop.api.controller;

import com.stop.api.utils.StopConstants;
import com.stop.dto.Chat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

  private static final Logger LOG = LoggerFactory.getLogger(ChatController.class);

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  /**
   * Send message.
   * 
   * @param msg message
   * @param sessionId session id
   */
  @MessageMapping(StopConstants.SECURED_CHAT_ROOM)
  public void sendSpecific(@Payload Chat msg, @Header("simpSessionId") String sessionId)
      throws Exception {
    LOG.info("Controlleeeeer");

    simpMessagingTemplate.convertAndSendToUser(msg.getReceiver(),
        StopConstants.SECURED_CHAT_SPECIFIC_USER, msg);

  }

}
