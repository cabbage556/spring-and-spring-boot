package com.cabbage556.cashcard;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

// Jackson 프레임워크를 사용하는 테스트 클래스임을 명시함
//      JSON 테스팅과 파싱 지원을 제공함
@JsonTest
public class CashCardJsonTest {

    // JacksonTester
    //      Jackson JSON 파싱 라이브러리에 대한 래퍼, JSON 객체의 직렬화와 역직렬화를 핸들링
    @Autowired
    private JacksonTester<CashCard> json;

    @Autowired
    private JacksonTester<CashCard[]> jsonList;

    private CashCard[] cashCards;

    @BeforeEach
    void setUp() {
        cashCards = Arrays.array(
                new CashCard(99L, 123.45, "sarah1"),
                new CashCard(100L, 1.00, "sarah1"),
                new CashCard(101L, 150.00, "sarah1")
        );
    }

    @Test
    void cashCardSerializationTest() throws IOException {
        CashCard cashCard = cashCards[0];

        // ClassLoader의 Resource를 사용해 single.json 파일 읽기
        ClassLoader classLoader = this.getClass().getClassLoader();
        File expectedJson = new File(classLoader.getResource("com.cabbage556.cashcard/single.json").getFile());

        // cashCard 객체의 직렬화 결과가 single.json과 동일한지 테스트
        //      직렬화: 자바 객체 -> json
        assertThat(json.write(cashCard)).isStrictlyEqualToJson(expectedJson);
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(99);
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.amount");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount")
                .isEqualTo(123.45);
    }

    @Test
    void cashCardDeserializationTest() throws IOException {
        String expected = """
                {
                    "id":99,
                    "amount":123.45,
                    "owner": "sarah1"
                }
                """;

        // String의 역직렬화 결과가 CashCard 객체와 동일한지 테스트
        //      역직렬화: json -> 자바 객체
        assertThat(json.parse(expected))
                .isEqualTo(new CashCard(99L, 123.45, "sarah1"));
        assertThat(json.parseObject(expected).id())
                .isEqualTo(99);
        assertThat(json.parseObject(expected).amount())
                .isEqualTo(123.45);
    }

    @Test
    void cashCardListSerializationTest() throws IOException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File expectedJson = new File(classLoader.getResource("com.cabbage556.cashcard/list.json").getFile());

        // cashCards 변수의 값을 JSON으로 직렬화 후 list.json과 동일한지 테스트
        assertThat(jsonList.write(cashCards)).isStrictlyEqualToJson(expectedJson);
    }

    @Test
    void cashCardListDeserializationTest() throws IOException {
        String expected = """
                [
                    { "id": 99, "amount": 123.45, "owner": "sarah1" },
                    { "id": 100, "amount": 1.00, "owner": "sarah1" },
                    { "id": 101, "amount": 150.00, "owner": "sarah1" }
                ]
                """;
        assertThat(jsonList.parse(expected)).isEqualTo(cashCards);
    }
}
