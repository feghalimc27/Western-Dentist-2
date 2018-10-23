package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

import java.awt.*;

public class Player extends Actor {

    private Texture texture = new Texture("Images/tempMerrySeoul.png");
    private Vector2 movement = new Vector2(0, 0);
    private Rectangle bounds = new Rectangle();

    private float speed = 200;
    private float fireRate = 600;
    private int rateCounter = 0;

    private boolean checkLeft = true, checkRight = true, checkTop = true, checkBottom = true;

    Player() {
        setPosition(0, 0);
        setBounds(0, 0, texture.getWidth() / 2, texture.getWidth() / 2);
        setName("Player");
    }

    Player(Vector2 position) {
        setPosition(position.x, position.y);
        setBounds(position.x, position.y, texture.getWidth() / 2, texture.getWidth() / 2);
        setName("Player");
    }

    Player(float x, float y) {
        setPosition(x, y, Align.center);
        bounds.set(x, y, texture.getWidth() / 4, texture.getWidth() / 4);
        bounds.setCenter(x + texture.getWidth() / 4, y - texture.getHeight() / 4);
        setName("Player");
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
        fire(delta);

        updateBounds();
        checkCollision();
    }

    private void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.W) && checkTop) {
            movement.y += speed;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S) && checkBottom) {
            movement.y -= speed;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) && checkLeft) {
            movement.x -= speed;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D) && checkRight) {
            movement.x += speed;
        }
    }

    private void applyMovement(float delta) {
        movement.x *= delta;
        movement.y *= delta;
        moveBy(movement.x, movement.y);
        setPosition(getX() + movement.x, getY() + movement.y);

        movement = new Vector2(0, 0);
    }

    private void fire(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (rateCounter == 0) {
                getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerBurst.png"), 800, getX(), getY(), "Player"));
                rateCounter += fireRate * delta * 10;
            }
        }

        if (rateCounter != 0) {
            rateCounter += fireRate * delta * 10;
        }

        if (rateCounter >= fireRate) {
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
                    Gdx.app.log("Collided with Projectile ", actor.getName());
                    // Collided with projectile, check name to see if friendly or enemy
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
