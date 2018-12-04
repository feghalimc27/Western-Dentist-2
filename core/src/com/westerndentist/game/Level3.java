package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Level3 extends Stage {

    private WesternDentist game;
    private Image background = new Image(new Texture("Images/WesternDentist_L3_Background.png"));
    private Image background2 = new Image(new Texture("Images/WesternDentist_L3_Background.png"));
    private Boolean bossTime, noBoss, bossMusic, spawnBossNow, win;

    /**
     * Constructor for Level 3
     * @param game      game object containing all values which persist across level instances
     */
    Level3 (final WesternDentist game) {
        super(game.viewport);

        // set background name
        background.setName("BACKGROUND");

        // Add background, player, UI
        background.setPosition(0, 2500);
        background2.setPosition(0, -2500);
        addActor(background);
        addActor(background2);
        game.player.setPosition(300,100);
        addActor(game.player);
        game.player.addAura();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                bossTime = true;
            }},   60000
        );

        bossTime = false;
        noBoss = true;
        bossMusic = false;
        spawnBossNow = false;
        win = false;
        this.game = game;
    }

    /**
     * Draw the scene on the window
     */
    @Override
    public void draw() {
        //sortActors();
        super.draw();
    }

    /**
     * Perform all actions on all objects on screen
     * @param delta     time since the last frame
     */
    @Override
    public void act(float delta) {
        backgroundScrolling(delta);
        if (!win) {
            if (!bossTime)
                spawnEnemies();
            if (bossTime && noBoss)
            {
                if(!bossMusic)
                {
                    game.playMusic(true);
                    bossMusic = true;
                    final Timer spawnBossTimer = new Timer();
                    spawnBossTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            spawnBossNow = true;
                        }},  4000);
                }
                if(spawnBossNow)
                {
                    spawnBoss();
                }
                //spawnBoss();
            }
            if (bossTime && !noBoss)
                if (bossIsDead())
                {
                    win = true;
                    game.player.remove();
                    game.changeStage(new Level4(game));
                }
        }
        super.act(delta);
    }

    /**
     * check whether boss is dead
     * @return      true if boss has died, false otherwise
     */
    private Boolean bossIsDead()
    {
        for(Actor actor : this.getActors())
        {
            if (L3Boss.class.isInstance(actor))
                return false;
        }
        return true;
    }

    /**
     * Randomly spawns one of two enemies on the screen with a chance of no spawn
     */
    private void spawnEnemies() {
        double decider = Math.random() * (1000 - 0);

        if (decider > 980) {
            Gdx.app.log("Level 3", "Spawned Enemy");
            double enemyType = Math.random() * 1000;
            if (enemyType < 250)
                addActor(new BasicEnemy(new Texture("Images/RocketEnemy.png"), 400, 90, 100, new Vector2((float)Math.random() * (520-64), 800)));
            else
                addActor(new BasicEnemy(new Texture("Images/AsteroidEnemy.png"), 150, 150, 100, new Vector2((float)Math.random() * (520-64), 800)));
        }
    }

    /**
     * Spawn a L3 boss
     */
    private void spawnBoss()
    {
        Gdx.app.log("Level 3", "Mr. Muskrat has Arrived");
        addActor(new L3Boss(220, 600));
        noBoss = false;
    }

    /**
     * scroll the background image across the screen
     * @param delta     Time since the last frame
     */
    private void backgroundScrolling(float delta) {
        background.setY(background.getY() + 30 * delta);
        background2.setY(background2.getY() + 30 * delta);

        if (background.getY() >= 5000) {
            background.setY(-5000);
        }

        if (background2.getY() >= 5000) {
            background2.setY(-5000);
        }
    }
}
