package com.tournamentoftunes.domain;

import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class Round {

  private List<Song> songs;
  private String category;
  private Date submissionsOpenDate;
  private Date submissionsCloseDate;
  private Date votingOpenDate;
  private Date votingCloseDate;

}
