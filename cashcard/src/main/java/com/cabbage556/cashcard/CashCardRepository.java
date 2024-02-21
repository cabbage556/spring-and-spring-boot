package com.cabbage556.cashcard;

import org.springframework.data.repository.CrudRepository;

// CrudRepository
//      스프링 데이터(Spring Data)가 제공하는 인터페이스
//      이 인터페이스를 상속하면 스프링 부트와 스프링 데이터가 함께 CRUD 메서드를 자동으로 생성해준다.
//      CashCardRepository가 관리해야 하는 데이터 객체 CashCard 지정(이 레포지토리의 도메인 타입이 CashCard가 됨)
//      CrudRepository를 상속하지 않으면 스프링 빈으로 등록되지 않음
interface CashCardRepository extends CrudRepository<CashCard, Long> {
}
