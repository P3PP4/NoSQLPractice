## redis 설치

https://github.com/microsoftarchive/redis/releases

## redis 실행

설치 폴더로 들어가서 redis-cli를 실행해본다!!</br>
![image](https://github.com/P3PP4/NoSQLPractice/assets/109469241/c4cb4299-0e36-4716-8773-c7c46e38facf)

## RedisConfig.java

```java

@Configuration
public class RedisConfig {

  @Value("${spring.redis.host}")
  private String host;
  
  @Value("${spring.redis.port}")
  private String port;
  
  @Value("${spring.redis.password}")
  private String password;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
    redisStandaloneConfiguration.setHostName(host);
    redisStandaloneConfiguration.setPort(Integer.parseInt(port));
    redisStandaloneConfiguration.setPassword(password);
    LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
    
    return lettuceConnectionFactory;
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory());

    // 일반적인 key-value 경우의 serializer
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());

    // hash를 사용할 경우의 serializer
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(new StringRedisSerializer());

    // 모든 경우 가능한 serializer
    redisTemplate.setDefaultSerializer(new StringRedisSerializer());

    return redisTemplate;
  }
  
}

```

## application.properties

```java

spring.redis.host=localhost
spring.redis.password=
spring.redis.port=6379

```
