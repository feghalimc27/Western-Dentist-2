package com.westerndentist.game;

import com.badlogic.gdx.graphics.Texture;

public class LinearProjectile extends Projectile {

    private float xFactor;
    private float yFactor;

    /**
     * Constructor -- only supported
     * @param texture       relative file path to the texture
     * @param initialSpeed  speed of the object's motion
     * @param x             x coordinate of the object's spawn position
     * @param y             y coordinate of the object's spawn position
     * @param tag           label for the object in gdx logs
     * @param xFactor       x component of motion
     * @param yFactor       y component of motion
     */
    LinearProjectile(Texture texture, float initialSpeed, float x, float y, String tag, float xFactor, float yFactor) {
        super(texture, initialSpeed, x, y, tag);

        this.xFactor = xFactor;
        this.yFactor = yFactor;

    }

    /**
     * perform necessary actions for this frame
     * @param delta time since last frame
     */
    @Override
    public void act(float delta) {
        super.act(delta);

        moveBy(xFactor * speed * delta, yFactor * speed * delta);

        updateBounds();

        if (getY() > 1000 || getY() < -100) {
            remove();
        }
    }

}
