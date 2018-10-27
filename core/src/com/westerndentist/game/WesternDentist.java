package com.westerndentist.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class WesternDentist extends Game {
    private Stage currentStage;
    private FitViewport viewport;

	@Override
	public void create () {
	    viewport = new FitViewport(800, 600);
        currentStage = new SplashScreen(this, viewport);
        Gdx.input.setInputProcessor(currentStage);
	}

	@Override
	public void render () {
	    Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        currentStage.act(Gdx.graphics.getDeltaTime());
        currentStage.draw();
		super.render();
	}

	@Override
    public void resize (int width, int height) {
        currentStage.getViewport().update(width, height, true);
    }

    public void changeStage(Stage newStage) {
	    currentStage.clear();
	    currentStage.dispose();
        currentStage = newStage;
        Gdx.input.setInputProcessor(currentStage);
    }
}
