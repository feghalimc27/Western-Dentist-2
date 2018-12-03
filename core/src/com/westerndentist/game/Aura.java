package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Aura class for the player's overshield
 */
public class Aura extends Actor {

    private Texture texture = new Texture("Images/WesternDentist_Aura.png");
    private Sound auraActivated = Gdx.audio.newSound(Gdx.files.internal("sounds/auraActivated.mp3"));
    private Sound auraDeactivated = Gdx.audio.newSound(Gdx.files.internal("sounds/auraDeactivated.mp3"));
    private float cooldown = 0;
    private float time = 0;
    private float degrees = 20;
    private boolean rising = true;
    private boolean active = false;
    private Player player;
    private WesternDentist game;
    private Rectangle bounds;

    /**
     * Constructor
     * @param player Handles a reference to the player
     * @param game Handles a reference to the game
     */
    Aura(Player player, WesternDentist game) {
        this.game = game;
        setName("Aura");
        setX(player.getX());
        setY(player.getY());
        bounds = new Rectangle(getX(), getY(), texture.getWidth(), texture.getHeight());
        this.player = player;
    }

    /**
     * Updated on each time step
     * @param delta the time since the last frame in seconds
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        if (active) {
            block();
        }
        if (!active && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && cooldown == 0) {
            auraActivated.play(game.soundEffectVolumeActual);
            active = true;
            time = 5;
        }

        if (time > 0 && active) {
            time -= delta;
        }

        if (time < 0) {
            auraDeactivated.play(game.soundEffectVolumeActual);
            time = 0;
            cooldown = 10;
            active = false;
        }

        if (degrees > 10 && rising) {
            degrees += 20 * delta;
            if (degrees >= 160) {
                rising = false;
            }
        }
        else if (degrees < 170 && !rising) {
            degrees -= 60 * delta;
            if (degrees <= 20) {
                rising = true;
            }
        }

        if (cooldown > 0) {
            cooldown -= delta;
        }
        else if (cooldown < 0) {
            cooldown = 0;
        }

        setX(player.getX() - texture.getWidth() / 2.6f);
        setY(player.getY() - texture.getHeight() / 3);
        bounds.set(getX(), getY(), texture.getWidth(), texture.getHeight());
    }

    /**
     * Draws objects to the screen
     * @param batch SpriteBatch to draw objects
     * @param parentAlpha Alpha for the parent (unused)
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (active) {
            batch.setColor(0, 1, 1, 1);
        }
        else if (!active && cooldown > 0) {
            batch.setColor(MathUtils.sinDeg(degrees), 0, 0,  MathUtils.sinDeg(degrees));
        }
        else {
            batch.setColor(Color.WHITE);
        }
        batch.draw(texture, getX(), getY());
        batch.setColor(Color.WHITE);
    }

    /**
     * Handles blocking projectiles when aura is active
     */
    private void block() {
        try {
            for (Actor actor: getStage().getActors()) {
                if (Projectile.class.isInstance(actor) && !(actor.getName().equals("Player"))) {
                    if (((Projectile)actor).getBounds().overlaps(bounds)) {
                        actor.remove();
                    }
                }
            }
        }
        catch (NullPointerException e) {
            Gdx.app.log("Aura", "An actor was removed before it could be checked");
        }
    }
}
