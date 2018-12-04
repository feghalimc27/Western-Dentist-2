package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class PauseMenu extends Stage {
    private Table table;

    PauseMenu(final WesternDentist game) {
        super(game.viewport);
        final Sound changeSelection = Gdx.audio.newSound(Gdx.files.internal("sounds/changeselect.mp3"));
        Image background = new Image(new Texture("images/background.png"));
        addActor(background);
        table = new Table();
        table.setFillParent(true);
        table.center();
        addActor(table);
        final TextButton restartLevelButton = new TextButton("Restart Level", game.textButtonStyle);
        restartLevelButton.getLabelCell().padTop(20f);
        restartLevelButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.restartStage();
                return true;
            }
            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (fromActor != restartLevelButton && fromActor != restartLevelButton.getLabel()) {
                    changeSelection.play(WesternDentist.soundEffectVolumeActual);
                }
            }
        });
        final TextButton quitToMainMenuButton = new TextButton("Quit to Main Menu", game.textButtonStyle);
        quitToMainMenuButton.getLabelCell().padTop(20f);
        quitToMainMenuButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.player.setScore(-1);
                game.changeStage(new MainMenu(game));
                return true;
            }
            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (fromActor != quitToMainMenuButton && fromActor != quitToMainMenuButton.getLabel()) {
                    changeSelection.play(WesternDentist.soundEffectVolumeActual);
                }
            }
        });
        final TextButton quitToDesktopButton = new TextButton("Quit to Desktop", game.textButtonStyle);
        quitToDesktopButton.getLabelCell().padTop(20f);
        quitToDesktopButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (fromActor != quitToDesktopButton && fromActor != quitToDesktopButton.getLabel()) {
                    changeSelection.play(WesternDentist.soundEffectVolumeActual);
                }
            }
        });
        table.add(restartLevelButton).size(32f, 64f);
        table.row();
        table.add(quitToMainMenuButton).size(32f, 64f);
        table.row();
        table.add(quitToDesktopButton).size(32f, 64f);
    }
}
