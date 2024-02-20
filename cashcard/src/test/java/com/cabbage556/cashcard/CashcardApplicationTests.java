package com.cabbage556.cashcard;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//		스프링 부트 애플리케이션을 실행해 테스트에서 요청을 수행할 수 있게 해줌
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CashcardApplicationTests {

	// 로컬에서 실행하는 애플리케이션에 HTTP 요청을 할 수 있도록 테스트 헬퍼 주입
	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void shouldReturnACashCardWhenDataIsSaved() {
		// /cashcard/99 엔드포인트로 HTTP GET 요청
		//		restTemplate은 요청 결과에 대해 ResponseEntity 리턴
		ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/99", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		// String 응답을 JSON 인식 객체로 변환함, 다양한 헬퍼 메서드를 포함함
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Number id = documentContext.read("$.id");  // JSON 인식 객체로부터 id 필드의 값 읽기
		assertThat(id).isEqualTo(99);

		Double amount = documentContext.read("$.amount");  // JSON 인식 객체로부터 amount 필드의 값 읽기
		assertThat(amount).isEqualTo(123.45);
	}

	@Test
	void shouldNotReturnACashCardWithAnUnknownId() {
		ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/1000", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isBlank();
	}

}
