package com.cabbage556.cashcard;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)  // 테스트에서 요청을 수행할 수 있도록 스프링 부트 애플리케이션을 실행함
// @DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)  // 서로 다른 테스트들이 영향을 주지 않도록 스프링을 깨끗한 상태로 실행함(클레스 레벨 적용)
class CashcardApplicationTests {

    // 로컬에서 실행하는 애플리케이션에 HTTP 요청을 할 수 있도록 테스트 헬퍼 주입
    @Autowired
    TestRestTemplate restTemplate;

    // GET /cashcards/{requestedId} 테스트
    @Test
    void shouldReturnACashCardWhenDataIsSaved() {
        // /cashcard/99 엔드포인트로 HTTP GET 요청
        //		restTemplate은 요청 결과에 대해 ResponseEntity 리턴
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("sarah1", "abc123")  // Basic Authentication 추가
                .getForEntity("/cashcards/99", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // String 응답을 JSON 인식 객체로 변환함, 다양한 헬퍼 메서드를 포함함
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");  // JSON 인식 객체로부터 id 필드의 값 읽기
        assertThat(id).isEqualTo(99);

        Double amount = documentContext.read("$.amount");  // JSON 인식 객체로부터 amount 필드의 값 읽기
        assertThat(amount).isEqualTo(123.45);
    }

    // GET /cashcards/{requestedId} 테스트
    @Test
    void shouldNotReturnACashCardWithAnUnknownId() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .getForEntity("/cashcards/1000", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();
    }

    // POST /cashcards 테스트
    @Test
    @DirtiesContext  // 다른 테스트에 영향을 주지 않도록 CashCard 생성 후 클린 업(메서드 레벨 적용)
    void shouldCreateANewCashCard() {
        CashCard newCashCard = new CashCard(null, 250.00, null);
        ResponseEntity<Void> createResponse = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .postForEntity(  // POST 요청의 경우 CashCard를 돌려 받지 않으므로 Void 응답 바디로 선언함
                        "/cashcards",
                        newCashCard,
                        Void.class
                );
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);  // POST 요청 성공의 경우 서버는 201 CREATED 상태 코드를 응답해야 함

        URI locationOfNewCashCard = createResponse.getHeaders().getLocation();  // 201 CREATED 상태 코드는 Location 헤더 필드를 포함해야 함
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .getForEntity(locationOfNewCashCard, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // String 응답 -> JSON 인식 객체
        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("$.id");           // id 필드 값 읽기
        Double amount = documentContext.read("$.amount");   // amount 필드 값 읽기
        assertThat(id).isNotNull();
        assertThat(amount).isEqualTo(250.00);
    }

    // GET /cashcards 테스트
    @Test
    void shouldReturnAllCashCardsWhenListIsRequested() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .getForEntity("/cashcards", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int cashCardCount = documentContext.read("$.length()");  // 배열 길이 계산
        assertThat(cashCardCount).isEqualTo(3);

        JSONArray ids = documentContext.read("$..id");  // id 값들의 리스트 리턴
        assertThat(ids).containsExactlyInAnyOrder(99, 100, 101);  // 리스트가 포함해야 하는 id 값들 확인, 순서는 고려하지 않음

        JSONArray amounts = documentContext.read("$..amount");  // amount 값들의 리스트 리턴
        assertThat(amounts).containsExactlyInAnyOrder(123.45, 1.00, 150.00);  // 리스트가 포함해야 하는 amount 값들 확인, 순서는 고려하지 않음
    }

    // GET /cashcards?page=0&size=1 테스트
    @Test
    void shouldReturnAPageOfCashCards() {
        // 요청 URI에 pagination 정보를 포함하여 GET 요청
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .getForEntity("/cashcards?page=0&size=1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray page = documentContext.read("$[*]");
        assertThat(page.size()).isEqualTo(1);
    }

    // GET /cashcards?page=0&size=1&sort=amount,desc 테스트
    @Test
    void shouldReturnASortedPageOfCashCards() {
        // 요청 URI에 pagination, sorting 정보를 포함하여 GET 요청
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .getForEntity("/cashcards?page=0&size=1&sort=amount,desc", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray read = documentContext.read("$[*]");
        assertThat(read.size()).isEqualTo(1);

        double amount = documentContext.read("$[0].amount");
        assertThat(amount).isEqualTo(150.00);
    }

    // GET /cashcards page, size, sort 기본값 테스트
    @Test
    void shouldReturnASortedPageOfCashCardsWithNoParametersAndUseDefaultValues() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .getForEntity("/cashcards", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray page = documentContext.read("$[*]");
        assertThat(page.size()).isEqualTo(3);

        JSONArray amounts = documentContext.read("$..amount");
        assertThat(amounts).containsExactly(1.00, 123.45, 150.00);
    }

    // 잘못된 자격증명을 사용했을 때 401 UNAUTHORIZED 응답 테스트
    @Test
    void shouldNotReturnACashCardWhenUsingBadCredentials() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("BAD-USER", "abc123")
                .getForEntity("/cashcards/99", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        response = restTemplate
                .withBasicAuth("sarah1", "BAD-PASSWORD")
                .getForEntity("/cashcards/99", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    // Role 검증 테스트
    @Test
    void shouldRejectUsersWhoAreNotCardOwners() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("hank-owns-no-cards", "qrs456")
                .getForEntity("/cashcards/99", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    // 다른 유저의 데이터에 접근하지 못하는 지 테스트
    @Test
    void shouldNotAllowAccessToCashCardsTheyDoNotOwn() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .getForEntity("/cashcards/102", String.class);  // kumar2의 데이터에 접근
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    // PUT 테스트
    @Test
    @DirtiesContext  // 다른 테스트에 영향을 주지 않도록 CashCard 업데이트 후 클린 업(메서드 레벨 적용)
    void shouldUpdateAnExistingCashCard() {
        CashCard cashCardUpdate = new CashCard(null, 19.99, null);
        HttpEntity<CashCard> request = new HttpEntity<>(cashCardUpdate);
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .exchange("/cashcards/99", HttpMethod.PUT, request, Void.class);  // putForEntity 메서드가 존재하지 않아 좀 더 일반적인 버전인 exchange 메서드 사용
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .getForEntity("/cashcards/99", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("$.id");
        Double amount = documentContext.read("$.amount");
        assertThat(id).isEqualTo(99);
        assertThat(amount).isEqualTo(19.99);
    }

    @Test
    void shouldNotUpdateACashCardThatDoesNotExist() {
        CashCard unknownCard = new CashCard(null, 19.99, null);
        HttpEntity<CashCard> request = new HttpEntity<>(unknownCard);
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .exchange("/cashcards/99999", HttpMethod.PUT, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotUpdateACashCardThatIsOwnedBySomeoneElse() {
        CashCard kumarsCard = new CashCard(null, 333.33, null);
        HttpEntity<CashCard> request = new HttpEntity<>(kumarsCard);
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .exchange("/cashcards/102", HttpMethod.PUT, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    // DELETE 테스트
    @Test
    @DirtiesContext
    void shouldDeleteAnExistingCashCard() {
        ResponseEntity<Void> response = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .exchange("/cashcards/99", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .getForEntity("/cashcards/99", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotDeleteACashCardThatDoesNotExist() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .exchange("/cashcards/99999", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldNotAllowDeletionOfCashCardsTheyDoNotOwn() {
        ResponseEntity<Void> deleteResponse = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .exchange("/cashcards/102", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("kumar2", "xyz789")
                .getForEntity("/cashcards/102", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
