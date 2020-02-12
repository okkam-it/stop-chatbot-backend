package com.stop.api.service;

import com.stop.dto.UserDto;
import com.stop.model.Branch;
import com.stop.model.User;
import com.stop.repository.BranchRepository;
import com.stop.repository.UserRepository;
import com.stop.response.GenericResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BranchRepository branchRepository;

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
    userDto.setUsername(user.getUsername());
    userDto.setEmail(user.getEmail());
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
    User found = userRepository.findOneByUsername(req.getUsername());
    if (found == null) {
      User user = new User();
      user.setUid(req.getUid());
      user.setAdmin(req.isAdmin());
      user.setCreated(new Date());
      user.setUsername(req.getUsername());
      user.setEmail(req.getEmail());
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
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
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
  public UserDto updateUser(Long id, UserDto req) {
    try {
      User user = userRepository.findById(id).get();
      user.setAdmin(req.isAdmin());
      User updated = userRepository.save(user);
      return convertUserToDto(updated);
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.", e);
    }
  }

  /**
   * Find a user by name.
   * 
   * @param name user name
   * @return user in db
   */
  public UserDto findByName(String name) {
    User user = userRepository.findOneByUsername(name);
    if (user != null) {
      return convertUserToDto(user);
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
  }

  /**
   * Add a branch to an user.
   * 
   * @param uid user uid
   * @param code branch code
   * @return response
   */
  public GenericResponse addBranchToUser(String uid, String code) {
    GenericResponse response = new GenericResponse();
    try {
      User user = userRepository.findOneByUid(uid);
      Branch branch = branchRepository.findOneByCode(code);
      if (branch == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid branch code.");
      }
      if (user.getBranches().contains(branch)) {
        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "This code already exists.");
      }
      user.getBranches().add(branch);
      userRepository.save(user);
      branch.getUsers().add(user);
      branchRepository.save(branch);
      response.setMessage("OK");
    } catch (NoSuchElementException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.", ex);
    }
    return response;
  }

  /**
   * Find users by branch.
   * 
   * @param branchId branch id
   * @return users associated to the branch
   */
  public List<UserDto> findByBranch(Long branchId) {
    Branch branch = branchRepository.findById(branchId).get();
    if (branch == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found.");
    }
    Set<User> users = branch.getUsers();
    List<UserDto> response = new ArrayList<>();
    for (User user : users) {
      response.add(convertUserToDto(user));
    }
    return response;
  }

  /**
   * Find a user given its uid.
   * 
   * @param uid user uid
   * @return user
   */
  public UserDto findByUid(String uid) {
    User user = userRepository.findOneByUid(uid);
    if (user != null) {
      return convertUserToDto(user);
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
  }

  /**
   * Retrieve a user given its id.
   * 
   * @param id user id
   * @return user if found
   */
  public UserDto findById(long id) {
    try {
      User user = userRepository.findById(id).get();
      return convertUserToDto(user);
    } catch (NoSuchElementException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.", ex);
    }
  }
}
