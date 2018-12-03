package com.westerndentist.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Timer;
import java.util.TimerTask;

public class Boss4Phase4 extends Actor {

    private RandomXS128 rng = new RandomXS128();

    private Texture texture1 = new Texture("Images/phase4overlay1.png");
    private Texture texture2 = new Texture("Images/phase4overlay2.png");

    private boolean flipped = false;
    private boolean firing = false;
    private boolean fired = false;
    private boolean dead = false;
    private boolean fireTimer = false;

    private float angle = 0;

    private float x1 = 480, y1 = 580 - 68, x2 = 0, y2 = 580 - 218, x3 = 480, y3 = 580 - 445;
    private float fx1 = 0, fy1 = 580 - 68, fx2 = 480, fy2 = 580 - 218, fx3 = 0, fy3 = 580 - 445;

    Boss4Phase4() {
        flipped = (12 * rng.nextFloat()) > 6;
        setX(20);
        setY(20);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        angle += 500 * delta;
        if (!fired) {
            fired = true;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    firing = true;
                }
                }, 3000);
        }
        if (firing) {
            if (!flipped) {
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurstL4P4.png"), 150, getX() + x1, y1, "Enemy", -4, -1, false, false));
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurstL4P4.png"), 150, getX() + x2, y2, "Enemy", 2.4f, -1, false, false));
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurstL4P4.png"), 100, getX() + x3, y3, "Enemy", -4, -1, false, false));
            }
            else {
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurstL4P4.png"), 150, getX() + fx1, fy1, "Enemy", 4, -1, false, false));
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurstL4P4.png"), 150, getX() + fx2, fy2, "Enemy", -2.4f, -1, false, false));
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurstL4P4.png"), 150, getX() + fx3, fy3, "Enemy", 4, -1, false, false));
            }
            if (!fireTimer) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        dead = true;
                    }
                }, 3500);
                fireTimer = true;
            }
        }
        if (dead) {
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(1, 1, 1, MathUtils.sinDeg(angle));
        if (flipped && !firing) {
            batch.draw(texture2, getX(), getY());
        }
        else if (!flipped && ! firing) {
            batch.draw(texture1, getX(), getY());
        }
        batch.setColor(Color.WHITE);
    }
}
