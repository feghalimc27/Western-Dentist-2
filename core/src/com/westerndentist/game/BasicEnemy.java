package com.westerndentist.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Basic implementation of the Enemy abstract class
 */
public class BasicEnemy extends Enemy {

    /**
     * Constructor
     * @param texture the texture for the enemy
     * @param speed the initial speed of the enemy
     * @param health the health of the enemy
     * @param fireRate the enemy's fire rate
     * @param position the enemy's initial position
     */
    BasicEnemy(Texture texture, float speed, float health, float fireRate, Vector2 position) {
        this.texture = texture;
        this.speed = speed;
        this.health = health;
        this.fireRate = fireRate;

        setPosition(position.x, position.y);
        bounds.set(getX(), getY(), texture.getWidth(), texture.getHeight());
    }

    /**
     * Draws the enemy to the screen
     * @param batch SpriteBatch to draw the sprite
     * @param parentAlpha Alpha for the parent
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    /**
     * Updates every time step
     * @param delta the time since the last frame in seconds
     */
    @Override
    public void act(float delta) {
        moveBy(0, - speed * delta);

        if (getY() < - 200) {
            remove();
        }

        super.act(delta);
    }
}
