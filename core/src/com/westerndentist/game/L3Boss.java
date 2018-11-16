package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class L3Boss extends Boss
{
    private Texture texture = new Texture("Images/Boss_L3.png");
    private Vector2 movement = new Vector2(0, 0);
    private Rectangle bounds = new Rectangle();

    private float speed = -50;
    private float fireRate = 10;
    private int rateCounter = 0;

    private int fireX1 = 0;
    private int fireY1 = 1000;
    private int XScale1 = 100;
    private int YScale1 = 100;
    private int fireX2 = 0;
    private int fireY2 = -1000;
    private int XScale2 = 100;
    private int YScale2 = 100;
    private int fireX3 = 0;
    private int fireY3 = -1000;
    private int fireX4 = 0;
    private int fireY4 = 1000;

    private boolean checkLeft = true, checkRight = true, checkTop = true, checkBottom = true;

    L3Boss() {
        setPosition(0, 0);
        setBounds(0, 0, texture.getWidth(), texture.getHeight());
        setName("Mr. Muskrat");
        health = 5000;
        // TODO: Boss Spawn Sound?
    }

    L3Boss(Vector2 position) {
        setPosition(position.x, position.y);
        setBounds(position.x, position.y, texture.getWidth(), texture.getHeight());
        setName("Mr. Muskrat");
        health = 5000;
        // TODO: Boss Spawn Sound?
    }

    L3Boss(float x, float y) {
        setPosition(x, y, Align.center);
        bounds.set(x, y, texture.getWidth()/2, texture.getHeight()/2);
        bounds.setCenter(x + texture.getWidth() / 4, y - texture.getHeight() / 4);
        setName("Mr. Muskrat");
        health = 5000;
        // TODO: Boss Spawn Sound?
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

    @Override
    public void act(float delta) {
        move();
        applyMovement(delta);
        if(getY() <= 350)
            fire(delta);
        updateBounds();
        checkCollision();
        killOnDead();
    }

    private void killOnDead()
    {
        if (health <= 0)
        {
            remove();
            Gdx.app.log("Mr. Muskrat", "That wasn\'t very Cash Money of you");
            // TODO: Play death Sound?
        }
    }

    private void move() {
        if (getY() > 250)
            movement.y += speed;
        else
            movement.y = 0;
    }

    private void applyMovement(float delta) {
        movement.x *= delta;
        movement.y *= delta;
        moveBy(movement.x, movement.y);
        setPosition(getX() + movement.x, getY() + movement.y);

        movement = new Vector2(0, 0);
    }

    private void fire(float delta) {
        // TODO: Change Projectile Texture
        if (rateCounter == 0) {
            // TODO: Play Firing Sound?
            getStage().addActor(new NonVerticalProjectile(new Texture("images/L3_Projectile.png"), -300, getX() + texture.getWidth()/2 - 10, getY() + texture.getHeight()/2 - 10, "Muskrat Missile", (float)fireX1/(float)1000, (float)fireY1/(float)1000, true));
            getStage().addActor(new NonVerticalProjectile(new Texture("images/L3_Projectile.png"), -300, getX() + texture.getWidth()/2 - 10, getY() + texture.getHeight()/2 - 10, "Muskrat Missile", (float)fireX2/(float)1000, (float)fireY2/(float)1000, true));
            getStage().addActor(new NonVerticalProjectile(new Texture("images/L3_Projectile.png"), -300, getX() + texture.getWidth()/2 - 10, getY() + texture.getHeight()/2 - 10, "Muskrat Missile", (float)fireX3/(float)1000, (float)fireY3/(float)1000, true));
            getStage().addActor(new NonVerticalProjectile(new Texture("images/L3_Projectile.png"), -300, getX() + texture.getWidth()/2 - 10, getY() + texture.getHeight()/2 - 10, "Muskrat Missile", (float)fireX4/(float)1000, (float)fireY4/(float)1000, true));
            fireX1 += XScale1;
            fireY1 += YScale1;
            fireX2 += XScale2;
            fireY2 += YScale2;
            fireX3 -= XScale2;
            fireY3 += YScale2;
            fireX4 -= XScale1;
            fireY4 += YScale1;

            // Cycle Bottom to Left & back
            if (fireX1 < -1000 || fireX1 > 1000)
                XScale1 *= -1;
            if (fireY1 < -1000 || fireY1 > 1000)
                YScale1 *= -1;
            if (fireX2 < -1000 || fireX2 > 1000)
                XScale2 *= -1;
            if (fireY2 < -1000 || fireY2 > 1000)
                YScale2 *= -1;

            rateCounter += fireRate;
        }
        if (rateCounter != 0) {
            --rateCounter;
        }

        if (rateCounter < 0) {
            rateCounter = 0;
        }
    }

    private void updateBounds() {
        float x = getX() + (texture.getWidth() / 2) - bounds.getWidth() / 2;
        float y = getY() + (texture.getHeight() / 2) - bounds.getHeight() / 2;

        bounds.setPosition(x, y);
    }

    private void checkCollision() {
        for (Actor actor : getStage().getActors()) {
            if (Projectile.class.isInstance(actor)) {
                if (bounds.overlaps(((Projectile)actor).getBounds())) {
                    // Collided with projectile, check name to see if friendly or enemy
                    if (actor.getName().equals("Muskrat Missile"))
                        continue;
                    else if (actor.getName().equals("Player"))
                    {
                        Gdx.app.log("Collided with", actor.getName());
                        health -= ((Projectile) actor).getDamage();
                        ((Projectile) actor).destroy();
                        System.out.println("Health: " + health);
                        // TODO: Damage Sound?
                    }
                }
            }

            if (Player.class.isInstance(actor)) {
                if (bounds.overlaps(((Player)actor).getBounds())) {
                    Gdx.app.log("Collided with Player", actor.getName());
                    // TODO: Take/Deal Damage on collision with Player?
                }
            }
        }

        // Check side checks
        checkRight = (getX() + 30 < 520);
        checkLeft = (getX() - 20 > 20);
        checkBottom = (getY() - 20 > 20);
        checkTop = (getY() + 30 < getStage().getViewport().getScreenHeight() - 20);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
