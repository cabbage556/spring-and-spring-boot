package com.cabbage556.cashcard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController  // RestController 타입의 Component이고, HTTP 요청을 처리할 수 있음을 명시함
@RequestMapping("/cashcards")  // 컨트롤러에 접근하기 위해 필요한 주소
class CashCardController {

    private final CashCardRepository cashCardRepository;

    private CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    @GetMapping("/{requestedId}")  // GET /cashcards/{requestedId} 요청이 이 메서드에 매핑됨
    private ResponseEntity<CashCard> findById(@PathVariable Long requestedId) {
        Optional<CashCard> cashCardOptional = cashCardRepository.findById(requestedId);
        if (cashCardOptional.isPresent()) {
            return ResponseEntity.ok(cashCardOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // @RequestBody CashCard newCashCardRequest: 요청 바디(API에 제공하는 데이터가 포함됨), 스프링 웹은 이 데이터를 CashCard로 역직렬화함
    // UriComponentBuilder ucb: 스프링 IoC 컨테이너로부터 주입됨
    @PostMapping
    private ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb) {
        // CrudRepository가 CashCard 저장 후 데이터베이스가 제공하는 유일한 id를 갖는 저장된 객체를 리턴
        CashCard savedCashCard = cashCardRepository.save(newCashCardRequest);

        // 새롭게 생성된 CashCard로의 URI 생성
        //      POST 요청 호출자가 새롭게 생성된 CashCard에 대한 GET 요청에 사용할 수 있는 URI
        URI locationOfNewCashCard = ucb
                .path("cashcards/{id}")
                .buildAndExpand(savedCashCard.id())
                .toUri();
        return ResponseEntity
                .created(locationOfNewCashCard)  // Location 헤더와 함께 201 CREATED 응답
                .build();
    }

    @GetMapping()
    private ResponseEntity<List<CashCard>> findAll(Pageable pageable) { // Pageable: 스프링 웹이 제공하는 객체, URI에 명시한 page, size 쿼리 파라미터의 값을 포함함
        // URI에 명시한 page, size 쿼리 파라미터의 값을 통해 pagination 구현
        Page<CashCard> page = cashCardRepository.findAll(
                // PageRequest: Pageable의 기본 자바 빈 구현체
                PageRequest.of(
                        pageable.getPageNumber(), // 페이지 인덱스(URI page 쿼리 파라미터의 값, 0부터 시작, 기본값: 0)
                        pageable.getPageSize(), // 페이지 크기(URI size 쿼리 파라미터의 값, 기본값: 20)
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))  // 정렬 기준(URI sort 쿼리 파라미터의 값, 기본값: getSortOr 메서드에 전달한 Sort 객체)
                )
        );
        return ResponseEntity.ok(page.getContent());
    }
}
