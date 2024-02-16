package com.liberty556.learnspringframework.game;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component // 스프링이 GameRunner 인스턴스를 생성해줌(스프링 빈을 직접 생성하지 않아도 됨)
public class GameRunner {
    // tightly coupled(강한 결합)
    //      GameRunner 클래스는 특정 game 클래스에 강하게 결합됨
    //      다른 game 클래스 인스턴스를 사용하려면 GameRunner 클래스 코드를 수정해야 함
    // private MarioGame game;
    // private SuperContraGame game;
    // public GameRunner(SuperContraGame game) {
    //     this.game = game;
    // }

    // loosely coupled(느슨한 결합)
    //      GameRunner 클래스는 특정 game 클래스가 아닌 GamingConsole 인터페이스와 결합됨
    //      다른 game 클래스를 인스턴스를 사용하기 위해 GameRunner 클래스 코드를 수정할 필요가 없음
    //      GameRunner 클래스는 특정 game 클래스와 강하게 결합되지 않고 분리되었음(느슨하게 결합됨)
    private GamingConsole game;

    public GameRunner(
            // GamingConsole 인터페이스 구현 객체가 오토 와이어링됨
            @Qualifier("SuperContraGameQualifier") GamingConsole game
    ) {
        this.game = game;
    }

    public void run() {
        System.out.println("Running game: " + game);
        game.up();
        game.down();
        game.left();
        game.right();
    }
}
