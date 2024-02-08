package com.cabbage556.mockito.mockitodemo.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)  // Mockito 어노테이션을 사용하기 위한 Mockito 확장 어노테이션
public class SomeBusinessImplMockTest {

    // Stubs 방식과 비교했을 때 Mock 방식의 장점
    //      테스트 시나리오마다 Stub 클래스를 여러 개 작성할 필요가 없음 -> 메서드 mocking
    //      DataService가 변경되더라도 mock에 영향을 적게 줌

    // DataService의 mock 생성
    @Mock  // DataService의 mock 생성 어노테이션 -> MockitoExtension에서 처리됨
    private DataService dataServiceMock;

    @InjectMocks  // SomeBusinessImpl의 mock 의존성 주입 어노테이션 -> MockitoExtension에서 처리됨
    private SomeBusinessImpl business;

    @Test
    void findTheGreatestFromAllData_basicScenario() {
        // mock 생성 후 mock의 특정 메서드가 어떤 값을 반환할지 지정(메서드 mocking)
        //      dataServiceMock.retrieveAllData() returns new int[] {25, 15, 5}
        when(dataServiceMock.retrieveAllData())       // dataServiceMock.retreiveAllData()가 호출되면
                .thenReturn(new int[] {25, 15, 5});   // new int[] {25, 15, 5} 를 반환해라

        assertThat(business.findTheGreatestFromAllData())
                .isEqualTo(25);
    }

    @Test
    void findTheGreatestFromAllData_withOneValue() {
        when(dataServiceMock.retrieveAllData())       // dataServiceMock.retreiveAllData()가 호출되면
                .thenReturn(new int[] {35});          // new int[] {35} 를 반환해라

        assertThat(business.findTheGreatestFromAllData())
                .isEqualTo(35);
    }

    @Test
    void findTheGreatestFromAllData_withEmptyArray() {
        when(dataServiceMock.retrieveAllData())       // dataServiceMock.retreiveAllData()가 호출되면
                .thenReturn(new int[] {});            // new int[] {} 를 반환해라

        assertThat(business.findTheGreatestFromAllData())
                .isEqualTo(Integer.MIN_VALUE);
    }
}
