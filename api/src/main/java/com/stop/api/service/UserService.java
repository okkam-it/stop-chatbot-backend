package com.stop.api.service;

import com.stop.dto.UserDto;
import com.stop.model.User;
import com.stop.repository.UserRepository;
import com.stop.response.GenericResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
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
    userDto.setUid(user.getUid());
    userDto.setName(user.getName());
    userDto.setAdmin(user.isAdmin());
    return userDto;
  }

  /**
   * Create a new user.
   * 
   * @param req user to create
   * @return user created
   */
  public UserDto createUser(UserDto req) {
    User found = userRepository.findUserByName(req.getName());
    if (found == null) {
      User user = new User();
      user.setUid(req.getUid());
      user.setAdmin(req.isAdmin());
      user.setCreated(new Date());
      user.setName(req.getName());
      User saved = userRepository.save(user);
      return convertUserToDto(saved);
    }
    return convertUserToDto(found);
  }

  /**
   * Delete a user.
   * 
   * @param id id of the user to delete
   * @return OK if the user was deleted successfully
   */
  public GenericResponse delete(Long id) {
    GenericResponse response = new GenericResponse();
    User user = userRepository.findById(id).get();
    if (user == null) {
      response.setMessage("KO");
      return response;
    }
    userRepository.deleteById(id);
    response.setMessage("OK");
    return response;
  }

  /**
   * Update user.
   * 
   * @param id id of the user to update
   * @param req the user to update
   * @return OK if the user was updated successfully
   */
  public GenericResponse updateUser(Long id, UserDto req) {
    GenericResponse response = new GenericResponse();
    try {
      User user = userRepository.findById(id).get();
      user.setAdmin(req.isAdmin());
      userRepository.save(user);
      response.setMessage("OK");
    } catch (NoSuchElementException e) {
      response.setMessage("KO");
    }
    return response;
  }

  /**
   * Find a user by name.
   * 
   * @param name user name
   * @return user in db
   */
  public UserDto findByName(String name) {
    User user = userRepository.findUserByName(name);
    if (user != null) {
      return convertUserToDto(user);
    }
    return null;
  }
}
