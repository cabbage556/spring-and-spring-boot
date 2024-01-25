package com.liberty556.learnspringframework.game;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component // 스프링이 PacmanGame 인스턴스를 생성해줌(스프링 빈을 직접 생성하지 않아도 됨)
@Primary
public class PacmanGame implements GamingConsole {
    @Override
    public void up() {
        System.out.println("Pacman go up");
    }

    @Override
    public void down() {
        System.out.println("Pacman go down");
    }

    @Override
    public void left() {
        System.out.println("Pacman go left");
    }

    @Override
    public void right() {
        System.out.println("Pacman go right");
    }
}
