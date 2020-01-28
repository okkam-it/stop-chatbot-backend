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

  // GET ALL
  @GetMapping
  @ResponseBody
  public List<UserDto> listUsers() {
    return userService.listAllUsers();
  }

  @GetMapping("/find/byname")
  @ResponseBody
  public UserDto findByName(@RequestParam String name) {
    return userService.findByName(name);
  }

  @GetMapping("/find/bybranch/{branchId}")
  @ResponseBody
  public List<UserDto> findByBranch(@PathVariable Long branchId) {
    return userService.findByBranch(branchId);
  }

  @PostMapping
  @ResponseBody
  public ResponseEntity<UserDto> createUser(HttpServletRequest httpReq, @RequestBody UserDto req) {
    UserDto resp = userService.createUser(req);
    return ResponseEntity.created(ResponseUtils.getCreationUri(httpReq, resp.getId())).body(resp);
  }

  @PutMapping("/{id}")
  @ResponseBody
  public GenericResponse updateBot(HttpServletRequest httpReq, @PathVariable Long id,
      @RequestBody UserDto req) {
    return userService.updateUser(id, req);
  }

  @DeleteMapping("/{id}")
  @ResponseBody
  public GenericResponse deleteUser(@PathVariable Long id) {
    return userService.delete(id);
  }

  @PostMapping("/{id}/addbranch")
  @ResponseBody
  public GenericResponse addBranchToUser(HttpServletRequest httpReq, @PathVariable Long id,
      @RequestParam String code) {
    GenericResponse resp = userService.addBranchToUser(id, code);
    return resp;
  }

}
