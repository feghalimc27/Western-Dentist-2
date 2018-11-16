package com.westerndentist.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PauseMenu extends Stage {
    private WesternDentist game;

    PauseMenu(final WesternDentist game) {
        super(game.viewport);
        this.game = game;
    }
}
