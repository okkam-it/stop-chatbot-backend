package com.stop.repository;

import com.stop.model.Bot;
import com.stop.model.Branch;
import com.stop.model.ChatRoom;
import com.stop.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

  public List<ChatRoom> findAllByBot(Bot bot);

  public ChatRoom findOneByUserAndBranchAndBot(User user, Branch branch, Bot bot);

}
