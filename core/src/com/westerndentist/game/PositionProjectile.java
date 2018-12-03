package com.westerndentist.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

/**
 * Projectile that spawns, waits, then moves
 */
public class PositionProjectile extends Projectile {

    private float xFactor, yFactor;
    private float delay;
    private float timer = 0;

    private Vector2 playerPos;

    /**
     * Constructor
     * @param texture texture
     * @param speed initial speed
     * @param tag name of the object that spawned the projectile
     * @param x initial x
     * @param y initial y
     * @param playerPos position to move towards
     * @param delay how long before moving
     */
    PositionProjectile(Texture texture, float speed, String tag, float x, float y, Vector2 playerPos, float delay) {
        super(texture, speed, x, y,tag);

        this.delay = delay;
        this.playerPos = playerPos;

        float hyp = Vector2.dst(x, y, playerPos.x, playerPos.y);

        xFactor = x - playerPos.x;
        yFactor = y - playerPos.y;

        xFactor -= (int)xFactor;
        yFactor -= (int)yFactor;

        xFactor *= -1;
        yFactor *= -1;

        setPosition(x, y);
    }

    /**
     * Updates every time step
     * @param delta time since the last frame in seconds
     */
    @Override
    public void act(float delta) {
        if (timer < delay) {
            timer += 10 * delta;
        }
        else {
            moveBy(xFactor * delta * speed, yFactor * delta * speed);
        }

        updateBounds();
    }
}
