package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenu extends Stage {
    private WesternDentist game;
    private Table table;
    private BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/touhoufont.fnt"), Gdx.files.internal("fonts/touhoufont.png"), false);

    MainMenu(final WesternDentist game, final FitViewport viewport) {
        super(viewport);
        this.game = game;
        table = new Table();
        table.setFillParent(true);
        table.center();
        addActor(table);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(
                new TextureRegionDrawable(new TextureRegion(new Texture("images/mainmenu/buttonUp.png"))),
                new TextureRegionDrawable(new TextureRegion(new Texture("images/mainmenu/buttonDown.png"))),
                null,
                font);
        TextButton playButton = new TextButton("Play Game", textButtonStyle);
        playButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.changeStage(new ExampleLevel(game, viewport));
                return true;
            }
        });
        TextButton quitButton = new TextButton("Play Game", textButtonStyle);
        quitButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });
        table.add(playButton);
        table.row();
        table.add(quitButton);
    }
}
