package com.westerndentist.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Boss extends Actor {

    protected Texture texture;
    protected float health;

    protected Rectangle bounds;

    private float score = 10000000;


    Boss() {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateBounds();
        score -= 10 * delta;
    }

    protected void giveScore() {
        for (Actor actor: getStage().getActors()) {
            if (Player.class.isInstance(actor)) {
                ((Player)actor).addScore(score);
            }
        }
    }

    private void updateBounds() {
        bounds.setPosition(getX(), getY());
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
