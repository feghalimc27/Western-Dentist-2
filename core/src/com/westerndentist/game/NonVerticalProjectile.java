package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Projectile for nonvertical movement
 */
public class NonVerticalProjectile extends Projectile {

    private float xFactor, yFactor, firstX, firstY;
    private float angle;
    private boolean curve, dir;

    /**
     * Constructor
     * @param texture texture
     * @param initialSpeed initial speed
     * @param x initial x position
     * @param y initial y position
     * @param tag name of the object that spawned
     * @param xFactor multiplicative factor for x
     * @param yFactor multiplicative factor for y
     * @param curve handles sine curve
     * @param dir handles bossPhase damage
     */
    NonVerticalProjectile(Texture texture, float initialSpeed, float x, float y, String tag, float xFactor, float yFactor, boolean curve, boolean dir) {
        super(texture, initialSpeed, x, y, tag);

        this.xFactor = xFactor;
        this.yFactor = yFactor;

        firstX = xFactor;
        firstY = yFactor;

        angle = getAngle(xFactor, yFactor);

        this.curve = curve;
        this.dir = dir;
    }

    /**
     * Updates every time step
     * @param delta time since the last frame in seconds
     */
    @Override
    public void act(float delta) {
        super.act(delta);

        moveBy(xFactor * speed * delta, yFactor * speed * delta);

        updateBounds();

        if (getY() > 1000 || getY() < -100) {
            remove();
        }

        if (curve) {
            mod(delta);
        }
    }

    /**
     * Handles curving
     * @param delta time since the last frame in seconds
     */
    private void mod(float delta) {
        //setOrigin(origin.x, origin.y);
        //angle += 360 * delta;
        //setRotation(angle);

        // Cool boss phase 1 bullet stuff
        if (dir) {
            angle += 2 * delta;
            xFactor = firstX + (float) Math.cos(angle);
            yFactor = firstY + (float) Math.sin(angle);
        }
        else {
            angle += 2 * delta;
            xFactor = firstX + (float) Math.sin(angle);
            yFactor = firstY + (float) Math.cos(angle);
        }
    }

    /**
     * Gets the angle between two factors
     * @param x x
     * @param y y
     * @return float angle
     */
    private float getAngle(float x, float y) {
        if (x == 1 && y == 0) {
            return 0;
        }
        else if (x == 0 && y == 1) {
            return 90;
        }
        else if (x == -1 && y == 0) {
            return 180;
        }
        else if (x == 0 && y == -1) {
            return 270;
        }
        else if (x == 1 && y == 1) {
            return 45;
        }
        else if (x == -1 && y == 1) {
            return 135;
        }
        else if (x == -1 && y == -1) {
            return 225;
        }
        else if (x == 1 && y == -1) {
            return 315;
        }
        else {
            return 0;
        }
    }
}
