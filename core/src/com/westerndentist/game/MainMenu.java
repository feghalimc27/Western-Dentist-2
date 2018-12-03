package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainMenu extends Stage {
    private WesternDentist game;
    private Table tableLeft;
    private Table tableRight;

    MainMenu(final WesternDentist game) {
        super(game.viewport);
        this.game = game;
        Image background = new Image(new Texture("images/mainmenu/background.jpg"));
        addActor(background);
        final Sound changeSelection = Gdx.audio.newSound(Gdx.files.internal("sounds/mainmenu/changeselect.mp3"));
        tableLeft = new Table();
        tableLeft.setSize(400, 600);
        tableLeft.left().bottom();
        addActor(tableLeft);
        tableRight = new Table();
        tableRight.setSize(400, 600);
        tableRight.setPosition(400, 0);
        tableRight.setVisible(false);
        tableRight.right().bottom();
        addActor(tableRight);
        final TextButton startButton = new TextButton("Start", game.textButtonStyle);
        startButton.getLabelCell().padTop(20f);
        startButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.changeStage(new Level2(game));
                return true;
            }
            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (fromActor != startButton && fromActor != startButton.getLabel()) {
                    changeSelection.play(game.soundEffectVolumeActual);
                }
            }
        });
        final TextButton optionsButton = new TextButton("Options", game.textButtonStyle);
        optionsButton.getLabelCell().padTop(20f);
        optionsButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                tableRight.setVisible(!tableRight.isVisible());
                return true;
            }
            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (fromActor != optionsButton && fromActor != optionsButton.getLabel()) {
                    changeSelection.play(game.soundEffectVolumeActual);
                }
            }
        });
        final TextButton quitButton = new TextButton("Quit", game.textButtonStyle);
        quitButton.getLabelCell().padTop(20f);
        quitButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (fromActor != quitButton && fromActor != quitButton.getLabel()) {
                    changeSelection.play(game.soundEffectVolumeActual);
                }
            }
        });
        tableLeft.add(startButton).size(32f, 64f).expandX().left().padLeft(64f).padBottom(4f);
        tableLeft.row();
        tableLeft.add(optionsButton).size(32f, 64f).expandX().left().padLeft(96f).padBottom(4f);
        tableLeft.row();
        tableLeft.add(quitButton).size(32f, 64f).expandX().left().padLeft(50f).padBottom(4f);
        final Label masterVolumeLabel = new Label("Master Volume", game.labelStyle);
        final Label masterVolumeValueLabel = new Label(String.valueOf((int)(game.masterVolume * 100)) + "%", game.labelStyle);
        final Slider masterVolumeSlider = new Slider(0f, 1f, 0.01f, false, game.sliderStyle);
        masterVolumeSlider.setValue(game.masterVolume);
        masterVolumeSlider.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.masterVolume = masterVolumeSlider.getValue();
                masterVolumeValueLabel.setText(String.valueOf((int)(game.masterVolume * 100)) + "%");
                game.music.setVolume(game.musicID, game.musicVolumeActual);
            }
        });
        final Label musicVolumeLabel = new Label("Music Volume", game.labelStyle);
        final Label musicVolumeValueLabel = new Label(String.valueOf((int)(game.musicVolume * 100)) + "%", game.labelStyle);
        final Slider musicVolumeSlider = new Slider(0f, 1f, 0.01f, false, game.sliderStyle);
        musicVolumeSlider.setValue(game.musicVolume);
        musicVolumeSlider.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.musicVolume = musicVolumeSlider.getValue();
                musicVolumeValueLabel.setText(String.valueOf((int)(game.musicVolume * 100)) + "%");
                game.music.setVolume(game.musicID, game.musicVolumeActual);
            }
        });
        final Label soundEffectVolumeLabel = new Label("Sound Effect Volume", game.labelStyle);
        final Label soundEffectVolumeValueLabel = new Label(String.valueOf((int)(game.soundEffectVolume * 100)) + "%", game.labelStyle);
        final Slider soundEffectVolumeSlider = new Slider(0f, 1f, 0.01f, false, game.sliderStyle);
        soundEffectVolumeSlider.setValue(game.soundEffectVolume);
        soundEffectVolumeSlider.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                game.soundEffectVolume = soundEffectVolumeSlider.getValue();
                soundEffectVolumeValueLabel.setText(String.valueOf((int)(game.soundEffectVolume * 100)) + "%");
            }
        });
        tableRight.add(masterVolumeLabel);
        tableRight.add(masterVolumeSlider);
        tableRight.add(masterVolumeValueLabel);
        tableRight.row();
        tableRight.add(musicVolumeLabel);
        tableRight.add(musicVolumeSlider);
        tableRight.add(musicVolumeValueLabel);
        tableRight.row();
        tableRight.add(soundEffectVolumeLabel);
        tableRight.add(soundEffectVolumeSlider);
        tableRight.add(soundEffectVolumeValueLabel);
        tableRight.row();
    }
}
