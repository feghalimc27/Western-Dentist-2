package com.westerndentist.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Level4 extends Stage {

    private WesternDentist game;

    private Sequencer sequencer;

    private Image background = new Image(new Texture("Images/WesternDentist_Background.png")), background2 = new Image(new Texture("Images/WesternDentist_Background.png"));



    Level4(final WesternDentist game, FitViewport viewport) {
        super(viewport);

        background.setPosition(0, 2500);
        background2.setPosition(0, -2500);

        addActor(background);
        addActor(background2);

        SprayEnemy testActor = new SprayEnemy(new Texture("Images/sprayEnemySprite.png"), 100, 300);
        testActor.setPosition(400, 300);
        testActor.addAction(Actions.moveTo(400, 550, 5));

        addActor(new Player(300, 100));

        sequencer = new Sequencer();

        sequencer.addPhase(300);
        sequencer.addPhase(400);
        sequencer.addPhase(100000);

        sequencer.addActorToPhase(0, new DiagonalEnemy(new Texture("Images/basicEnemySprite.png"), 10, 100, -200));
        sequencer.addPhaseSpawnFrequency(0, 30);
        sequencer.addPhaseSpawnPosition(0, new Vector2(600, 700));

        sequencer.addActorToPhase(0, new DiagonalEnemy(new Texture("Images/basicEnemySprite.png"), 10, 100, 200));
        sequencer.addPhaseSpawnFrequency(0, 30);
        sequencer.addPhaseSpawnPosition(0, new Vector2(0, 700));

        sequencer.addActorToPhase(0, new DiagonalEnemy(new Texture("Images/basicEnemySprite.png"), 10, 100, -200));
        sequencer.addPhaseSpawnFrequency(0, 60);
        sequencer.addPhaseSpawnPosition(0, new Vector2(600, 700));

        sequencer.addActorToPhase(0, new DiagonalEnemy(new Texture("Images/basicEnemySprite.png"), 10, 100, 200));
        sequencer.addPhaseSpawnFrequency(0, 60);
        sequencer.addPhaseSpawnPosition(0, new Vector2(0, 700));

        sequencer.addActorToPhase(1, testActor);
        sequencer.addPhaseSpawnFrequency(1, 30);
        sequencer.addPhaseSpawnPosition(1, new Vector2(0, 1000));

        SprayEnemy nextActor = new SprayEnemy(new Texture("Images/sprayEnemySprite.png"), 100, 300);
        nextActor.addAction(Actions.moveTo(100, 550, 5));

        sequencer.addActorToPhase(1, nextActor);
        sequencer.addPhaseSpawnFrequency(1, 33);
        sequencer.addPhaseSpawnPosition(1, new Vector2(0, 1000));

        BossLevel4 boss = new BossLevel4();
        boss.addAction(Actions.moveTo(300, 400, 6));

        sequencer.addActorToPhase(2, boss);
        sequencer.addPhaseSpawnFrequency(2, 100);
        sequencer.addPhaseSpawnPosition(2, new Vector2(300, 1000));

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
        background.setY(background.getY() - 30 * delta);
        background2.setY(background2.getY() - 30 * delta);

        if (background.getY() <= -5000) {
            background.setY(5000);
        }

        if (background2.getY() <= -5000) {
            background2.setY(5000);
        }
    }
}
