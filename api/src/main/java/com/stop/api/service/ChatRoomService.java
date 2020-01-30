package com.stop.api.service;

import com.stop.dto.ChatDto;
import com.stop.dto.ChatRoomDto;
import com.stop.model.Bot;
import com.stop.model.Branch;
import com.stop.model.Chat;
import com.stop.model.ChatRoom;
import com.stop.model.User;
import com.stop.repository.BotRepository;
import com.stop.repository.BranchRepository;
import com.stop.repository.ChatRepository;
import com.stop.repository.ChatRoomRepository;
import com.stop.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomService {

  @Autowired
  private ChatRoomRepository chatRoomRepository;

  @Autowired
  private ChatRepository chatRepository;

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

  /**
   * Find a chatroom by id.
   * 
   * @param chatRoomId chat room id
   * @return chatroom with the given id
   */
  public ChatRoomDto findById(Long chatRoomId) {
    try {
      ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).get();
      return convertChatRoomToDto(chatRoom);
    } catch (NoSuchElementException e) {
      return null;
    }
  }

  private ChatRoomDto convertChatRoomToDto(ChatRoom chatRoom) {
    ChatRoomDto chatRoomDto = new ChatRoomDto();
    chatRoomDto.setId(chatRoom.getId());
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
      if (chatRoom != null) {
        return convertChatRoomToDto(chatRoom);
      }
      return null;
    } catch (NoSuchElementException e) {
      return null;
    }
  }

  /**
   * Find chats in a chatroom.
   * 
   * @param id chatroom id
   * @param limit limit of chats
   * @return first *limit* chats in the chatroom
   */
  public List<ChatDto> findChats(Long id, Integer limit) {
    try {
      List<ChatDto> response = new ArrayList<>();
      ChatRoom chatRoom = chatRoomRepository.findById(id).get();
      if (chatRoom != null) {
        // get the last *limit* chats 
        PageRequest page = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "sendDate"));
        List<Chat> chats = chatRepository.findAllByChatRoom(chatRoom, page);
        for (Chat chat : chats) {
          response.add(convertChatToDto(chat));
        }
      }
      // sort from the oldest to the newer
      Collections.sort(response);
      return response;
    } catch (NoSuchElementException e) {
      return new ArrayList<>();
    }
  }

  private ChatDto convertChatToDto(Chat chat) {
    ChatDto chatDto = new ChatDto();
    chatDto.setUser(chat.getUser());
    chatDto.setType(chat.getType());
    chatDto.setMessage(chat.getMessage());
    chatDto.setSendDate(chat.getSendDate());
    return chatDto;
  }

  /**
   * Save a chat.
   * 
   * @param chatRoomId chat room of the chat
   * @param message chat to save
   */
  @Transactional
  public void saveChat(Long chatRoomId, ChatDto message) {
    ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).get();
    Chat chat = new Chat();
    chat.setChatRoom(chatRoom);
    chat.setMessage(message.getMessage());
    chat.setSendDate(new Date());
    chat.setType(message.getType());
    chat.setUser(message.getUser());
    Chat saved = chatRepository.save(chat);
    chatRoom.getChats().add(saved);
    chatRoomRepository.save(chatRoom);

  }

}
