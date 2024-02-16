package com.liberty556.learnspringframework.game;

import com.liberty556.learnspringframework.game.GameRunner;
import com.liberty556.learnspringframework.game.PacmanGame;

public class App01GamingBasicJava {
    public static void main(String[] args) {
        // var game = new MarioGame();
        // var game = new SuperContraGame();

        // 1. 객체 생성
        var game = new PacmanGame();

        // 2. 객체 생성 + 종속성 연결(wiring dependencies)
        //      특정 Game 클래스가 GameRunner 클래스의 종속성이다.(게임을 실행하려면 특정 Game 객체가 필요함)
        //      특정 Game 객체를 생성해 GameRunner 객체에 주입해 두 객체가 결합된다.
        //      이 클래스들의 객체를 우리가 직접 생성해 주입하는 대신 스프링 프레임워크가 객체를 대신 생성해 주입하게 한다.
        var gameRunner = new GameRunner(game);
        gameRunner.run();
    }
}
