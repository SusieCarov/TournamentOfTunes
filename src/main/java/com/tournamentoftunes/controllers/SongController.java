package com.tournamentoftunes.controllers;


import com.tournamentoftunes.services.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// TODO: move all v1s to end of URL?

@Slf4j
@RestController
@RequestMapping(value = "v1/song")
public class SongController {

  @Autowired
  private SongService songService;

  // TODO: Best naming per REST conventions
  //  also, should this be link in body or path variable
  //  also, also, is PUT the best request method?
  @RequestMapping(value = "{songName}", method = RequestMethod.PUT)
  public String submitSong(@PathVariable("songName") String songName) {
    // TODO: need user and tournament ids

    log.info("Submitted song name: {}", songName);
    return "This is the put";
  }

}
