package com.tournamentoftunes.controllers;

import com.tournamentoftunes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "v1/user/{userId}/")
public class UserServiceController {

  @Autowired
  private UserService userService;



}
