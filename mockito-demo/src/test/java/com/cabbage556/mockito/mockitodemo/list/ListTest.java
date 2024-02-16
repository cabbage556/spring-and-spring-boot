package com.cabbage556.mockito.mockitodemo.list;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// List 인터페이스 mocking으로 Mock 더 자세히 알아보기
public class ListTest {

    @Test
    void simpleTest() {
        List listMock = mock(List.class);

        when(listMock.size())
                .thenReturn(3);

        assertThat(listMock.size())
                .isEqualTo(3);
    }

    @Test
    void multipleReturns() {
        List listMock = mock(List.class);

        when(listMock.size())       // listMock.size()가 호출되면
                .thenReturn(1)   // 첫 번째 호출의 경우 1 리턴
                .thenReturn(2);  // 두 번째 이후 호출의 경우 2 리턴

        assertThat(listMock.size())
                .isEqualTo(1);
        assertThat(listMock.size())
                .isEqualTo(2);
        assertThat(listMock.size())
                .isEqualTo(2);
        assertThat(listMock.size())
                .isEqualTo(2);
    }

    @Test
    void specificParams() {
        List listMock = mock(List.class);

        when(listMock.get(0))                 // listMock.get()에 0이 전달되어 호출되면
                .thenReturn("SomeString"); // "SomeString" 리턴

        assertThat(listMock.get(0))
                .isEqualTo("SomeString");
        assertThat(listMock.get(1))
                .isEqualTo(null);
    }

    @Test
    void genericParams() {
        List listMock = mock(List.class);

        when(listMock.get(Mockito.anyInt()))        // listMock.get()에 어떤 int 값이 전달되어 호출되면
                .thenReturn("SomeOtherString");  // "SomeOtherString" 리턴

        assertThat(listMock.get(0))
                .isEqualTo("SomeOtherString");
        assertThat(listMock.get(1))
                .isEqualTo("SomeOtherString");
        assertThat(listMock.get(10))
                .isEqualTo("SomeOtherString");
    }
}
