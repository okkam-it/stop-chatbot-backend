package com.stop.api.service;

import com.stop.dto.ChatRoomDto;
import com.stop.model.Bot;
import com.stop.model.Branch;
import com.stop.model.ChatRoom;
import com.stop.model.User;
import com.stop.repository.BotRepository;
import com.stop.repository.BranchRepository;
import com.stop.repository.ChatRoomRepository;
import com.stop.repository.UserRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomService {

  @Autowired
  private ChatRoomRepository chatRoomRepository;

  @Autowired
  private BotRepository botRepository;

  @Autowired
  private BranchRepository branchRepository;

  @Autowired
  private UserRepository userRepository;

  /**
   * Retrieve all chatrooms.
   * 
   * @return all chatrooms
   */
  public List<ChatRoomDto> listAllChatRooms() {
    List<ChatRoom> chatRooms = chatRoomRepository.findAll();
    List<ChatRoomDto> result = new ArrayList<ChatRoomDto>();
    for (ChatRoom chatRoom : chatRooms) {
      result.add(convertChatRoomToDto(chatRoom));
    }
    return result;
  }

  /**
   * Find all chatrooms for a bot.
   * 
   * @param botId bot id
   * @return chatrooms open for that bot
   */
  public List<ChatRoomDto> findByBot(Long botId) {
    Bot bot = botRepository.findById(botId).get();
    List<ChatRoomDto> result = new ArrayList<ChatRoomDto>();
    for (ChatRoom chatRoom : bot.getChatRoom()) {
      result.add(convertChatRoomToDto(chatRoom));
    }
    return result;
  }

  private ChatRoomDto convertChatRoomToDto(ChatRoom chatRoom) {
    ChatRoomDto chatRoomDto = new ChatRoomDto();
    chatRoomDto.setBotId(chatRoom.getBot().getId());
    chatRoomDto.setUserId(chatRoom.getUser().getId());
    chatRoomDto.setBranchId(chatRoom.getBranch().getId());
    return chatRoomDto;
  }

  /**
   * Create a new chatroom.
   * 
   * @param req chatroom to create
   * @return the chatroom created
   */
  public ChatRoomDto createChatRoom(ChatRoomDto req) {
    try {
      Bot bot = botRepository.findById(req.getBotId()).get();
      Branch branch = branchRepository.findById(req.getBranchId()).get();
      User user = userRepository.findById(req.getUserId()).get();
      ChatRoom chatRoom = new ChatRoom();
      chatRoom.setBot(bot);
      chatRoom.setBranch(branch);
      chatRoom.setUser(user);
      chatRoom.setCreated(new Date());
      ChatRoom saved = chatRoomRepository.save(chatRoom);
      return convertChatRoomToDto(saved);
    } catch (NoSuchElementException e) {
      return null;
    }
  }

  /**
   * Find chatroom given a user, branch and bot.
   * 
   * @param userId user id
   * @param branchId branch id
   * @param botId bot id
   * @return chatroom
   */
  public ChatRoomDto findChatRoom(Long userId, Long branchId, Long botId) {
    try {
      Bot bot = botRepository.findById(botId).get();
      Branch branch = branchRepository.findById(branchId).get();
      User user = userRepository.findById(userId).get();
      ChatRoom chatRoom = chatRoomRepository.findOneByUserAndBranchAndBot(user, branch, bot);
      return convertChatRoomToDto(chatRoom);
    } catch (NoSuchElementException e) {
      return null;
    }
  }

}
