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
|변수|기본값|설명|
|---|:---:|---|
|spring.redis.database|0|커넥션 팩토리에 사용되는 데이터베이스 인덱스
|spring.redis.host|localhost|레디스 서버 호스트
|spring.redis.password||레디스 서버 로그인 패스워드
|spring.redis.pool.max-active|8|pool에 할당될 수 있는 커넥션 최대수 (음수로 하면 무제한)
|spring.redis.pool.max-idle|8|pool의 "idle" 커넥션 최대수 (음수로 하면 무제한)
|spring.redis.pool.max-wait|-1|pool이 바닥났을 때 예외발생 전에 커넥션 할당 차단의 최대 시간 (단위: 밀리세컨드, 음수는 무제한 차단)
|spring.redis.pool.min-idle|0|풀에서 관리하는 idle 커넥션의 최소 수 대상 (양수일 때만 유효)
|spring.redis.port|6379|레디스 서버 포트
|spring.redis.sentinel.master||레디스 서버 이름
|spring.redis.sentinel.nodes||호스트:포트 쌍 목록 (콤마로 구분)
|spring.redis.timeout|0|커넥션 타임아웃 (단위: 밀리세컨드)
