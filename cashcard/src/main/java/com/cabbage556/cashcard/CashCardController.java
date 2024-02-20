package com.cabbage556.cashcard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // RestController 타입의 Component이고, HTTP 요청을 처리할 수 있음을 명시함
@RequestMapping("/cashcards")  // 컨트롤러에 접근하기 위해 필요한 주소
class CashCardController {

    @GetMapping("/{requestedId}")  // GET /cashcards/{requestedId} 요청이 이 메서드에 매핑됨
    private ResponseEntity<CashCard> findById(@PathVariable Long requestedId) {
        if (requestedId.equals(99L)) {
            return ResponseEntity.ok(
                    new CashCard(99L, 123.45)
            );
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
