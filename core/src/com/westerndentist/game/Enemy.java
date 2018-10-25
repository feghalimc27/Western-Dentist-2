package com.westerndentist.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Enemy extends Actor {

    protected Texture texture;

    protected float speed;
    protected float fireRate;
    protected float health;

    protected Rectangle bounds = new Rectangle();

    Enemy() {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

    @Override
    public void act(float delta) {

    }

    private void updateBounds() {
        bounds.setPosition(getX(), getY());
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
