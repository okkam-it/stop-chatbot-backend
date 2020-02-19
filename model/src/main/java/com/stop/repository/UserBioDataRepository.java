package com.stop.repository;

import com.stop.model.User;
import com.stop.model.UserBioData;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBioDataRepository extends JpaRepository<UserBioData, Long> {

  public List<UserBioData> findAllByUser(User user);
}
