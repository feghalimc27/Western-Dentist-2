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

public class Player extends Actor {

    private Texture texture = new Texture("Images/tempMerrySeoul.png");
    private Vector2 movement = new Vector2(0, 0);
    private CircleShape bounds;

    private float speed;

    Player() {
        setPosition(0, 0);
        bounds = new CircleShape();
        bounds.setRadius(texture.getWidth() / 4);
    }

    Player(Vector2 position) {
        setPosition(position.x, position.y);
        bounds = new CircleShape();
        bounds.setRadius(texture.getWidth() / 4);
    }

    Player(float x, float y) {
        setPosition(x, y);
        bounds = new CircleShape();
        bounds.setRadius(texture.getWidth() / 4);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

    @Override
    public void act(float delta) {
        move();
        applyMovement(delta);
        updateBoundsPos();
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

        movement = new Vector2(0, 0);
    }

    private void updateBoundsPos() {
        bounds.setPosition(new Vector2(getX(), getY()));
    }
}
