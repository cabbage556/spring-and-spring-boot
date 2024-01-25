package com.liberty556.learnspringframework;

import com.liberty556.learnspringframework.game.GameRunner;
import com.liberty556.learnspringframework.game.GamingConsole;
import com.liberty556.learnspringframework.game.PacmanGame;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GamingConfiguration {

    @Bean
    public GamingConsole game() {
        return new PacmanGame();
    }

    @Bean
    public GameRunner gameRunner(GamingConsole game) {
        // 스프링은 game 빈을 가져와 game 파라미터에 연결함(auto-wiring)
        return new GameRunner(game);
    }
}
