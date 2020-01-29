package com.stop.api.controller;

import static com.stop.api.utils.StopConstants.CHATROOM_SERVICE_BASE_PATH;

import com.stop.api.service.ChatRoomService;
import com.stop.api.utils.ResponseUtils;
import com.stop.dto.ChatRoomDto;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @GetMapping("/find/{userId}/{branchId}/{botId}")
  @ResponseBody
  public ChatRoomDto findChatRoom(@PathVariable Long userId, @PathVariable Long branchId,
      @PathVariable Long botId) {
    return chatRoomService.findChatRoom(userId, branchId, botId);
  }

  @GetMapping("/find/bybot/{botId}")
  @ResponseBody
  public List<ChatRoomDto> findByBot(@PathVariable Long botId) {
    return chatRoomService.findByBot(botId);
  }

  @PostMapping
  @ResponseBody
  public ResponseEntity<ChatRoomDto> createChatRoom(HttpServletRequest httpReq,
      @RequestBody ChatRoomDto req) {
    ChatRoomDto resp = chatRoomService.createChatRoom(req);
    return ResponseEntity.created(ResponseUtils.getCreationUri(httpReq, resp.getId())).body(resp);
  }

}
