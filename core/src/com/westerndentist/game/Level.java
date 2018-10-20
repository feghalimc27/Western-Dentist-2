package com.westerndentist.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Level implements Screen {

    private Array<Entity> objects;

    Level() {
        objects = new Array<Entity>();
    }

    public void addEntity(Entity entity) {
        objects.add(entity);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    public void render(SpriteBatch batch) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
