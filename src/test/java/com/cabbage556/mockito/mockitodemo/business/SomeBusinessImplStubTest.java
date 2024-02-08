package com.cabbage556.mockito.mockitodemo.business;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SomeBusinessImplStubTest {

    @Test
    void findTheGreatestFromAllData_basicScenario() {
        DataService dataServiceStub = new DataServiceStub1();
        SomeBusinessImpl businessImpl = new SomeBusinessImpl(dataServiceStub);
        int result = businessImpl.findTheGreatestFromAllData();

        assertThat(result).isEqualTo(25);
    }

    @Test
    void findTheGreatestFromAllData_withOneValue() {
        DataService dataServiceStub = new DataServiceStub2();
        SomeBusinessImpl businessImpl = new SomeBusinessImpl(dataServiceStub);
        int result = businessImpl.findTheGreatestFromAllData();

        assertThat(result).isEqualTo(35);
    }
}

// 유닛 테스트에서 실제 DataService 대신 사용하는 Stub 클래스
// Stub 클래스의 문제점
//      1. DataService 인터페이스에 메서드가 추가될 때마다 메서드를 구현해야 하고, 인터페이스의 메서드가 변경되면 이에 맞게 메서드를 수정해야 함
//      2. 많은 시나리오를 테스트하기가 어려움
//              시나리오마다 Stub 클래스를 구현해야 함
//              테스트마다 시나리오에 맞는 Stub 클래스를 사용해야 함
//      즉 Stub 클래스를 유지보수하기가 어려워짐
// Stub 클래스를 유지보수하기 어렵기 때문에 Mock을 사용하는 방식이 권장됨
class DataServiceStub1 implements DataService {
    @Override
    public int[] retrieveAllData() {
        return new int[] {25, 15, 5};
    }
}

// 다른 시나리오에서 사용하는 Stub 클래스
class DataServiceStub2 implements DataService {
    @Override
    public int[] retrieveAllData() {
        return new int[] {35};
    }
}
