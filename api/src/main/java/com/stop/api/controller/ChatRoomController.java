package com.stop.api.controller;

import static com.stop.api.utils.StopConstants.CHATROOM_SERVICE_BASE_PATH;

import com.stop.api.service.ChatRoomService;
import com.stop.api.utils.ResponseUtils;
import com.stop.dto.ChatDto;
import com.stop.dto.ChatRoomDto;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
@RequestMapping(CHATROOM_SERVICE_BASE_PATH)
public class ChatRoomController {

  @Autowired
  private ChatRoomService chatRoomService;

  /**
   * Retrieve all configured chat rooms.
   * 
   * @return all chat rooms
   */
  @GetMapping
  @ResponseBody
  public List<ChatRoomDto> listChatRooms() {
    return chatRoomService.listAllChatRooms();
  }

  /**
   * Find a chatroom given an user, branch and bot.
   * 
   * @param userId user id
   * @param branchId branch id
   * @param botId bot id
   * @return a chat room for the given parameters
   */
  @GetMapping("/find/{userId}/{branchId}/{botId}")
  @ResponseBody
  public ChatRoomDto findChatRoom(@PathVariable Long userId, @PathVariable Long branchId,
      @PathVariable Long botId) {
    return chatRoomService.findChatRoom(userId, branchId, botId);
  }

  /**
   * Find all chatrooms for a bot.
   * 
   * @param botId bot id
   * @return list of chatrooms
   */
  @GetMapping("/find/bybot/{botId}")
  @ResponseBody
  public List<ChatRoomDto> findByBot(@PathVariable Long botId) {
    return chatRoomService.findByBot(botId);
  }

  /**
   * Create a new chatroom.
   * 
   * @param httpReq http request
   * @param req chatroom to create
   * @return created chatroom
   */
  @PostMapping
  @ResponseBody
  public ResponseEntity<ChatRoomDto> createChatRoom(HttpServletRequest httpReq,
      @RequestBody ChatRoomDto req) {
    ChatRoomDto resp = chatRoomService.createChatRoom(req);
    return ResponseEntity.created(ResponseUtils.getCreationUri(httpReq, resp.getId())).body(resp);
  }

  /**
   * Retrieve chatroom chats.
   * 
   * @param id chatroom id
   * @param limit limit of the chats
   * @return list of the last *limit* chats
   */
  @GetMapping("/{id}/chats")
  @ResponseBody
  public List<ChatDto> findChats(@PathVariable Long id, @RequestParam Integer limit) {
    return chatRoomService.findChats(id, limit);
  }

}
