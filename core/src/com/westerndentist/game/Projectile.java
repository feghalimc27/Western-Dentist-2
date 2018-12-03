package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Base projectile class
 */
public class Projectile extends Actor{

    private Texture texture;
    protected float speed = 0;
    private float damage = 10;
    private Rectangle bounds = new Rectangle();

    /**
     * Constructor
     * @param texture texture
     * @param initialSpeed initial speed
     * @param x initial x
     * @param y initial y
     * @param tag actor who spawned
     */
    Projectile(Texture texture, float initialSpeed, float x, float y, String tag) {
        this.texture = texture;
        speed = initialSpeed;
        setPosition(x, y);
        setName(tag);
        bounds.set(x + texture.getWidth() / 8, y + texture.getWidth() / 8, texture.getWidth() / 4, texture.getHeight() / 4);
    }

    /**
     * Constructor
     * @param texture texture
     * @param initialSpeed initial speed
     * @param x initial x
     * @param y initial y
     * @param tag actor who spawned
     * @param damage damage
     */
    Projectile(Texture texture, float initialSpeed, float x, float y, String tag, float damage) {
        this.texture = texture;
        speed = initialSpeed;
        setPosition(x, y);
        setName(tag);
        this.damage = damage;
        bounds.set(x + texture.getWidth() / 8, y + texture.getWidth() / 8, texture.getWidth() / 4, texture.getHeight() / 4);
    }


    /**
     * Draws the projectile
     * @param batch SpriteBatch to draw sprite
     * @param parentAlpha alpha of the parent (unused)
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (getName().equals("Player") && damage > 10) {
            setColor((255 - damage)/255, (255 - damage)/255, 1, 0.8f);
        }
        else if (damage == 10 && getName().equals("Player")) {
            setColor(1, 1, 1, 0.8f);
        }
        batch.setColor(getColor());
        batch.draw(texture, getX(), getY(), texture.getWidth() / 2, texture.getHeight() / 2);
    }

    /**
     * Updates every time step
     * @param delta time since the last frame in seconds
     */
    @Override
    public void act(float delta) {
        super.act(delta);

        if (!(this instanceof PositionProjectile || this instanceof NonVerticalProjectile || this instanceof LinearProjectile)) {
            moveBy(0, (speed * delta));
        }
        updateBounds();

        if (getY() > 1000 || getY() < -100) {
            addAction(Actions.removeActor());
        }
        if (getX() > 800 || getX() < -200) {
            addAction(Actions.removeActor());
        }
    }

    /**
     * Draw projectile hitbox on debug = true
     * @param shapes
     */
    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        super.drawDebug(shapes);
    }

    /**
     * get the damage value to inflict
     * @return float damage
     */
    public float getDamage() {
        return damage;
    }

    /**
     * Destroy projectile
     */
    public void destroy() {
        remove();
    }

    /**
     * Update bounds
     */
    protected void updateBounds() {
        bounds.setPosition(getX() + texture.getWidth() / 8, getY()+ texture.getWidth() / 8);
    }

    /**
     * Get bounds for collision
     * @return Rectangle bounds
     */
    public Rectangle getBounds() {
        return bounds;
    }


}
