package com.stop.repository;

import com.stop.model.Chat;
import com.stop.model.ChatRoom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {

  public List<Chat> findAllByChatRoom(ChatRoom chatRoom);

}
