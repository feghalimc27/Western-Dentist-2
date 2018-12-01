package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class BossTPoseQueen extends Boss {

    private float phase1damage = 1500, phase2damage = 3000,
            phase3damage = 6000, phase4damage = 12000;

    private Texture texture = new Texture("Images/L2_Boss_T-Pose_Queen.png");

    private int phase = 1;
    private float spinFactor = 1;


    private float proj1xFactor = 1000, proj1yFactor = 0;

    private float phase2Counter = 0;
    private float phase3Counter = 0;
    private float phase3Cooldown = 5000;

    private float rateCounter1 = 0;
    private float fireRate1 = 10;

    private double rotate = -1;
    private double rotate2 = 1;

    private float speed = -50;
    private float basicSpawnX = 0;
    private float fireRate = 10;
    private int rateCounter = 0;
    private int fireX1 = 0;
    private int fireY1 = 1000;
    private int XScale1 = 100;
    private int YScale1 = 100;
    private int fireX2 = 0;
    private int fireY2 = -1000;
    private int XScale2 = 100;
    private int YScale2 = 100;
    private int fireX3 = 0;
    private int fireY3 = -1000;
    private int fireX4 = 0;
    private int fireY4 = 1000;

    private Sequencer sequencer;

    private float damage = 0;

    private boolean spawned = false;

    private float spin = 1;

    BossTPoseQueen(Sequencer Sequencer) {
        super();
        this.sequencer = Sequencer;
        bounds = new Rectangle(getX(), getY(), texture.getWidth(), texture.getHeight());
    }

    @Override
    public void act(float delta) {
        if (!spawned) {
            spawned = true;
            // Play sound, do stuff
            Gdx.app.log("Boss 2", "Spawned");
        }
        super.act(delta);
        takeDamageFromProjectile();
        //changePhase();
        phase();

        Gdx.app.log("Boss Damage", "" + damage);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }


    private void phase() {
        if (this.damage < this.phase1damage) {
            phase1();
            Gdx.app.log("Boss2", "phase1");
        } else if (this.damage > this.phase1damage && this.damage < this.phase2damage) {
            phase1();
            phase2();
            Gdx.app.log("Boss2", "phase2");

        } else if (this.damage > this.phase2damage && this.damage < this.phase3damage) {
            phase1();
            Gdx.app.log("Boss2", "phase3");

//            phase2();
//            phase3();
        }

        else if(this.damage > this.phase4damage){

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
                            damage += ((Projectile) actor).getDamage();
                            ((Projectile) actor).destroy();
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            Gdx.app.log("Enemy: ", "Something broke but I'm just gonna ignore it lol");
        }
    }

    private void phase1() {
        try {
            if (rateCounter == 0) {
                // TODO: Play Firing Sound?
                getStage().addActor(new LinearProjectile(new Texture("images/WesternDentist_BossBurst.png"), (float) -300, (float) (getX() + texture.getWidth() / 2), (float) (getY() + texture.getHeight() / 2 - 10), "Enemy", (float) fireX1 / (float) 1000, (float) fireY1 / (float) 1000, false));
                getStage().addActor(new LinearProjectile(new Texture("images/WesternDentist_BossBurst.png"), (float) -300, (float) (getX() + texture.getWidth() / 2), (float) (getY() + texture.getHeight() / 2 - 10), "Enemy", (float) fireX2 / (float) 1000, (float) fireY2 / (float) 1000, false));
                getStage().addActor(new LinearProjectile(new Texture("images/WesternDentist_BossBurst.png"), (float) -300, (float) (getX() + texture.getWidth() / 2), (float) (getY() + texture.getHeight() / 2 - 10), "Enemy", (float) fireX3 / (float) 1000, (float) fireY3 / (float) 1000, false));
                getStage().addActor(new LinearProjectile(new Texture("images/WesternDentist_BossBurst.png"), (float) -300, (float) (getX() + texture.getWidth() / 2), (float) (getY() + texture.getHeight() / 2 - 10), "Enemy", (float) fireX4 / (float) 1000, (float) fireY4 / (float) 1000, false));
                fireX1 += XScale1;
                fireY1 += YScale1;
                fireX2 += XScale2;
                fireY2 += YScale2;
                fireX3 -= XScale2;
                fireY3 += YScale2;
                fireX4 -= XScale1;
                fireY4 += YScale1;

                // Cycle Bottom to Left & back
                if (fireX1 < -1000 || fireX1 > 1000)
                    XScale1 *= -1;
                if (fireY1 < -1000 || fireY1 > 1000)
                    YScale1 *= -1;
                if (fireX2 < -1000 || fireX2 > 1000)
                    XScale2 *= -1;
                if (fireY2 < -1000 || fireY2 > 1000)
                    YScale2 *= -1;

                rateCounter += fireRate;
            }
            if (rateCounter != 0) {
                --rateCounter;
            }

            if (rateCounter < 0) {
                rateCounter = 0;
            }
        } catch (NullPointerException e) {

        }
    }

    private void phase2() {
        if(basicSpawnX < 3000)
            if(basicSpawnX % 20 == 0) {
                getStage().addActor(new BasicEnemy(new Texture("Images/T-Pose_Luigi.png"), 100, 10, 100, new Vector2(basicSpawnX, 1000)));
                getStage().addActor(new LinearProjectile(new Texture("images/WesternDentist_BossBurst.png"), (float) -300, basicSpawnX, 1000, "Enemy", 0, 1, false));
            }
        basicSpawnX += 1;
    }
}
