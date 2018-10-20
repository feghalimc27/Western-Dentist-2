package com.westerndentist.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Tile {
    protected Texture texture;
    protected Vector2 position;

    Tile() {
        texture = null;
        position = new Vector2(0, 0);
    }

    Tile (Texture texture, Vector2 position) {
        this.texture = texture;
        this.position = position;
    }

    public void create() {

    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public void update() {

    }

    public void dispose() {
        texture.dispose();
    }
}
