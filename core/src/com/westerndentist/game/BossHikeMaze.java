package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import java.lang.Math;

public class BossHikeMaze extends Boss
{
    double blueSin = 0;
    double greenSin = 0;

    float checkX = 100;
    float checkY = 300;

    /**
     * Constructor for initializing the boss
     * @param texture - the texture image for the boss
     * @param health - the health amount for the boss
     * @param position - the initial position of the boss
     */
    BossHikeMaze(Texture texture, float health, Vector2 position)
    {
        super();

        this.texture = texture;
        this.health = health;

        setPosition(position.x, position.y);
        bounds = new Rectangle(getX(), getY(), (float)(texture.getWidth()/2.5), (float)(texture.getHeight()/2.5));
        //setDebug(true);
    }

    @Override
    public void act(float delta)
    {
        killOnDead();
        takeDamageFromProjectile();
        if(getX() == checkX && getY() == checkY)
        {
            fire(delta);
        }
        super.act(delta);
        updateBounds();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        super.drawDebug(shapes);
    }


    /**
     * Updates the bounds to be more accurate with the bosses position
     */
    private void updateBounds()
    {
        bounds.setPosition(getX()+60, getY()+100);
    }

    /**
     * Checks if the health of the boss is zero, adds to the score of a flat sum, then removes the boss
     */
    private void killOnDead()
    {
        if (health <= 0)
        {
            giveScore();
            remove();
        }
    }

    /**
     * This function checks if it receives a projectile and decrement the health
     */
    private void takeDamageFromProjectile()
    {
        try {
            for (Actor actor : getStage().getActors()) {
                if (Enemy.class.isInstance(actor)) {
                    continue;
                }

                if (Projectile.class.isInstance(actor)) {
                    if (bounds.overlaps(((Projectile) actor).getBounds())) {
                        if (actor.getName() != "Enemy") {
                            health -= ((Projectile) actor).getDamage();
                            ((Projectile) actor).destroy();
                        }
                    }
                }
            }
        }
        catch (NullPointerException e) {
            Gdx.app.log("Boss: ", "Actor was destroyed before position collision could be checked");
        }
    }

    /**
     * This function allows the boss to fire projectiles at the enemy
     * @param delta - time in seconds since the last frame
     */
    private void fire(float delta)
    {
        blueSin += delta * 2;
        greenSin += delta * 2;
        try
        {
            Projectile blueFire = new NonVerticalProjectile(new Texture("Images/WesternDentist_BallBlue.png"), 1000, getX(), getY()+60, "Enemy", (float)(-2 * Math.sin(blueSin)),  -2, true, false);
            Projectile greenFire = new NonVerticalProjectile(new Texture("Images/WesternDentist_BallGreen.png"), 1000, getX()+200, getY()+60, "Enemy", (float)(-2 * Math.sin(greenSin)), -2, true, false);

            getStage().addActor(blueFire);
            getStage().addActor(greenFire);

        }

        catch (NullPointerException e)
        {
            Gdx.app.log("Boss: ", "List reshuffled");
        }
    }
}
