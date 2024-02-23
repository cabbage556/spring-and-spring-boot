package com.cabbage556.cashcard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

// CrudRepository
//      스프링 데이터(Spring Data)가 제공하는 인터페이스
//      이 인터페이스를 상속하면 스프링 부트와 스프링 데이터가 함께 CRUD 메서드를 자동으로 생성해준다.
//      CashCardRepository가 관리해야 하는 데이터 객체 CashCard 지정(이 레포지토리의 도메인 타입이 CashCard가 됨)
//      CrudRepository를 상속하지 않으면 스프링 빈으로 등록되지 않음
// PagingAndSortingRepository
//      스프링 데이터가 제공하는 paging, sorting 기능을 갖고 있는 인터페이스
interface CashCardRepository extends CrudRepository<CashCard, Long>, PagingAndSortingRepository<CashCard, Long> {

    // owner로 CashCard 데이터 필터링
    CashCard findByIdAndOwner(Long id, String owner);

    Page<CashCard> findByOwner(String owner, PageRequest pageRequest);

    boolean existsByIdAndOwner(Long id, String owner);
}
