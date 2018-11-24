package com.westerndentist.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class WesternDentist extends Game {
    public float masterVolume = 1.0f;
    public float musicVolume = 1.0f;
    public float soundEffectVolume = 1.0f;
    public float musicVolumeActual = masterVolume*musicVolume;
    public float soundEffectVolumeActual = masterVolume*soundEffectVolume;
    public BitmapFont font;
    public TextButton.TextButtonStyle textButtonStyle;
    public Slider.SliderStyle sliderStyle;
    public Label.LabelStyle labelStyle;
    public FitViewport viewport;
    public Sound music;
    public long musicID;
    private Stage currentStage;
    private Stage userInterface;
    private Stage pauseMenu;
    private boolean paused = false;
    private Sound theme;
    private Sound level1;
    private Sound level2;
    private Sound level3;
    private Sound level4;
    private Sound bossWarning;
    private Sound level1Boss;
    private Sound level2Boss;
    private Sound level3Boss;
    private Sound level4Boss;

	@Override
	public void create () {
        theme = Gdx.audio.newSound(Gdx.files.internal("sounds/mainmenu/theme.mp3"));
        level1 = Gdx.audio.newSound(Gdx.files.internal("sounds/level1.mp3"));
        //level2 = Gdx.audio.newSound(Gdx.files.internal(""));
        //level3 = Gdx.audio.newSound(Gdx.files.internal(""));
        level4 = Gdx.audio.newSound(Gdx.files.internal("sounds/level4.mp3"));
        bossWarning = Gdx.audio.newSound(Gdx.files.internal("sounds/bosswarning.mp3"));
        level1Boss = Gdx.audio.newSound(Gdx.files.internal("sounds/level1Boss.mp3"));
        //level2Boss = Gdx.audio.newSound(Gdx.files.internal(""));
        //level3Boss = Gdx.audio.newSound(Gdx.files.internal(""));
        level4Boss = Gdx.audio.newSound(Gdx.files.internal("sounds/level4Boss.mp3"));
	    font = new BitmapFont(Gdx.files.internal("fonts/touhoufont.fnt"), Gdx.files.internal("fonts/touhoufont.png"), false);
        textButtonStyle = new TextButton.TextButtonStyle(
                null,
                new TextureRegionDrawable(new TextureRegion(new Texture("images/mainmenu/buttonDown.png"))),
                null,
                font);
        textButtonStyle.over = new TextureRegionDrawable(new TextureRegion(new Texture("images/mainmenu/buttonOver.png")));
        sliderStyle = new Slider.SliderStyle(
                new TextureRegionDrawable(new TextureRegion(new Texture("images/mainmenu/sliderBackground.png"))),
                new TextureRegionDrawable(new TextureRegion(new Texture("images/mainmenu/sliderKnob.png")))
        );
        labelStyle = new Label.LabelStyle(font, null);
	    viewport = new FitViewport(800, 600);
        currentStage = new SplashScreen(this);
        userInterface = new UserInterface(this);
        pauseMenu = new PauseMenu(this);
        Gdx.input.setInputProcessor(currentStage);
	}

	@Override
	public void render () {
        if (!(currentStage instanceof SplashScreen) && !(currentStage instanceof MainMenu) && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            paused = !paused;
            if (paused) {
                music.pause();
                Gdx.input.setInputProcessor(pauseMenu);
            } else {
                music.resume();
                pauseMenu.clear();
                pauseMenu.dispose();
                pauseMenu = new PauseMenu(this);
                Gdx.input.setInputProcessor(currentStage);
            }
        }
	    Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (!paused) {
            currentStage.act(Gdx.graphics.getDeltaTime());
            currentStage.draw();
            if (!(currentStage instanceof SplashScreen) && !(currentStage instanceof MainMenu)) {
                userInterface.act(Gdx.graphics.getDeltaTime());
                userInterface.draw();
            }
        } else {
            currentStage.draw();
            if (!(currentStage instanceof SplashScreen) && !(currentStage instanceof MainMenu)) {
                userInterface.draw();
            }
            pauseMenu.act(Gdx.graphics.getDeltaTime());
            pauseMenu.draw();
        }
        musicVolumeActual = masterVolume*musicVolume;
        soundEffectVolumeActual = masterVolume*soundEffectVolume;
		super.render();
	}

	@Override
    public void resize (int width, int height) {
        currentStage.getViewport().update(width, height, true);
        userInterface.getViewport().update(width, height, true);
        pauseMenu.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
	    super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    public void changeStage(Stage newStage) {
        paused = false;
        pauseMenu.clear();
        pauseMenu.dispose();
        pauseMenu = new PauseMenu(this);
	    currentStage.clear();
	    currentStage.dispose();
        currentStage = newStage;
        userInterface.clear();
        if (!(currentStage instanceof SplashScreen) && !(currentStage instanceof MainMenu)) {
            userInterface.dispose();
            userInterface = new UserInterface(this);
        }
        Gdx.input.setInputProcessor(currentStage);
        playMusic(false);
    }

    public void restartStage() {
	    paused = false;
        pauseMenu.clear();
        pauseMenu.dispose();
        pauseMenu = new PauseMenu(this);
        userInterface.clear();
        userInterface.dispose();
        userInterface = new UserInterface(this);
        currentStage.clear();
        currentStage.dispose();
        if (currentStage instanceof Level1) {
            currentStage = new Level1(this);
        /*} else if (currentStage instanceof Level2 {

        } else if (currentStage instanceof Level3) {*/

        } else if (currentStage instanceof Level4) {
            currentStage = new Level4(this);
        }
        Gdx.input.setInputProcessor(currentStage);
        playMusic(false);
    }

    public void playMusic(boolean bossBattle) {
	    if (music != null) {
            music.stop();
        }
	    if (bossBattle) {
            if (currentStage instanceof Level1) {
                music = level1Boss;
            /*} else if (currentStage instanceof Level2) {
                music = level2Boss;
            } else if (currentStage instanceof Level3) {
                music = level3Boss;*/
            } else if (currentStage instanceof Level4) {
                music = level4Boss;
            }
            final Image warningBG = new Image(new Texture("images/warning_background.png"));
            final Image warningFG = new Image(new Texture("images/warning_foreground.png"));
            warningBG.setPosition(-971, 150);
            warningBG.addAction(Actions.moveTo(0, 150, 8f));
            warningFG.setPosition(0, 229);
            warningFG.addAction(Actions.moveTo(-971, 229, 8f));
            SequenceAction sequence = Actions.sequence();
            sequence.addAction(Actions.delay(4f));
            sequence.addAction(Actions.run(new Runnable() {
                @Override
                public void run() {
                    musicID = music.loop(musicVolumeActual);
                    warningBG.remove();
                    warningFG.remove();
                }
            }));
            userInterface.addAction(sequence);
            userInterface.addActor(warningBG);
            userInterface.addActor(warningFG);
            bossWarning.play(soundEffectVolumeActual);
        } else {
            if (currentStage instanceof MainMenu) {
                music = theme;
            } else if (currentStage instanceof Level1) {
                music = level1;
            /*} else if (currentStage instanceof Level2) {
                music = level2;
            } else if (currentStage instanceof Level3) {
                music = level3;*/
            } else if (currentStage instanceof Level4) {
                music = level4;
            }
            musicID = music.loop(musicVolumeActual);
        }
    }
}
