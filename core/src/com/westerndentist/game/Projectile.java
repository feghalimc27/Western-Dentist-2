package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Projectile extends Actor{

    private Texture texture;
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
        Gdx.app.log("Projectile Z axis", "" + getZIndex());
        moveBy(0,  (speed * delta));

        if (hasParent()) {
            Gdx.app.log("Parent", getParent().getName());
        }

        if (getY() > 1000 || getY() < -100) {
            remove();
        }
    }
}
