package com.westerndentist.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class ExampleLevel extends Stage {

    private WesternDentist game;

    ExampleLevel (final WesternDentist game, final FitViewport viewport) {
        super(viewport);
        // Load level
        // Load UI and Background
        Image ui = new Image(new Texture("Images/WesternDentist_UI.png"));
        Image background = new Image(new Texture("Images/WesternDentist_Background.png"));
        // Set Z axis for UI and set background name
        ui.setName("UI_FRAME");
        ui.setZIndex(300000);
        background.setName("BACKGROUND");
        // Add background, player, UI
        addActor(background);
        addActor(new Player(300, 300));
        addActor(ui);

        this.game = game;
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
