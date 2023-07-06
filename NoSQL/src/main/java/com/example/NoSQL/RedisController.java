package com.example.NoSQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RedisController {
	
	// 코드를 확인하기 쉽게 하기 위해
	// service 단에서 로직을 처리하지 않고 바로 redisTemplate으로 controller에서 처리하게 했음
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	////////////////////
	// String get/set //
	////////////////////
	
	@GetMapping(value = "/getValue/{key}")
	public ResponseEntity<String> getRedisValue(@PathVariable String key) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		
		System.out.println("key : " + key + ", value : " + valueOperations.get(key));
		
		return ResponseEntity.status(HttpStatus.OK).body(valueOperations.get(key));
	}
	
	@PutMapping(value = "/setValue/{key}/{value}")
	public ResponseEntity<String> setRedisValue(@PathVariable String key, @PathVariable String value) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(key, value);
		
		System.out.println("key : " + key + ", value : " + valueOperations.get(key));
		
		return ResponseEntity.status(HttpStatus.OK).body(value);
	}
	
	//////////////////
	// List get/set //
	//////////////////
	
	@GetMapping(value = "/getList")
	public ResponseEntity<List> getListValue() {
		// List를 처리하기 위한 템플릿
		ListOperations<String, String> listOperations = redisTemplate.opsForList();
		
		// list라는 key로 저장된 리스트를 가져옴
		List<String> list = listOperations.range("list", 0, 3);
		
		System.out.println("key : " + "list" + ", value : " + list.toString());
		
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@PutMapping(value = "/setList")
	public ResponseEntity<Long> setListValue() {
		// List를 처리하기 위한 템플릿
		ListOperations<String, String> listOperations = redisTemplate.opsForList();
		
		// List는 아래처럼 양쪽으로 삽입,삭제가 가능
		listOperations.rightPush("list", "psw");
		listOperations.rightPush("list", "lys");
		listOperations.rightPush("list", "ysj");
		listOperations.leftPush("list", "LYS ENTERTAINMENT");
		
		System.out.println("key : " + "list" + ", size : " + listOperations.size("list"));
		
		return ResponseEntity.status(HttpStatus.OK).body(listOperations.size("list"));
	}
	
	// 기타 Set, Sorted Set, Map도 저장 가능
	
}
