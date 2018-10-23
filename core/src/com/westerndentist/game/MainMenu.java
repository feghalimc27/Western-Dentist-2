package com.westerndentist.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenu extends Stage {
    private WesternDentist game;
    private Table table;

    MainMenu(final WesternDentist game, final FitViewport viewport) {
        super(viewport);
        this.game = game;
        table = new Table();
        table.setFillParent(true);
        table.center();
    }
}
