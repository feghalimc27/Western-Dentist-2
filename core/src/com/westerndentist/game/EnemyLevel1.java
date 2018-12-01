package com.westerndentist.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class EnemyLevel1 extends Enemy
{
    private float rateCounter = 0;

    EnemyLevel1(Texture texture, float speed, float health, float fireRate, Vector2 position) {
        this.texture = texture;
        this.speed = speed;
        this.health = health;
        this.fireRate = fireRate;


        setPosition(position.x, position.y);
        bounds.set(getX(), getY(), texture.getWidth(), texture.getHeight());

        //setDebug(true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {

        moveBy( (speed/2) * delta, - 3);

        fire(delta);

        if(getY() < -50)
        {
            remove();
        }

        super.act(delta);
    }

    private void fire(float delta)
    {
        try
        {
            if (rateCounter == 0) {

                Projectile duckFire = new NonVerticalProjectile(new Texture("Images/WesternDentist_PlayerBurst.png"), 1000, getX() + 5, getY(), "Enemy", 0, -1, false, false);
                getStage().addActor(duckFire);
                rateCounter += fireRate * delta * 10;
            }

            if (rateCounter != 0) {
                rateCounter += 1000 * delta * 10;
            }

            if (rateCounter >= fireRate) {
                rateCounter = 0;
            }

        }
        catch (NullPointerException e)
        {

        }
    }

}
