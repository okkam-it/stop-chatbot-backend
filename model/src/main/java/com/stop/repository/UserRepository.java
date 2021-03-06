package com.stop.repository;

import com.stop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  public User findOneByUsername(String username);

  public User findOneByUid(String uid);

}
