package com.cabbage556.cashcard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController  // RestController 타입의 Component이고, HTTP 요청을 처리할 수 있음을 명시함
@RequestMapping("/cashcards")  // 컨트롤러에 접근하기 위해 필요한 주소
class CashCardController {

    private final CashCardRepository cashCardRepository;

    private CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    // 파라미터
    //      Principal 객체: 유저의 인증, 권한 정보를 담고 있음. 스프링 시큐리티에 의해 자동으로 제공됨
    @GetMapping("/{requestedId}")
    private ResponseEntity<CashCard> findById(@PathVariable Long requestedId, Principal principal) {
        CashCard cashCard = findCashCard(requestedId, principal);
        if (cashCard == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cashCard);
    }

    // 파라미터
    //      @RequestBody CashCard newCashCardRequest: 요청 바디(API에 제공하는 데이터가 포함됨), 스프링 웹은 이 데이터를 CashCard로 역직렬화함
    //      UriComponentBuilder ucb: 스프링 IoC 컨테이너로부터 주입됨
    @PostMapping
    private ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb, Principal principal) {
        // Principal로부터 username을 가져와 CashCard 생성
        CashCard cashCardWithOwner = new CashCard(null, newCashCardRequest.amount(), principal.getName());

        // CrudRepository가 CashCard 저장 후 데이터베이스가 제공하는 유일한 id를 갖는 저장된 객체를 리턴
        CashCard savedCashCard = cashCardRepository.save(cashCardWithOwner);

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

    // 파라미터
    //      Pageable: 스프링 웹이 제공하는 객체, URI에 명시한 page, size 쿼리 파라미터의 값을 포함함
    @GetMapping()
    private ResponseEntity<List<CashCard>> findAll(Pageable pageable, Principal principal) {
        // URI에 명시한 page, size 쿼리 파라미터의 값을 통해 pagination 구현
        Page<CashCard> page = cashCardRepository.findByOwner(
                principal.getName(),
                PageRequest.of(                   // PageRequest: Pageable의 기본 자바 빈 구현체
                        pageable.getPageNumber(), // 페이지 인덱스(URI page 쿼리 파라미터의 값, 0부터 시작, 기본값: 0)
                        pageable.getPageSize(),   // 페이지 크기(URI size 쿼리 파라미터의 값, 기본값: 20)
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))  // 정렬 기준(URI sort 쿼리 파라미터의 값, 기본값: getSortOr 메서드에 전달한 Sort 객체)
                )
        );
        return ResponseEntity.ok(page.getContent());
    }

    @PutMapping("/{requestedId}")
    private ResponseEntity<Void> putCashCard(@PathVariable Long requestedId, @RequestBody CashCard cashCardUpdate, Principal principal) {
        CashCard cashCard = findCashCard(requestedId, principal);
        if (cashCard == null) {  // id에 해당하는 CashCard를 찾지 못하는 경우 404 NOT FOUND 응답
            return ResponseEntity.notFound().build();
        }

        CashCard updatedCashCard = new CashCard(cashCard.id(), cashCardUpdate.amount(), principal.getName());
        cashCardRepository.save(updatedCashCard);  // id 값을 갖는 레코드가 데이터베이스에 존재하면 업데이트
        return ResponseEntity.noContent().build();
    }

    private CashCard findCashCard(Long requestedId, Principal principal) {
        return cashCardRepository.findByIdAndOwner(requestedId, principal.getName());
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteCashCard(@PathVariable Long id, Principal principal) {
        if (cashCardRepository.existsByIdAndOwner(id, principal.getName())) {
            cashCardRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
