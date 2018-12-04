package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * UserInterface stage class, which is drawn on top of the current playable stage to display stats and other information
 */
public class UserInterface extends Stage {
    private WesternDentist game;
    private Label hiScoreLabel;
    private Label scoreLabel;
    private Label playerLabel;
    private Label powerLabel;
    private Label fpsLabel;

    /**
     * UserInterface constructor, handles creation and placement of all on-screen assets
     * @param game  game, contains assets and methods
     */
    UserInterface(final WesternDentist game) {
        super(game.viewport);
        this.game = game;
        Image uiMain = new Image(new Texture("images/userinterface.png"));
        hiScoreLabel = new Label("000000000", game.labelStyle);
        hiScoreLabel.setPosition(630, 522);
        scoreLabel = new Label("000000000", game.labelStyle);
        scoreLabel.setPosition(630, 486);
        playerLabel = new Label("* * * * *", game.labelStyle);
        playerLabel.setPosition(630, 431);
        powerLabel = new Label("0/1000", game.labelStyle);
        powerLabel.setPosition(630, 398);
        fpsLabel = new Label("0 FPS", game.labelStyle);
        fpsLabel.setPosition(700, 0);
        addActor(uiMain);
        addActor(hiScoreLabel);
        addActor(scoreLabel);
        addActor(playerLabel);
        addActor(powerLabel);
        addActor(fpsLabel);
    }

    /**
     * Override of Stage act method, which calls super.act(), then performs the update of every stat label to it's current value (every frame)
     * @param deltaTime
     */
    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        hiScoreLabel.setText(String.format("%09d", game.hiScore));
        scoreLabel.setText(String.format("%09d", game.player.getScore()));
        if (game.player.getHealth() == 1) {
            playerLabel.setText("*");
        } else if (game.player.getHealth() == 2) {
            playerLabel.setText("* *");
        } else if (game.player.getHealth() == 3) {
            playerLabel.setText("* * *");
        } else if (game.player.getHealth() == 4) {
            playerLabel.setText("* * * *");
        } else if (game.player.getHealth() == 5) {
            playerLabel.setText("* * * * *");
        }
        powerLabel.setText(String.format("%04d", game.player.getPower()) + "/1000");
        fpsLabel.setText(Gdx.graphics.getFramesPerSecond() + " FPS");
    }
}
