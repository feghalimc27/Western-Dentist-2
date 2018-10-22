package com.westerndentist.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ExampleLevel extends Stage {
    ExampleLevel (Viewport viewport) {
        super(viewport);
        Image ui = new Image(new Texture("Images/WesternDentist_UI.png"));
        Image background = new Image(new Texture("Images/WesternDentist_Background.png"));
        ui.setZIndex(1241414);
        addActor(background);
        addActor(new Player(300, 300));
        addActor(ui);
    }
}
