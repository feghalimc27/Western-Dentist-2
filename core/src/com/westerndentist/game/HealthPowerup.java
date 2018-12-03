package com.westerndentist.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Health pickup, extends powerup
 */
public class HealthPowerup extends Powerup {

    private Texture texture = new Texture("Images/healthPickup.png");

    /**
     * Constructor
     * @param lifetime lifetime of the powerup
     * @param value how much health is given
     * @param position initial spawn position
     */
    HealthPowerup(float lifetime, float value, Vector2 position) {
        super(false);
        this.lifetime = lifetime;
        this.value = value;

        setPosition(position.x, position.y);
        bounds = new Rectangle(getX(), getY(), texture.getWidth(), texture.getHeight());
        setName("Health");
    }

    /**
     * Constructor
     * @param lifetime lifetime of the powerup
     * @param value how much health is given
     * @param position initial spawn position
     * @param boss boolean if was spawned from boss or not
     */

    HealthPowerup(float lifetime, float value, Vector2 position, boolean boss) {
        super(boss);
        this.lifetime = lifetime;
        this.value = value;
        this.boss = boss;

        setPosition(position.x, position.y);
        bounds = new Rectangle(getX(), getY(), texture.getWidth(), texture.getHeight());
        setName("Health");
    }


    /**
     * Draws the object to the screen
     * @param batch
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }
}
