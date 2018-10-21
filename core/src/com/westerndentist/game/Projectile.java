package com.westerndentist.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Projectile extends Actor{

    private Texture texture = new Texture("");
    private float speed = 0;

    Projectile(Texture texture, float initialSpeed, float x, float y) {
        this.texture = texture;
        speed = initialSpeed;
        setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

    @Override
    public void act(float delta) {
        moveBy(getX(), getY() + speed * delta);
    }
}
