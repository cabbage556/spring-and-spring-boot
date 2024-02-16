package com.cabbage556.mockito.mockitodemo.business;

public class SomeBusinessImpl {

    private DataService dataService;

    public SomeBusinessImpl(DataService dataService) {
        this.dataService = dataService;
    }

    public int findTheGreatestFromAllData() {
        int[] data = dataService.retrieveAllData();
        int greatestValue = Integer.MIN_VALUE;
        for (int value : data) {
            greatestValue = Integer.max(value, greatestValue);
        }
        return greatestValue;
    }
}

interface DataService {
    int[] retrieveAllData();
}
