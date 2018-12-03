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

public abstract class Enemy extends Actor {

    protected Texture texture;

    protected float speed;
    protected float fireRate;
    protected float health;
    protected int scoreMultiplier;

    private boolean checked = false;

    protected Rectangle bounds = new Rectangle();

    private RandomXS128 rng = new RandomXS128();

    Enemy() {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

    @Override
    public void act(float delta) {
        if (!checked) {
            checked = true;
            scoreMultiplier = (int)health % 10;
        }
        super.act(delta);
        updateBounds();
        takeDamageFromProjectile();
        killOnDead();
    }

    private void killOnDead() {
        if (health <= 0) {
            int powerups = (int)(6 * rng.nextFloat()) + 1;
            int health = (int)(12 * rng.nextFloat());
            if (health == 3) {
                getStage().addActor(new HealthPowerup(1000, 1, new Vector2(getX(), getY())));
            }

            for (int i = 0; i < powerups; ++i) {
                getStage().addActor(new PowerPowerup(1000, 10, new Vector2(getX() + i, getY() + i)));
            }

            for (Actor actor: getStage().getActors()) {
                if (Player.class.isInstance(actor)) {
                    ((Player)actor).addScore(7000 * scoreMultiplier);
                    break;
                }
            }

            remove();
        }
    }

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

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        super.drawDebug(shapes);
    }

    private void updateBounds() {
        bounds.setPosition(getX(), getY());
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
