package com.westerndentist.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class NonVerticalProjectile extends Projectile {

    private float xFactor, yFactor;
    private boolean curve;

    NonVerticalProjectile(Texture texture, float initialSpeed, float x, float y, String tag, float xFactor, float yFactor, boolean curve) {
        super(texture, initialSpeed, x, y, tag);

        this.xFactor = xFactor;
        this.yFactor = yFactor;
        this.curve = curve;
    }

    @Override
    public void act(float delta) {

        moveBy(xFactor * speed * delta, yFactor * speed * delta);


        updateBounds();

        if (getY() > 1000 || getY() < -100) {
            remove();
        }

        if (curve) {
            modX();
            modY();
        }
    }

    private void modX() {

    }

    private void modY() {

    }

}
