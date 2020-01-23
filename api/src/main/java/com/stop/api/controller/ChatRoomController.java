package com.stop.api.controller;

import static com.stop.api.utils.StopConstants.CHATROOM_SERVICE_BASE_PATH;

import com.stop.api.service.ChatRoomService;
import com.stop.dto.ChatRoomDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(CHATROOM_SERVICE_BASE_PATH)
public class ChatRoomController {

  @Autowired
  private ChatRoomService chatRoomService;

  // GET ALL
  @GetMapping
  @ResponseBody
  public List<ChatRoomDto> listUsers() {
    return chatRoomService.listAllChatRooms();
  }

}
