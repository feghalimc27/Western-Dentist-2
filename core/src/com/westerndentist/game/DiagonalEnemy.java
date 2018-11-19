package com.westerndentist.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class DiagonalEnemy extends Enemy {

    private float rateCounter = 0;
    private float speed = 100;

    DiagonalEnemy(Texture texture, float health, float fireRate, float speed) {
        super();

        this.texture = texture;
        this.health = health;this.fireRate = fireRate;

        this.speed = speed;

        bounds.set(getX(), getY(), texture.getWidth(), texture.getHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        move(delta);
        fire(delta);
    }

    private void fire (float delta) {
        if (rateCounter == 0) {
            try {
                getStage().addActor(new Projectile(new Texture("Images/WesternDentist_BossBurst.png"), -400, getX(), getY(), "Enemy"));
            }
            catch (NullPointerException e) {

            }
            rateCounter = fireRate;
        }

        else {
            rateCounter -= 1000 * delta;
        }

        if (rateCounter <= 0) {
            rateCounter = 0;
        }
    }

    private void move(float delta) {
        moveBy(speed * delta, -Math.abs(speed * delta));
    }
}
