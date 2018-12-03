package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Base enemy abstract class
 */
public abstract class Enemy extends Actor {

    protected Texture texture;

    protected float speed;
    protected float fireRate;
    protected float health;
    protected int scoreMultiplier;

    private boolean checked = false;

    protected Rectangle bounds = new Rectangle();

    private RandomXS128 rng = new RandomXS128();

    /**
     * Constructor
     */
    Enemy() {

    }

    /**
     * Draws object to the screen
     * @param batch SpriteBatch to draw the texture
     * @param parentAlpha The alpha of the objects parent (Unused)
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

    /**
     * Updates every frame
     * @param delta the time since the last frame in secondss
     */
    @Override
    public void act(float delta) {
        if (!checked) {
            checked = true;
            scoreMultiplier = (int)health / 10;
        }
        super.act(delta);
        updateBounds();
        takeDamageFromProjectile();
        killOnDead();
    }

    /**
     * Handles killing the enemies on health < 0
     */
    private void killOnDead() {
        if (health <= 0) {
            int powerups = (int)(6 * rng.nextFloat()) + 1;
            int health = (int)(12 * rng.nextFloat());
            if (health == 3) {
                getStage().addActor(new HealthPowerup(1000, 1, new Vector2(getX(), getY())));
            }

            for (int i = 0; i < powerups; ++i) {
                getStage().addActor(new PowerPowerup(1000, 1, new Vector2(getX() + i, getY() + i)));
            }

            for (Actor actor: getStage().getActors()) {
                if (Player.class.isInstance(actor)) {
                    ((Player)actor).addScore(1000 * scoreMultiplier);
                    break;
                }
            }

            remove();
        }
    }

    /**
     * Handles taking damage from projectiles
     */
    private void takeDamageFromProjectile() {
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
            Gdx.app.log("Enemy: ", "Size of the actor container changed.");
        }
    }

    /**
     * Override debug draw to draw enemy bounding boxes when in debug mode
     * @param shapes shapes
     */
    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        super.drawDebug(shapes);
    }

    /**
     * Updates the bounding box
     */
    private void updateBounds() {
        bounds.setPosition(getX(), getY());
    }

    /**
     * Returns the bounding box for collision handling
     * @return Rectangle bounds
     */
    public Rectangle getBounds() {
        return bounds;
    }
}
