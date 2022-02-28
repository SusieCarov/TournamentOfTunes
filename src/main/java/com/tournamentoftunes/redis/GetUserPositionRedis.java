
package com.tournamentoftunes.redis;

import com.tournamentoftunes.domain.Leaderboard;
import com.tournamentoftunes.domain.User;
import redis.clients.jedis.Jedis;

public class GetUserPositionRedis {

  private String globalLeaderboard = "globalLeaderboard";

  public Leaderboard getMyLeaderboardPosition(String userId) {

    Leaderboard leaderboard = null;
    Jedis jedis = null;

    try {
      // Getting a connection from the pool
      jedis = JedisConfiguration.getPool().getResource();

      // Getting the user properties from the hash set
      User user = this.getUserFromHashSet(userId, jedis);

      if (user != null) {
        // zrevrank retrieves the user position at the sorted set
        Long position = jedis.zrevrank(globalLeaderboard, userId);

        // Adding one to the position because it starts at 0
        if (position != null) {
          position = position + 1;
        }

        // zscore retrieves the user current score
        Double value = jedis.zscore(globalLeaderboard, userId);

        // If both values found, create a Leaderboard object
        if (position == null || value == null) {
          leaderboard = null;
        } else {
          leaderboard = new Leaderboard();
          leaderboard.setPosition(position);
          leaderboard.setScore(value);
          leaderboard.setUser(user);
        }

      }
    } catch (Exception ex) {

    } finally {
      if (jedis != null) {
        jedis.close();
      }
    }

    return leaderboard;
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