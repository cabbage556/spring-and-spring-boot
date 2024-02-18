package com.cabbage556.cashcard;

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

    @Test
    void cashCardSerializationTest() throws IOException {
        CashCard cashCard = new CashCard(99L, 123.45);

        // ClassLoader의 Resource를 사용해 expected.json 파일 읽기
        ClassLoader classLoader = this.getClass().getClassLoader();
        File expectedJson = new File(classLoader.getResource("com.cabbage556.cashcard/expected.json").getFile());

        // cashCard 객체의 직렬화 결과가 expected.json과 동일한지 테스트
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
                    "amount":123.45
                }
                """;

        // String의 역직렬화 결과가 CashCard 객체와 동일한지 테스트
        //      역직렬화: json -> 자바 객체
        assertThat(json.parse(expected))
                .isEqualTo(new CashCard(99L, 123.45));
        assertThat(json.parseObject(expected).id())
                .isEqualTo(99);
        assertThat(json.parseObject(expected).amount())
                .isEqualTo(123.45);
    }
}
