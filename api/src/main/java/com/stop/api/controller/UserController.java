package com.stop.api.controller;

import static com.stop.api.utils.StopConstants.USER_SERVICE_BASE_PATH;

import com.stop.api.service.UserService;
import com.stop.dto.UserDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
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

}
