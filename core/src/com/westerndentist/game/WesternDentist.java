package com.westerndentist.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class WesternDentist extends Game {
    public BitmapFont font;
    public Stage currentStage;

	@Override
	public void create () {
	    font = new BitmapFont();
        currentStage = new MainMenu(new FitViewport(800, 600));
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
}
