package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Spray enemy for level 4
 */
public class SprayEnemy extends Enemy {

    private float rateCounter = 0;
    private float moveCooldown = 0;

    private boolean canMove = true;

    private Vector2 moveToPosition;
    private Vector2 moveFromPosition;

    private static float maxMoveX = 300;
    private static float maxMoveY = 300;

    private static RandomXS128 generator = new RandomXS128();

    /**
     * Constructor
     * @param texture texture
     * @param health initial health
     * @param fireRate rate of fire
     */
    SprayEnemy(Texture texture, float health, float fireRate) {
        super();

        this.texture = texture;
        this.health = health;
        this.fireRate = fireRate;
        bounds.set(getX(), getY(), texture.getWidth(), texture.getHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);

        canMove = false;
        moveCooldown = 70;

        generator = new RandomXS128();
    }

    /**
     * Updates on each time step
     * @param delta the time since the last frame in seconds
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        fireSpray(delta);
        move(delta);
    }

    /**
     * Spray fire
     * @param delta time since the last frame in seconds
     */
    private void fireSpray(float delta) {
        if (rateCounter == 0) {
            try {
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 60, getX() - getWidth(), getY() - getHeight(), "Enemy", 0, 1, true, true));
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 60, getX() - getWidth(), getY() - getHeight(), "Enemy", 1, 0, true, true));
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 60, getX() - getWidth(), getY() - getHeight(), "Enemy", -1, 0, true, true));
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 60, getX() - getWidth(), getY() - getHeight(), "Enemy", 0, -1, true, true));
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 60, getX() - getWidth(), getY() - getHeight(), "Enemy", 1, 1, true, true));
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 60, getX() - getWidth(), getY() - getHeight(), "Enemy", -1, 1, true, true));
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 60, getX() - getWidth(), getY() - getHeight(), "Enemy", -1, -1, true, true));
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 60, getX() - getWidth(), getY() - getHeight(), "Enemy", 1, -1, true, true));
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 60, getX() - getWidth(), getY() - getHeight(), "Enemy", 0, 1, true, false));
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 60, getX() - getWidth(), getY() - getHeight(), "Enemy", 1, 0, true, false));
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 60, getX() - getWidth(), getY() - getHeight(), "Enemy", -1, 0, true, false));
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 60, getX() - getWidth(), getY() - getHeight(), "Enemy", 0, -1, true, false));
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 60, getX() - getWidth(), getY() - getHeight(), "Enemy", 1, 1, true, false));
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 60, getX() - getWidth(), getY() - getHeight(), "Enemy", -1, 1, true, false));
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 60, getX() - getWidth(), getY() - getHeight(), "Enemy", -1, -1, true, false));
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 60, getX() - getWidth(), getY() - getHeight(), "Enemy", 1, -1, true, false));
            }
            catch (NullPointerException e) {

            }


            rateCounter = fireRate;
        }
        else {
            rateCounter -= 1000 * delta;
        }

        if (rateCounter <= 0) {
            rateCounter = 0;
        }
    }

    /**
     * Move based on XOR Shift Random
     * @param delta time since the last frame in seconds
     */
    private void move(float delta) {
        if (moveCooldown == 0 && canMove) {

            float x = maxMoveX * generator.nextFloat();
            float y = maxMoveY * generator.nextFloat();

            int diceX = (int)(generator.nextFloat() * 24);
            int diceY = (int)(generator.nextFloat() * 24);

            if (diceX % 2 == 0) {
                x *= -1;
            }
            if (diceY % 2 == 0) {
                y *= -1;
            }

            moveFromPosition = new Vector2(getX(), getY());
            moveToPosition = new Vector2(x, y);
            moveCooldown = 70;
            canMove = false;

            Vector2 finalPos = new Vector2(moveFromPosition.x + moveToPosition.x, moveFromPosition.y + moveToPosition.y);

            if (finalPos.x < 20) {
                finalPos.x = 20;
            }

            if (finalPos.x > 520) {
                finalPos.x = 520;
            }

            if (finalPos.y > 580 - 20) {
                finalPos.y = 580 - 20;
            }

            if (finalPos.y < 20) {
                finalPos.y = 20;
            }

            addAction(Actions.moveTo(finalPos.x, finalPos.y, 10 * generator.nextFloat()));
        }

        moveCooldown -= delta * 10;

        if (moveCooldown <= 0) {
            moveCooldown = 0;
            canMove = true;
        }
    }
}
