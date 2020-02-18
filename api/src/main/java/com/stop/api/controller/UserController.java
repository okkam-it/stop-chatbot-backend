package com.stop.api.controller;

import static com.stop.api.utils.StopConstants.USER_SERVICE_BASE_PATH;

import com.stop.api.service.UserService;
import com.stop.api.utils.ResponseUtils;
import com.stop.dto.UserDto;
import com.stop.response.GenericResponse;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
@RequestMapping(USER_SERVICE_BASE_PATH)
public class UserController {

  @Autowired
  private UserService userService;

  /**
   * Retrieve all configured users.
   * 
   * @return all users
   */
  @GetMapping
  @ResponseBody
  public List<UserDto> listUsers() {
    return userService.listAllUsers();
  }

  /**
   * Find a user by its username.
   * 
   * @param name username to check
   * @return an user
   */
  @GetMapping("/find/byname")
  @ResponseBody
  public UserDto findByName(@RequestParam String name) {
    return userService.findByName(name);
  }

  /**
   * Find all users associated to a branch.
   * 
   * @param branchId branch id
   * @return a list of users associated to the branch
   */
  @GetMapping("/find/bybranch/{branchId}")
  @ResponseBody
  public List<UserDto> findByBranch(@PathVariable Long branchId) {
    return userService.findByBranch(branchId);
  }

  /**
   * Find a user by its uid.
   * 
   * @param uid uid (firebase identifier)
   * @return user with the given uid
   */
  @GetMapping("/find/byuid/{uid}")
  @ResponseBody
  public UserDto findByUid(@PathVariable String uid) {
    return userService.findByUid(uid);
  }

  /**
   * Create a new user.
   * 
   * @param httpReq http request
   * @param req user to create
   * @return the user created
   */
  @PostMapping
  @ResponseBody
  public ResponseEntity<UserDto> createUser(HttpServletRequest httpReq, @RequestBody UserDto req) {
    UserDto resp = userService.createUser(req);
    return ResponseEntity.created(ResponseUtils.getCreationUri(httpReq, resp.getId())).body(resp);
  }

  /**
   * Update an user.
   * 
   * @param id id of the user to update.
   * @param req user to update
   * @return user updated
   */
  @PutMapping("/{id}")
  @ResponseBody
  public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto req) {
    return userService.updateUser(id, req);
  }

  /**
   * Delete an user.
   * 
   * @param id id of the user to delete
   * @return delete response
   */
  @DeleteMapping("/{id}")
  @ResponseBody
  public GenericResponse deleteUser(@PathVariable Long id) {
    return userService.delete(id);
  }

  /**
   * Add a branch to an user.
   * 
   * @param uid uid of the user
   * @param code branch code
   * @return OK if the operation was successful
   */
  @PostMapping("/{uid}/addbranch")
  @ResponseBody
  public GenericResponse addBranchToUser(@PathVariable String uid, @RequestParam String code) {
    return userService.addBranchToUser(uid, code);
  }

}
