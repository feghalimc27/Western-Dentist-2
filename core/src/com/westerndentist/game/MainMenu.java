package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenu extends Stage {
    private WesternDentist game;
    private Table tableLeft;
    private Table tableRight;
    private BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/touhoufont.fnt"), Gdx.files.internal("fonts/touhoufont.png"), false);

    MainMenu(final WesternDentist game, final FitViewport viewport) {
        super(viewport);
        this.game = game;
        Image background = new Image(new Texture("images/mainmenu/background.jpg"));
        addActor(background);
        final Sound theme = Gdx.audio.newSound(Gdx.files.internal("sounds/mainmenu/theme.mp3"));
        final Sound changeSelection = Gdx.audio.newSound(Gdx.files.internal("sounds/mainmenu/changeselect.mp3"));
        final long themeID = theme.loop(WesternDentist.musicVolumeActual);
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
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(
                null,
                new TextureRegionDrawable(new TextureRegion(new Texture("images/mainmenu/buttonDown.png"))),
                null,
                font);
        textButtonStyle.over = new TextureRegionDrawable(new TextureRegion(new Texture("images/mainmenu/buttonOver.png")));
        final TextButton startButton = new TextButton("Start", textButtonStyle);
        startButton.getLabelCell().padTop(20f);
        startButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                theme.dispose();
                game.changeStage(new ExampleLevel(game, viewport));
                return true;
            }
            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (fromActor != startButton && fromActor != startButton.getLabel()) {
                    changeSelection.play(WesternDentist.soundEffectVolumeActual);
                }
            }
        });
        final TextButton optionsButton = new TextButton("Options", textButtonStyle);
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
                    changeSelection.play(WesternDentist.soundEffectVolumeActual);
                }
            }
        });
        final TextButton quitButton = new TextButton("Quit", textButtonStyle);
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
                    changeSelection.play(WesternDentist.soundEffectVolumeActual);
                }
            }
        });
        tableLeft.add(startButton).size(32f, 64f).expandX().left().padLeft(64f).padBottom(4f);
        tableLeft.row();
        tableLeft.add(optionsButton).size(32f, 64f).expandX().left().padLeft(96f).padBottom(4f);
        tableLeft.row();
        tableLeft.add(quitButton).size(32f, 64f).expandX().left().padLeft(50f).padBottom(4f);
        Slider.SliderStyle sliderStyle = new Slider.SliderStyle(
                new TextureRegionDrawable(new TextureRegion(new Texture("images/mainmenu/sliderBackground.png"))),
                new TextureRegionDrawable(new TextureRegion(new Texture("images/mainmenu/sliderKnob.png")))
        );
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, null);
        final Label masterVolumeLabel = new Label("Master Volume", labelStyle);
        final Label masterVolumeValueLabel = new Label(String.valueOf((int)(WesternDentist.masterVolume * 100)) + "%", labelStyle);
        final Slider masterVolumeSlider = new Slider(0f, 1f, 0.01f, false, sliderStyle);
        masterVolumeSlider.setValue(WesternDentist.masterVolume);
        masterVolumeSlider.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                WesternDentist.masterVolume = masterVolumeSlider.getValue();
                masterVolumeValueLabel.setText(String.valueOf((int)(WesternDentist.masterVolume * 100)) + "%");
                theme.setVolume(themeID, WesternDentist.musicVolumeActual);
            }
        });
        final Label musicVolumeLabel = new Label("Music Volume", labelStyle);
        final Label musicVolumeValueLabel = new Label(String.valueOf((int)(WesternDentist.musicVolume * 100)) + "%", labelStyle);
        final Slider musicVolumeSlider = new Slider(0f, 1f, 0.01f, false, sliderStyle);
        musicVolumeSlider.setValue(WesternDentist.musicVolume);
        musicVolumeSlider.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                WesternDentist.musicVolume = musicVolumeSlider.getValue();
                musicVolumeValueLabel.setText(String.valueOf((int)(WesternDentist.musicVolume * 100)) + "%");
                theme.setVolume(themeID, WesternDentist.musicVolumeActual);
            }
        });
        final Label soundEffectVolumeLabel = new Label("Sound Effect Volume", labelStyle);
        final Label soundEffectVolumeValueLabel = new Label(String.valueOf((int)(WesternDentist.soundEffectVolume * 100)) + "%", labelStyle);
        final Slider soundEffectVolumeSlider = new Slider(0f, 1f, 0.01f, false, sliderStyle);
        soundEffectVolumeSlider.setValue(WesternDentist.soundEffectVolume);
        soundEffectVolumeSlider.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                WesternDentist.soundEffectVolume = soundEffectVolumeSlider.getValue();
                soundEffectVolumeValueLabel.setText(String.valueOf((int)(WesternDentist.soundEffectVolume * 100)) + "%");
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
