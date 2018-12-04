package com.westerndentist.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Power pickup class
 */
public class PowerPowerup extends Powerup{

    private Texture texture = new Texture("Images/powerPickup.png");

    /**
     * Constructor
     * @param lifetime lifetime of the object
     * @param value value of power to grant
     * @param position position of the powerup
     */
    PowerPowerup(float lifetime, float value, Vector2 position) {
        super(false);
        this.lifetime = lifetime;
        this.value = value;

        setPosition(position.x, position.y);
        bounds = new Rectangle(getX(), getY(), texture.getWidth(), texture.getHeight());
        setName("Power");
    }

    /**
     * Constructor
     * @param lifetime lifetime of the object
     * @param value value of power to grant
     * @param position position of the powerup
     * @param boss if it was spawned by the boss
     */
    PowerPowerup(float lifetime, float value, Vector2 position, boolean boss) {
        super(boss);
        this.lifetime = lifetime;
        this.value = value;

        setPosition(position.x, position.y);
        bounds = new Rectangle(getX(), getY(), texture.getWidth(), texture.getHeight());
        setName("Power");
    }


    /**
     * Show hitbox on debug
     * @param shapes
     */
    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        super.drawDebug(shapes);
    }

    /**
     * Draws the sprite
     * @param batch SpriteBatch to perform draws
     * @param parentAlpha parent alpha (unused)
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }
}
