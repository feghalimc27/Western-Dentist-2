package com.westerndentist.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Level4 extends Stage {

    private WesternDentist game;

    private Sequencer sequencer;

    Level4(final WesternDentist game, FitViewport viewport) {
        super(viewport);

        addActor(new Player(300, 300));

        sequencer = new Sequencer();

        sequencer.addPhase(30);
        sequencer.addPhase(40);
        sequencer.addPhase(100);

        sequencer.addActorToPhase(0, new BasicEnemy(new Texture("Images/tempRedCircle.png"), 500, 10, 30, new Vector2(0, 0)));
        sequencer.addPhaseSpawnFrequency(0, 10);
        sequencer.addPhaseSpawnPosition(0, new Vector2(300, 700));

        this.game = game;
    }

    @Override
    public void act(float delta) {
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
}