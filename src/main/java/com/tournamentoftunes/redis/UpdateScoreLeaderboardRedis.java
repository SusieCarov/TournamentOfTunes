
package com.tournamentoftunes.redis;

import redis.clients.jedis.Jedis;

public class UpdateScoreLeaderboardRedis {

  private String globalLeaderboard = "globalLeaderboard";

  public void updateScore(String userId, double score) {
    Jedis jedis = null;
    try {
      // Getting a connection from the pool
      jedis = JedisConfiguration.getPool().getResource();

      // Updating the user score with zadd. With zadd you overwrite the score.
      jedis.zadd(globalLeaderboard, score, userId);

      // With zincrby you add a value to the current score
      //jedis.zincrby(globalLeaderboard, score, userId);

    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      if (jedis != null) {
        jedis.close();
      }
    }

  }

}