package com.westerndentist.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

public class PositionProjectile extends Projectile {

    private float xFactor, yFactor;
    private float delay;
    private float timer = 0;

    private Vector2 playerPos;

    PositionProjectile(Texture texture, float speed, String tag, float x, float y, Vector2 playerPos, float delay) {
        super(texture, speed, x, y,tag);

        this.delay = delay;
        this.playerPos = playerPos;

        float hyp = Vector2.dst(x, y, playerPos.x, playerPos.y);

        xFactor = x - playerPos.x;
        yFactor = y - playerPos.y;

        xFactor -= (int)xFactor;
        yFactor -= (int)yFactor;

        xFactor *= -1;
        yFactor *= -1;

        setPosition(x, y);
    }

    @Override
    public void act(float delta) {
        if (timer < delay) {
            timer += 10 * delta;
        }
        else {
            moveBy(xFactor * delta * speed, yFactor * delta * speed);
        }

        updateBounds();
    }
}
