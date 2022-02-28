package com.tournamentoftunes.domain;

import lombok.Data;

@Data
public class Leaderboard {

  private User user;
  private long position;
  private Double score;
}
