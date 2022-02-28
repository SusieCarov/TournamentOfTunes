package com.tournamentoftunes.services;

import com.tournamentoftunes.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  public User updateUserName(String username) {
    return new User();
  }

}
