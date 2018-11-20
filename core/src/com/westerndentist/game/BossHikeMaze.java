package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BossHikeMaze extends Boss
{
    BossHikeMaze(Texture texture, float health, Vector2 position)
    {
        super();

        this.texture = texture;
        this.health = health;

        setPosition(position.x, position.y);
        bounds = new Rectangle(getX(), getY(), texture.getWidth()/2, texture.getHeight()/2);
        setDebug(true);
    }

    @Override
    public void act(float delta)
    {
        super.act(delta);
        killOnDead();
        takeDamageFromProjectile();
        updateBounds();
        fire();
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

    private void updateBounds()
    {
        bounds.setPosition(getX()+45, getY()+100);
    }


    private void killOnDead()
    {
        if (health <= 0) {
            remove();
        }
    }

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
            Gdx.app.log("Boss: ", "Something broke but I'm just gonna ignore it lol");
        }
    }

    private void fire()
    {
        try
        {
            Projectile blueFire = new NonVerticalProjectile(new Texture("Images/WesternDentist_BallBlue.png"), 500, getX()+5, getY()+5, "Enemy", 0, -1, true, false);
            Projectile greenFire = new NonVerticalProjectile(new Texture("Images/WesternDentist_BallGreen.png"), 500, getX()+60, getY()+60, "Enemy", 0, -1, true, false);

            getStage().addActor(blueFire);
            getStage().addActor(greenFire);

        }
        catch (NullPointerException e)
        {

        }
    }
}
