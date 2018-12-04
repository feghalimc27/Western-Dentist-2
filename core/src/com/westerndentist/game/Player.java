package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

/**
 * Player class
 */
public class Player extends Actor {

    private float score;

    private Texture texture = new Texture("Images/tempMerrySeoul.png");
    private Vector2 movement = new Vector2(0, 0);
    private Rectangle bounds = new Rectangle();
    private Rectangle powerBounds = new Rectangle();
    private Sound hitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/playerhit.mp3"));

    private int health = 5;
    private float speed = 200;
    private float fireRate = 600;
    private float iframes = 0;
    private int rateCounter = 0;
    private float power = 0;
    private WesternDentist game;
    private Aura aura;

    private boolean checkLeft = true, checkRight = true, checkTop = true, checkBottom = true;

    /**
     * Default Constructor
     */
    Player() {
        setPosition(0, 0);
        setBounds(0, 0, texture.getWidth() / 2, texture.getWidth() / 2);
        setName("Player");
    }

    /**
     * Constructor with position (unused)
     * @param position initial position
     */
    Player(Vector2 position) {
        setPosition(position.x, position.y);
        setBounds(position.x, position.y, texture.getWidth() / 2, texture.getWidth() / 2);
        setName("Player");
    }

    /**
     * Player constructor
     * @param x initial x
     * @param y initial y
     * @param game instance of the game
     */
    Player(float x, float y, WesternDentist game) {
        setPosition(x, y, Align.center);
        bounds.set(x, y, texture.getWidth() / 4, texture.getWidth() / 4);
        bounds.setCenter(x + texture.getWidth() / 4, y - texture.getHeight() / 4);
        powerBounds.set(x, y, texture.getWidth(), texture.getHeight());
        setName("Player");
        this.game = game;
    }

    /**
     * Draws texture to frame
     * @param batch
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor());
        batch.draw(texture, getX(), getY());
        batch.setColor(1, 1, 1, 1);
    }

    /**
     * Handles drawing hitbox
     * @param shapes
     */
    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        super.drawDebug(shapes);
    }

    /**
     * Updates every time step
     * @param delta time since the last frame in seconds
     */
    @Override
    public void act(float delta) {
        move();
        applyMovement(delta);
        fire(delta);
        decayIframes(delta);
        increaseScore(delta);

        updateBounds();
        checkCollision();
        if (health <= 0) {
            health = 5;
            SequenceAction sequence = Actions.sequence();
            sequence.addAction(Actions.delay(3f));
            sequence.addAction(Actions.fadeOut(1));
            sequence.addAction(Actions.run(new Runnable() {
                @Override
                public void run() {
                    if (game.currentStage instanceof  Level4) {
                        ((Level4)game.currentStage).restart();
                    }
                    game.restartStage();
                }
            }));
            Image background = new Image(new Texture("images/background.png"));
            Table table = new Table();
            table.setSize(560, 600);
            table.center();
            Label youDied = new Label("Failure.", game.labelStyle);
            Label restarting = new Label("Restarting...", game.labelStyle);
            table.add(youDied);
            table.row();
            table.add(restarting);
            game.currentStage.addActor(background);
            game.currentStage.addActor(table);
            game.currentStage.addAction(sequence);
            remove();
        }
    }

    /**
     * Remove player and aura from stage
     * @return
     */
    @Override
    public boolean remove() {
        aura.remove();
        return super.remove();
    }

    /**
     * Add aura to stage
     */
    public void addAura() {
        aura = new Aura(this);
        getStage().addActor(aura);
    }

    /**
     * Increase score over time
     * @param delta the time since the last frame in seconds
     */
    private void increaseScore(float delta) {
        score += 100 * delta;
    }

    /**
     * Modifies power decay when firing
     * @param delta time since the last frame in seconds
     */
    private void modPower(float delta) {
        if (power > 1000) {
            power = 1000;
        }

        fireRate = 600 - power;
        if (fireRate < 0) {
            fireRate = 0;
        }

        if (power > 0) {
            power -= (power / 35) * delta;
        }
        if (power < 0) {
            power = 0;
        }
    }

    /**
     * Decay intangibility frames
     * @param delta time since the last frame in seconds
     */
    private void decayIframes(float delta) {
        if (iframes > 0) {
            iframes -= 20 * delta;
            setColor(0.5f, 0.5f, 0.5f, 0.3f + 0.9f * Math.abs(MathUtils.cos( iframes / 50)));
        }
        else if (iframes < 0) {
            iframes = 0;
            setColor(1, 1, 1, 1);
        }
    }

    /**
     * Handles player movement
     */
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

    /**
     * Applies movement
     * @param delta time since the last frame in seconds
     */
    private void applyMovement(float delta) {
        movement.x *= delta;
        movement.y *= delta;
        moveBy(movement.x, movement.y);
        setPosition(getX() + movement.x, getY() + movement.y);

        movement = new Vector2(0, 0);
    }

    /**
     * Handles firing
     * @param delta time since the last frame in seconds
     */
    private void fire(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (rateCounter == 0) {
                if (power <= 10) {
                    getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerProjectile.png"), 800, getX(), getY() + 10, "Player"));
                }
                else if (power > 10 && power <= 50) {
                    getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerProjectile.png"), 800, getX() + 10, getY() + 10 - 2, "Player"));
                    getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerProjectile.png"), 800, getX(), getY() + 10, "Player"));
                    getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerProjectile.png"), 800, getX() - 10, getY() + 10 - 2, "Player"));
                }
                else if (power > 50 && power <= 120) {
                    getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerProjectile.png"), 800, getX() + 10, getY() + 10 - 2, "Player"));
                    getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerProjectile.png"), 800, getX(), getY() + 10, "Player"));
                    getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerProjectile.png"), 800, getX() - 10, getY() + 10 - 2, "Player"));
                    getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerProjectile.png"), 800, getX() + 20, getY() + 10 - 4, "Player"));
                    getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerProjectile.png"), 800, getX() - 20, getY() + 10 - 4, "Player"));
                }
                else if (power > 120 && power <= 240) {
                    getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerProjectile.png"), 800, getX() + 10, getY() + 10 - 2, "Player"));
                    getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerProjectile.png"), 800, getX(), getY() + 10, "Player"));
                    getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerProjectile.png"), 800, getX() - 10, getY() + 10 - 2, "Player"));
                    getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerProjectile.png"), 800, getX() - 20, getY() + 10 - 4, "Player"));
                    getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerProjectile.png"), 800, getX() - 30, getY() + 10 - 6, "Player"));
                    getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerProjectile.png"), 800, getX() + 20, getY() + 10 - 4, "Player"));
                    getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerProjectile.png"), 800, getX() + 30, getY() + 10 - 6, "Player"));

                }
                else {
                    getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerProjectile.png"), 800, getX() + 10, getY() + 10 - 2, "Player", 10 + power / 100));
                    getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerProjectile.png"), 800, getX(), getY() + 10, "Player", 10 + power % 100));
                    getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerProjectile.png"), 800, getX() - 10, getY() + 10 - 2, "Player", 10 + power / 100));
                    getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerProjectile.png"), 800, getX() + 20, getY() + 10 - 4, "Player", 10 + power / 100));
                    getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerProjectile.png"), 800, getX() - 20, getY() + 10 - 4, "Player", 10 + power / 100));
                    getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerProjectile.png"), 800, getX() - 30, getY() + 10 - 6, "Player", 10 + power / 100));
                    getStage().addActor(new Projectile(new Texture("images/WesternDentist_PlayerProjectile.png"), 800, getX() + 30, getY() + 10 - 6, "Player", 10 + power / 100));
                    getStage().addActor(new NonVerticalProjectile(new Texture("images/WesternDentist_PlayerBurst.png"), 800, getX() + 40, getY() + 10 - 8, "Player", -1, 1, false, false));
                    getStage().addActor(new NonVerticalProjectile(new Texture("images/WesternDentist_PlayerBurst.png"), 800, getX() - 40, getY() + 10 - 8, "Player", 1, 1, false, false));
                }
                rateCounter += fireRate * delta * 10;
                modPower(delta);
            }
        }

        if (rateCounter != 0) {
            rateCounter += 1000 * delta * 10;
        }

        if (rateCounter >= fireRate) {
            rateCounter = 0;
        }
    }

    /**
     * Update bounding box
     */
    private void updateBounds() {
        float x = getX() + (texture.getWidth() / 2) - bounds.getWidth() / 2;
        float y = getY() + (texture.getHeight() / 2) - bounds.getHeight() / 2;

        bounds.setPosition(x, y);
        powerBounds.setPosition(x, y);
    }

    /**
     * Handle collision detection
     */
    private void checkCollision() {
        for (Actor actor : getStage().getActors()) {
            if (Projectile.class.isInstance(actor) && iframes == 0) {
                if (bounds.overlaps(((Projectile) actor).getBounds())) {
                    if (actor.getName() == "Enemy" || actor.getName() == "Muskrat Missile") {
                        hitSound.play(WesternDentist.soundEffectVolumeActual);
                        health -= 1;
                        ((Projectile) actor).destroy();
                        iframes = 100;
                    }
                }
            }

            if (Enemy.class.isInstance(actor) && iframes == 0) {
                if (bounds.overlaps(((Enemy) actor).getBounds())) {
                    hitSound.play(WesternDentist.soundEffectVolumeActual);
                    health -= 1;
                    iframes = 100;
                }
            }

            if (Powerup.class.isInstance(actor)) {
                if (powerBounds.overlaps(((Powerup) actor).getBounds())) {
                    if (PowerPowerup.class.isInstance(actor)) {
                        float plus = ((PowerPowerup) actor).destroy();
                        power += plus;
                    }
                    else if (HealthPowerup.class.isInstance(actor)) {
                        if (health < 5) {
                            health += ((HealthPowerup) actor).destroy();
                        }
                    }
                }
            }
        }

        // Check side checks
        checkRight = (getX() + 30 < 520);
        checkLeft = (getX() - 20 > 20);
        checkBottom = (getY() - 20 > 20);
        checkTop = (getY() + 30 < 580);
    }

    /**
     * Return bounding box for collision detection
     * @return Rectangle bounds
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Add to score
     * @param score
     */
    public void addScore(float score) {
        this.score += score;
    }

    /**
     * Set health
     * @param health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Set score
     * @param score
     */
    public void setScore(float score) {
        this.score = score;
    }

    /**
     * Set power
     * @param power
     */
    public void setPower(float power) {
        this.power = power;
    }

    /**
     * Get score
     * @return
     */
    public int getScore() {
        return (int)score;
    }

    /**
     * Get power
     * @return
     */
    public int getPower() {
        return (int)power;
    }

    /**
     * Get health
     * @return
     */
    public int getHealth() {
        return health;
    }
}
