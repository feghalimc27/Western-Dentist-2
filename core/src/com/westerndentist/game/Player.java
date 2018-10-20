package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {

    private Vector2 movement = new Vector2(0, 0);

    Player() {
        super();
        create();
    }

    Player(Texture texture, Vector2 position) {
        super(texture, position);
        create();
    }

    @Override
    public void create() {
        speed = 4;
        health = 100;

        super.create();
    }

    @Override
    public void update() {
        move();
        applyMovement();
        super.update();
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }

    @Override
    public void dispose() {
        super.dispose();
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

    private void applyMovement() {
        position.x += movement.x;
        position.y += movement.y;
        movement = new Vector2(0, 0);
    }
}
