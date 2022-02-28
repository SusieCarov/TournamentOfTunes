package com.tournamentoftunes.redis;

import com.tournamentoftunes.domain.Leaderboard;
import com.tournamentoftunes.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

public class GetLeaderboardRedis {

  private String globalLeaderboard = "globalLeaderboard";

  public List<Leaderboard> getGlobalLeaderboardRedis() throws Exception {
    Jedis jedis = null;
    try {
      // Getting a connection from the pool
      jedis = JedisConfiguration.getPool().getResource();

      //The Tuple type has two values: key and score
      //zrevrange gets from the bigger score to the lower
      //It is being limited just for the first 100 positions
      //For all positions, pass 0 and -1
      Set<Tuple> tupleList = jedis.zrevrangeWithScores(globalLeaderboard, 0, 99);
      List<Leaderboard> leaderboardList = new ArrayList<Leaderboard>();
      int position = 0;

      //Iterating over the tuples
      for (Tuple tuple : tupleList) {
        //When using zrevrange you need to handle the positions
        position++;
        Leaderboard leaderboard = new Leaderboard();
        //Retrieving the user from hash set
        leaderboard.setUser(this.getUserFromHashSet(tuple.getElement(), jedis));
        //Setting the position
        leaderboard.setPosition(position);
        //Seting the score from the tuple
        leaderboard.setScore(tuple.getScore());

        //Adding the LeaderboardList
        leaderboardList.add(leaderboard);
      }
      return leaderboardList;
    } catch (Exception ex) {
      throw new Exception();

    } finally {
      if (jedis != null) {
        jedis.close();
      }
    }
  }

  public User getUserFromHashSet(String userId, Jedis jedis) {
    User user = null;

    try {
      jedis = JedisConfiguration.getPool().getResource();
      if (jedis.exists(userId)) {
        user = new User();
        user.setId(jedis.hget(userId, "id"));
        user.setUsername(jedis.hget(userId, "username"));

      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return user;
  }
}