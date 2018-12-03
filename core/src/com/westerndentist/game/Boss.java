package com.westerndentist.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * The bass abstract class for the Boss
 */
public abstract class Boss extends Actor {

    protected Texture texture;
    protected float health;

    protected Rectangle bounds;

    private float score = 10000000;


    /**
     * Constructor
     */
    Boss() {

    }

    /**
     * Draws the actor to the screen, calls super.draw()
     * @param batch SpriteBatch for drawing
     * @param parentAlpha Alpha for the parent
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    /**
     * Updates on each time step
     * @param delta the time in seconds since the last frame
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        updateBounds();
        score -= 10000 * delta;
    }

    /**
     * Grants the player score on death
     */
    protected void giveScore() {
        for (Actor actor: getStage().getActors()) {
            if (Player.class.isInstance(actor)) {
                ((Player)actor).addScore(score);
                break;
            }
        }
    }

    /**
     * Updates the bounding box position of the actor
     */
    private void updateBounds() {
        bounds.setPosition(getX(), getY());
    }

    /**
     * Returns the bounds for collision detection
     * @return Rectangle Bounds
     */
    public Rectangle getBounds() {
        return bounds;
    }
}
