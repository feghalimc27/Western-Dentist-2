package com.westerndentist.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class UserInterface extends Stage {
    UserInterface(final WesternDentist game) {
        Image uiMain = new Image(new Texture("images/WesternDentist_UI.png"));
        addActor(uiMain);
    }
}
