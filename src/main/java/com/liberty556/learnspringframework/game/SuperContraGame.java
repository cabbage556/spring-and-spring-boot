package com.liberty556.learnspringframework.game;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component // 스프링이 SuperContraGame 인스턴스를 생성해줌(스프링 빈을 직접 생성하지 않아도 됨)
@Qualifier("SuperContraGameQualifier")
public class SuperContraGame implements GamingConsole {

    @Override
    public void up() {
        System.out.println("up");
    }

    @Override
    public void down() {
        System.out.println("Sit down");
    }

    @Override
    public void left() {
        System.out.println("Go back");
    }

    @Override
    public void right() {
        System.out.println("Shoot a bullet");
    }
}
