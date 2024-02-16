package com.liberty556.learnspringframework.game;

// 인터페이스
//      GameRunner 클래스와 특정 Game 클래스들의 강한 결합을 느슨한 결합으로 만들어줌
public interface GamingConsole {
    void up();
    void down();
    void left();
    void right();
}
