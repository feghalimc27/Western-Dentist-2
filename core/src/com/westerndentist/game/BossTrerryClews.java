package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BossTrerryClews extends Boss{

    private float checkX = 150;
    private float checkY = 365;

    private float rateCounter = 0;
    private float inCounter = 10;

    private float initX1 = 1;
    private float initX2 = -1;
    private float initY1 = -1;
    private float initY2 = 1;

    private boolean isSpawned = false;


    BossTrerryClews(Texture texture, float health, Vector2 position)
    {
        super();

        this.texture = texture;
        this.health = health;

        setPosition(position.x, position.y);
        bounds = new Rectangle(getX(), getY(), (float)(texture.getWidth()/2), (float)(texture.getHeight()/2));
        //setDebug(true);
    }

    @Override
    public void act(float delta)
    {
        if(!isSpawned)
        {
            isSpawned = true;
        }
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

    private void updateBounds()
    {
        bounds.setPosition(getX()+65, getY()+70);
    }


    private void killOnDead()
    {
        if (health <= 0)
        {
            giveScore();
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
        initX1 += delta;
        initX2 += delta;
        initY1 += delta;
        initY2 += delta;

        try
        {
            if (rateCounter == 0)
            {

                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 100, getX() + 105, getY() + 35, "Enemy", (float) Math.sin(initX1), (float) Math.sin(initY1), false, false));

                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 100, getX() + 190, getY() + 60, "Enemy", (float) Math.sin(initX2), (float) Math.sin(initY2), false, false));


                rateCounter += inCounter;

            }

            if (rateCounter != 0)
            {
                --rateCounter;
            }

            if (rateCounter < 0)
            {
                rateCounter = 0;
            }

        }

        catch (NullPointerException e)
        {

        }
    }

}
