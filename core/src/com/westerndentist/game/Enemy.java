package com.westerndentist.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Enemy extends Actor {

    protected Texture texture;

    protected float speed;
    protected float fireRate;
    protected float health;

    private CircleShape bounds;

    Enemy() {
        bounds = new CircleShape();
        bounds.setRadius(texture.getWidth() / 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

    @Override
    public void act(float delta) {

    }

}
