package com.systemk.spyder.Config.Redis;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 43200)
public class RedisSessionConfig {

}
