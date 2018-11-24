package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BossTrerryClews extends Boss{

    float checkX = 100;
    float checkY = 350;

    BossTrerryClews(Texture texture, float health, Vector2 position)
    {
        super();

        this.texture = texture;
        this.health = health;

        setPosition(position.x, position.y);
        bounds = new Rectangle(getX(), getY(), (float)(texture.getWidth()/2), (float)(texture.getHeight()/2));
        setDebug(true);
    }

    @Override
    public void act(float delta)
    {
        killOnDead();
        takeDamageFromProjectile();
        if(getX() == checkX && getY() == checkY)
        {
            //fire(delta);
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

    private void updateBounds()
    {
        bounds.setPosition(getX()+65, getY()+70);
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


    private void fire(float delta)
    {
        try
        {
            Projectile redFire = new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 1000, getX(), getY()+60, "Enemy", 0,  -2, false, false);

            getStage().addActor(redFire);

        }
        catch (NullPointerException e)
        {

        }
    }

}
