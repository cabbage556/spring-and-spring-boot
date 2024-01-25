package com.liberty556.learnspringframework.game;

import org.springframework.stereotype.Component;

@Component // 스프링이 MarioGame 인스턴스를 생성해줌(스프링 빈을 직접 생성하지 않아도 됨)
public class MarioGame implements GamingConsole {

    @Override
    public void up() {
        System.out.println("Jump");
    }

    @Override
    public void down() {
        System.out.println("Go into a hole");
    }

    @Override
    public void left() {
        System.out.println("Go back");
    }

    @Override
    public void right() {
        System.out.println("Accelerate");
    }
}
