package com.westerndentist.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Level1 extends Stage
{
    private WesternDentist game;

    private Sequencer sequencer;

    private Image background = new Image(new Texture("Images/WesternDentist_Background.png")),
            background2 = new Image(new Texture("Images/WesternDentist_Background.png"));

    Level1(final WesternDentist game)
    {

        background.setPosition(0, 2500);
        background2.setPosition(0, -2500);

        addActor(background);
        addActor(background2);

        addActor(new Player(300, 300));

        EnemyLevel1 duck1 = new EnemyLevel1(new Texture("Images/bubber ducky.png"), 220, 50, 6000, new Vector2(0, 0));
        EnemyLevel1 duck2 = new EnemyLevel1(new Texture("Images/bubber ducky.png"), 220, 50, 6000, new Vector2(0, 0));
        EnemyLevel1 duck3 = new EnemyLevel1(new Texture("Images/bubber ducky.png"), 220, 50, 6000, new Vector2(0, 0));
        EnemyLevel1 duck4 = new EnemyLevel1(new Texture("Images/bubber ducky.png"), 220, 50, 6000, new Vector2(0, 0));
        EnemyLevel1 duck5 = new EnemyLevel1(new Texture("Images/bubber ducky.png"), 220, 50, 6000, new Vector2(0, 0));


        BossHikeMaze hikeMaze = new BossHikeMaze(new Texture("Images/Hike Maze zombie.png"), 2000, new Vector2(0, 400));
        hikeMaze.addAction(Actions.moveTo(100, 300, 5));

        BossTrerryClews trerryClews = new BossTrerryClews(new Texture("Images/Scary Trews.png"), 6500, new Vector2(0, 400));
        trerryClews.addAction(Actions.moveTo(150,365, 10));

        sequencer = new Sequencer();

        sequencer.addPhase(200);
        sequencer.addPhase(200);
        sequencer.addPhase(200);
        sequencer.addPhase(200);
        sequencer.addPhase(200);
        sequencer.addPhase(1000);

        //sequencer.addActorToPhase(0, duck1);
        //sequencer.addPhaseSpawnFrequency(0, 50);
        //sequencer.addPhaseSpawnPosition(0, new Vector2(40, 700));

        //sequencer.addActorToPhase(1, duck2);
        //sequencer.addPhaseSpawnFrequency(1, 55);
        //sequencer.addPhaseSpawnPosition(1, new Vector2(60, 700));

        //sequencer.addActorToPhase(2, duck3);
        //sequencer.addPhaseSpawnFrequency(2, 60);
        //sequencer.addPhaseSpawnPosition(2, new Vector2(20, 700));

        //sequencer.addActorToPhase(3, duck4);
        //sequencer.addPhaseSpawnFrequency(3, 65);
        //sequencer.addPhaseSpawnPosition(3, new Vector2(0, 700));

        //sequencer.addActorToPhase(4, duck5);
        //sequencer.addPhaseSpawnFrequency(4, 70);
        //sequencer.addPhaseSpawnPosition(4, new Vector2(70, 700));

        //sequencer.addActorToPhase(5, hikeMaze);
        //sequencer.addPhaseSpawnFrequency(5, 90);
        //sequencer.addPhaseSpawnPosition(5, new Vector2(0, 700));

        sequencer.addActorToPhase(0, trerryClews);
        sequencer.addPhaseSpawnFrequency(0, 50);
        sequencer.addPhaseSpawnPosition(0, new Vector2(0, 700));

        this.game = game;
    }

    @Override
    public void act(float delta) {
        backgroundScrolling(delta);
        sequencer.update(delta, this);
        super.act(delta);
    }

    @Override
    public void draw() {
        sortActors();
        super.draw();
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
