package com.stop.api.service;

import com.stop.dto.UserDto;
import com.stop.model.User;
import com.stop.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  /**
   * Retrieve a list of all users.
   * 
   * @return all users inserted
   */
  public List<UserDto> listAllUsers() {
    List<User> users = userRepository.findAll();
    List<UserDto> result = new ArrayList<>();
    for (User user : users) {
      result.add(convertUserToDto(user));
    }
    return result;
  }

  private UserDto convertUserToDto(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setName(user.getName());
    userDto.setAdmin(user.isAdmin());
    return userDto;
  }
}
