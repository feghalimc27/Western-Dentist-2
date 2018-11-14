package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Projectile extends Actor{

    private Texture texture;
    protected float speed = 0;
    private float damage = 10;
    private Rectangle bounds = new Rectangle();

    Projectile(Texture texture, float initialSpeed, float x, float y, String tag) {
        this.texture = texture;
        speed = initialSpeed;
        setPosition(x, y);
        setName(tag);
        bounds.set(x, y, texture.getWidth() / 2, texture.getHeight() / 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), texture.getWidth() / 2, texture.getHeight() / 2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        moveBy(0,  (speed * delta));
        updateBounds();

        if (getY() > 1000 || getY() < -100) {
            remove();
        }
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        super.drawDebug(shapes);
    }

    public float getDamage() {
        return damage;
    }

    public void destroy() {
        remove();
    }

    protected void updateBounds() {
        bounds.setPosition(getX(), getY());
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
