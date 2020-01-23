package com.stop.api.service;

import com.stop.dto.ChatRoomDto;
import com.stop.repository.ChatRoomRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomService {

  @Autowired
  private ChatRoomRepository chatRoomRepository;

  public List<ChatRoomDto> listAllChatRooms() {
    // TODO Auto-generated method stub
    return null;
  }

}
