package com.westerndentist.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Entity extends Tile {

    protected float fireRate;
    protected float speed;
    protected float health;
    protected Rectangle collider;

    Entity() {
        super();
    }

    Entity(Texture texture, Vector2 position) {
        super(texture, position);
    }

    @Override
    public void create() {
        super.create();
    }

    @Override
    public void update() {
        // Test move in one direction
        // position.x -= 4;
        // position.y -= 4;
        super.update();
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
