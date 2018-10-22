package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Player extends Actor {

    private Texture texture = new Texture("Images/tempMerrySeoul.png");
    private Vector2 movement = new Vector2(0, 0);

    private float speed = 200;
    private float fireRate = 600;
    private int rateCounter = 0;

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
        setPosition(x, y);
        setBounds(x, y, texture.getWidth() / 2, texture.getWidth() / 2);
        setName("Player");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

    @Override
    public void act(float delta) {
        move();
        applyMovement(delta);
        fire(delta);
    }

    private void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            movement.y += speed;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            movement.y -= speed;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            movement.x -= speed;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
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
                getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerBurst.png"), 800, getX() - texture.getWidth() / 2, getY()));
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
}
