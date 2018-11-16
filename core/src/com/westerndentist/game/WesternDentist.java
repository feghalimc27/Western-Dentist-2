package com.westerndentist.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    private Stage currentStage;
    private Stage pauseMenu;
    private boolean paused = false;

	@Override
	public void create () {
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
        pauseMenu = new PauseMenu(this);
        Gdx.input.setInputProcessor(currentStage);
	}

	@Override
	public void render () {
        if (!(currentStage instanceof SplashScreen) && !(currentStage instanceof MainMenu) && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            paused = !paused;
            if (paused) {
                Gdx.input.setInputProcessor(pauseMenu);
            } else {
                Gdx.input.setInputProcessor(currentStage);
            }
        }
	    Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (!paused) {
            currentStage.act(Gdx.graphics.getDeltaTime());
        } else {
            pauseMenu.act(Gdx.graphics.getDeltaTime());
            pauseMenu.draw();
        }
        currentStage.draw();
        musicVolumeActual = masterVolume*musicVolume;
        soundEffectVolumeActual = masterVolume*soundEffectVolume;
		super.render();
	}

	@Override
    public void resize (int width, int height) {
        currentStage.getViewport().update(width, height, true);
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
	    currentStage.clear();
	    currentStage.dispose();
        currentStage = newStage;
        Gdx.input.setInputProcessor(currentStage);
    }
}
