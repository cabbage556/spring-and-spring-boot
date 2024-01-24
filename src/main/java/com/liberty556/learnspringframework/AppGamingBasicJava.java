package com.liberty556.learnspringframework;

import com.liberty556.learnspringframework.game.GameRunner;
import com.liberty556.learnspringframework.game.MarioGame;
import com.liberty556.learnspringframework.game.PacmanGame;
import com.liberty556.learnspringframework.game.SuperContraGame;

public class AppGamingBasicJava {
    public static void main(String[] args) {
        // var game = new MarioGame();
        // var game = new SuperContraGame();
        var game = new PacmanGame();
        var gameRunner = new GameRunner(game);
        gameRunner.run();
    }
}
