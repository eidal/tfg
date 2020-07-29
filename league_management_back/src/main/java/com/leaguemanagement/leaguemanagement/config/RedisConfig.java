package com.leaguemanagement.leaguemanagement.config;

import com.leaguemanagement.leaguemanagement.utils.LeagueManagementConsts;
import java.time.Duration;
import java.util.Optional;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Slf4j
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

  @Autowired
  @Qualifier(LeagueManagementConsts.EXPIRE_ONE_DAY_CACHE_MANAGER)
  private CacheManager expireOneDayCacheManager;

  private void clearCache(CacheManager manager, String cache) {
    Optional.ofNullable(manager.getCache(cache))
        .ifPresent(Cache::clear);
  }

  /**
   * Set up redis config.
   * This will clear the associated cache on startup.
   */
  @PostConstruct
  public void setUp() {
    log.info("Flushing cache");
    clearCache(expireOneDayCacheManager, LeagueManagementConsts.COMPETITION_CACHE_NAME);
    clearCache(expireOneDayCacheManager, LeagueManagementConsts.TEAM_CACHE_NAME);
    clearCache(expireOneDayCacheManager, LeagueManagementConsts.TEAM_SEASON_CACHE_NAME);
    clearCache(expireOneDayCacheManager, LeagueManagementConsts.TEAM_SEASON_PLAYER_CACHE_NAME);
    clearCache(expireOneDayCacheManager, LeagueManagementConsts.PLAYER_CACHE_NAME);
    clearCache(expireOneDayCacheManager, LeagueManagementConsts.REFEREE_CACHE_NAME);
    clearCache(expireOneDayCacheManager, LeagueManagementConsts.COMPETITION_CACHE_NAME);
    clearCache(expireOneDayCacheManager, LeagueManagementConsts.MATCH_CACHE_NAME);
    clearCache(expireOneDayCacheManager, LeagueManagementConsts.SEASON_CACHE_NAME);
  }

  /**
   * RedisCacheConfiguration with 24 hours of TTL.
   *
   * @return redis cache configuration
   */
  @Bean
  public RedisCacheConfiguration oneDayRedisCacheConfiguration() {
    return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(24L))
        .disableCachingNullValues();
  }

  /**
   * Create a RedisCacheManager with RedisConnectionFactory and
   * one day redis cache configuration.
   *
   * @param connectionFactory connection factory
   * @return redis cache manager
   */
  @Primary
  @Bean(name = LeagueManagementConsts.EXPIRE_ONE_DAY_CACHE_MANAGER)
  public RedisCacheManager expireOneDayCacheManager(RedisConnectionFactory connectionFactory) {
    return RedisCacheManager.builder(connectionFactory)
        .cacheDefaults(oneDayRedisCacheConfiguration()).build();
  }

}
