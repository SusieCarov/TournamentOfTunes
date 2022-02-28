
package com.tournamentoftunes.redis;

import com.tournamentoftunes.domain.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import redis.clients.jedis.Jedis;

public class InitLeaderboardRedis {

  private String globalLeaderboard = "globalLeaderboard";

  public void jedisInit(List<User> userList) {
    Jedis jedis = null;
    try {

      // Getting a connection from the pool
      jedis = JedisConfiguration.getPool().getResource();

      Map<String, Double> map = new HashMap<>();

      // For each user, creates a hash set and put in a map
      for (User user : userList) {
        map.put(user.getId(), 0.0);
        jedis.hset(user.getId(), "id", user.getId());
        jedis.hset(user.getId(), "username", user.getUsername());

      }

      // Adding all the keys(userId) related to value(score) in our sorted list
      jedis.zadd(globalLeaderboard, map);

    } catch (Exception e) {
      e.printStackTrace();

    } finally {
      if (jedis != null) {
        // Closing connection to
        jedis.close();
      }
    }

  }
}