package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Level2 extends Stage {

    private WesternDentist game;

    private Sequencer sequencer;

    private Image background = new Image(new Texture("Images/WesternDentist_L2_Layer1_Background.png")),
            background2 = new Image(new Texture("Images/WesternDentist_L2_Layer2_Background.png"));

    private boolean bossSpawned = false;
    private boolean bossMusicPlayed = false;
    private int backgroundHeight1 = 426;
    private int backgroundHeight2 = 3508;
    private double increment1 = 10;
    private double increment2 = 0.01;


    Level2(final WesternDentist game) {
        super(game.viewport);

        float stageWidth = Gdx.graphics.getWidth(),
                stageHeight = Gdx.graphics.getHeight();

        background.setPosition(0, 0);
        background2.setPosition(0, 0);
        background2.setSize(stageWidth, stageHeight);
        addActor(background);
        addActor(background2);

        addActor(new Player(300, 100));

        sequencer = new Sequencer();

        sequencer.addPhase(300);
        sequencer.addPhase(400);
        sequencer.addPhase(100000);

        for(int i = 0; i < 10; ++i) {
            sequencer.addActorToPhase(0, new DiagonalEnemy(new Texture("Images/T-Pose_Luigi.png"), 10, 100, -200));
            sequencer.addPhaseSpawnFrequency(0, 30 + i);
            sequencer.addPhaseSpawnPosition(0, new Vector2(600 + i, 700 + i));
        }

        for(int i = 0; i < 10; ++i) {
            sequencer.addActorToPhase(0, new DiagonalEnemy(new Texture("Images/T-Pose_Luigi.png"), 10, 100, 200));
            sequencer.addPhaseSpawnFrequency(0, 50 + i);
            sequencer.addPhaseSpawnPosition(0, new Vector2(0, 700 + i));
        }

        BossTPoseQueen boss = new BossTPoseQueen(sequencer);
        boss.addAction(Actions.moveTo(200, 250, 6));

        sequencer.addActorToPhase(2, boss);
        sequencer.addPhaseSpawnFrequency(2, 30);
        sequencer.addPhaseSpawnPosition(2, new Vector2(300, 1000));




        this.game = game;
    }

    @Override
    public void act(float delta) {
        backgroundScrolling(delta);
        sequencer.update(delta, this);
        checkBoss();
        super.act(delta);
    }

    @Override
    public void draw() {
        sortActors();
        super.draw();
    }

    private void checkBoss() {
        if (!bossSpawned) {
            try {

                for (Actor actor: getActors()) {
                    if (actor instanceof BossTPoseQueen) {
                        bossSpawned = true;
                        game.playMusic(true);
                        bossMusicPlayed = true;
                    }
                }
            }
            catch (NullPointerException e) {

            }
        }else if (bossSpawned) {
            for (Actor actor: getActors()) {
                if (actor instanceof BossTPoseQueen) {
                    return;
                }
            }
            //Level3 level3 = new Level3(game);
            //game.changeStage(level3);
        }
    }

    private void sortActors() {
        for (Actor actor : this.getActors()) {
            int z = actor.getZIndex();
            if (Image.class.isInstance(actor) && actor.getName() == "UI_FRAME") {
                if (actor.getZIndex() != 300000) {
                    actor.setZIndex(300000);
                }
            }
            else if (Image.class.isInstance(actor) && actor.getName() == "BACKGROUND") {
                actor.setZIndex(0);
            }
            else if (Image.class.isInstance(actor)) {
                actor.setZIndex(0);
            }
            else if (Player.class.isInstance(actor)) {
                actor.setZIndex(3000);
            }
            else if (z > 3000) {
                actor.setZIndex(z - 3000);
            }
            else {
                actor.setZIndex(1);
            }
        }
    }

    private void backgroundScrolling(float delta) {
        background.setY((float) (background.getY() - increment1 * delta));
        background2.rotateBy((float) increment2);

        if (Math.abs(background2.getRotation()) >= 2) {
            this.increment2 *= -1;
        }
    }
}