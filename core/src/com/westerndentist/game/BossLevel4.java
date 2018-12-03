package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.Timer;
import java.util.TimerTask;

public class BossLevel4 extends Boss {

    private float phase1damage = 3000, phase2damage = 9000,
                  phase3damage = 18000, phase4damage = 36000;

    private Texture texture = new Texture("Images/l4BossSprite.png");

    private int phase = 1;

    private float proj1xFactor = 1000, proj1yFactor = 0;

    private float phase2Counter = 0;
    private float phase3Counter = 0;
    private float phase3Cooldown = 5000;

    private float rateCounter1 = 0;
    private float fireRate1 = 10;

    private float maxMoveX = 520;
    private float maxMoveY = 580;

    private float moveCooldown = 147;
    private boolean canMove = false;

    private boolean spawningPower = false;

    private Vector2 moveFromPosition;
    private Vector2 moveToPosition;

    private float damage = 0;

    private boolean spawned = false;

    private RandomXS128 rng = new RandomXS128();

    BossLevel4() {
        super();

        bounds = new Rectangle(getX(), getY(), texture.getWidth(), texture.getHeight());
        setName("BOSS4");
    }

    @Override
    public void act(float delta) {
        if (!spawned) {
            spawned = true;
            ((Level4)getStage()).playMusic();
            Gdx.app.log("Boss 4", "Spawned");
        }
        super.act(delta);
        move(delta);
        takeDamageFromProjectile();
        changePhase();
        phase(delta);
        spawnPower(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

    private void move(float delta) {
        if (moveCooldown == 0 && canMove) {

            float x = maxMoveX * rng.nextFloat();
            float y = maxMoveY * rng.nextFloat();

            int diceX = (int) (rng.nextFloat() * 24);
            int diceY = (int) (rng.nextFloat() * 24);

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

            if (finalPos.x < 20 + texture.getWidth() / 2) {
                finalPos.x = 20 + texture.getWidth() / 2;
            }

            if (finalPos.x > 520 - texture.getWidth()) {
                finalPos.x = 520 - texture.getWidth();
            }

            if (finalPos.y > maxMoveY - 20 - texture.getHeight() / 2) {
                finalPos.y = maxMoveY - 20 - texture.getHeight() / 2;
            }

            if (finalPos.y < 20 + texture.getHeight() / 2 + 300) {
                finalPos.y = 20 + texture.getHeight() / 2 + 300;
            }

            addAction(Actions.moveTo(finalPos.x, finalPos.y, 10 * rng.nextFloat()));
        }

        moveCooldown -= delta * 10;

        if (moveCooldown <= 0) {
            moveCooldown = 0;
            canMove = true;
        }
    }

    private void changePhase() {
        if (damage > phase1damage && damage < phase2damage) {
            phase = 2;
        }
        else if (damage > phase2damage && damage < phase3damage) {
            phase = 3;
        }
        else if (damage > phase3damage && damage < phase4damage) {
            phase = 4;
        }
        else if (damage > phase4damage) {
            // end level
            phase = 5;
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

    private void spawnPower(float delta) {
        if (!spawningPower) {
            spawningPower = true;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    spawningPower = false;
                }
            }, 30000);

            getStage().addActor(new PowerPowerup(5000, 10, new Vector2(getX() + 40 + texture.getWidth() / 2, getY()), true));
            getStage().addActor(new PowerPowerup(5000, 10, new Vector2(getX() + 80 + texture.getWidth() / 2, getY()), true));
            getStage().addActor(new HealthPowerup(5000, 1, new Vector2(getX() + 120 + texture.getWidth() / 2, getY()), true));
            getStage().addActor(new HealthPowerup(5000, 1, new Vector2(getX() - 120 + texture.getWidth() / 2, getY()), true));
            getStage().addActor(new PowerPowerup(5000, 10, new Vector2(getX() - 40 + texture.getWidth() / 2, getY()), true));
            getStage().addActor(new PowerPowerup(5000, 10, new Vector2(getX() - 80 + texture.getWidth() / 2, getY()), true));

        }
    }

    private void phase(float delta) {
        switch (phase) {
            case 1:
                phase1(delta);
                break;
            case 2:
                phase1(delta);
                phase2(delta);
                break;
            case 3:
                phase1(delta);
                phase2(delta);
                phase3(delta);
                break;
            case 4:
                phase1(delta);
                phase2(delta);
                phase3(delta);
                break;
            default:
                break;
        }
    }

    private void phase1(float delta) {
        float spinFactor = 1000;

        if (proj1xFactor <= 1005 && proj1xFactor > -5 && proj1yFactor >= -5) {
            proj1xFactor -= spinFactor * delta;
            proj1yFactor += spinFactor * delta;
        }
        else if (proj1xFactor <= -5 && proj1xFactor > -1005 && proj1yFactor > -5) {
            proj1xFactor -= spinFactor * delta;
            proj1yFactor -= spinFactor * delta;
        }
        else if (proj1xFactor > -1005 && proj1xFactor < -5 && proj1yFactor >= -1005) {
            proj1xFactor += spinFactor * delta;
            proj1yFactor -= spinFactor * delta;
        }
        else if (proj1xFactor >= -5 && proj1xFactor < 1005 && proj1yFactor > -1005) {
            proj1xFactor += spinFactor * delta;
            proj1yFactor += spinFactor * delta;
        }

        if (rateCounter1 == 0) {
            try {
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurstL4P1.png"), 110, getX() + texture.getWidth() / 2, getY() + texture.getHeight() / 2, "Enemy", proj1xFactor / 1000, proj1yFactor / 1000, false, false));
            } catch (NullPointerException e) {
                Gdx.app.log("Boss Level 4", "Enemy not found");
            }

            if (proj1xFactor <= 1100 && proj1xFactor > 0 && proj1yFactor >= -100) {
                proj1xFactor -= spinFactor * delta;
                proj1yFactor += spinFactor * delta;
            }
            else if (proj1xFactor <= 0 && proj1xFactor > -1000 && proj1yFactor > 0) {
                proj1xFactor -= spinFactor * delta;
                proj1yFactor -= spinFactor * delta;
            }
            else if (proj1xFactor > -1100 && proj1xFactor < 0 && proj1yFactor > -1100) {
                proj1xFactor += spinFactor * delta;
                proj1yFactor -= spinFactor * delta;
            }
            else if (proj1xFactor >= 0 && proj1xFactor < 1000 && proj1yFactor > -1100) {
                proj1xFactor += spinFactor * delta;
                proj1yFactor += spinFactor * delta;
            }

            rateCounter1 = fireRate1;
        }
        else {
            rateCounter1 -= 1000 * delta;
        }

        if (rateCounter1 <= 0) {
            rateCounter1 = 0;
        }
    }

    private void phase2(float delta) {
        if (phase2Counter < 100) {
            try {
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 130, getX() + texture.getWidth() / 2, getY() + texture.getHeight() / 2, "Enemy", 1, -2, true, false));
            } catch (NullPointerException e) {

            }
        }
        else if (phase2Counter > 100 && phase2Counter < 200) {
            try {
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 130, getX() + texture.getWidth() / 2, getY() + texture.getHeight() / 2, "Enemy", -1, -2, true, false));
            } catch (NullPointerException e) {

            }
        }
        else if (phase2Counter > 200 && phase2Counter < 300) {
            try {
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 130, getX() + texture.getWidth() / 2, getY() + texture.getHeight() / 2, "Enemy", -1, -2, true, true));
            } catch (NullPointerException e) {

            }
        }
        else if (phase2Counter > 300 && phase2Counter < 400) {
            try {
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 130, getX() + texture.getWidth() / 2, getY() + texture.getHeight() / 2, "Enemy", 1, -2, true, true));
            } catch (NullPointerException e) {

            }
        }
        else if (phase2Counter > 400) {
            phase2Counter = 0;
        }

        phase2Counter += 500 * delta;
    }

    private void phase3(float delta) {
        if (phase3Counter >= phase3Cooldown) {
            Vector2 playerPos = new Vector2();
            for (Actor actor: getStage().getActors()) {
                if (actor instanceof Player) {
                    playerPos.x = actor.getX();
                    playerPos.y = actor.getY();
                    break;
                }
            }

            for (int i = 0; i < 3; ++i) {
                getStage().addActor(new PositionProjectile(new Texture("Images/WesternDentist_BossBurstL4P3.png"), 100, "Enemy", 500 + rng.nextFloat(), 550 + rng.nextFloat(), playerPos, 30 + i));
                getStage().addActor(new PositionProjectile(new Texture("Images/WesternDentist_BossBurstL4P3.png"), 100, "Enemy", 50 + rng.nextFloat(), 550 + rng.nextFloat(), playerPos, 30 + i));
                getStage().addActor(new PositionProjectile(new Texture("Images/WesternDentist_BossBurstL4P3.png"), 100, "Enemy", 50 + rng.nextFloat(), 50 + rng.nextFloat(), playerPos, 30 + i));
                getStage().addActor(new PositionProjectile(new Texture("Images/WesternDentist_BossBurstL4P3.png"), 100, "Enemy", 500 + rng.nextFloat(), 50 + rng.nextFloat(), playerPos, 30 + i));
            }
            for (int i = 0; i < 3; ++i) {
                getStage().addActor(new PositionProjectile(new Texture("Images/WesternDentist_BossBurstL4P3.png"), 100, "Enemy", 500 + rng.nextFloat(), 550 + rng.nextFloat(), playerPos, 30 + i));
                getStage().addActor(new PositionProjectile(new Texture("Images/WesternDentist_BossBurstL4P3.png"), 100, "Enemy", 50 + rng.nextFloat(), 550 + rng.nextFloat(), playerPos, 30 + i));
                getStage().addActor(new PositionProjectile(new Texture("Images/WesternDentist_BossBurstL4P3.png"), 100, "Enemy", 50 + rng.nextFloat(), 50 + rng.nextFloat(), playerPos, 30 + i));
                getStage().addActor(new PositionProjectile(new Texture("Images/WesternDentist_BossBurstL4P3.png"), 100, "Enemy", 500 + rng.nextFloat(), 50 + rng.nextFloat(), playerPos, 30 + i));
            }
            for (int i = 0; i < 3; ++i) {
                getStage().addActor(new PositionProjectile(new Texture("Images/WesternDentist_BossBurstL4P3.png"), 100, "Enemy", 500 + rng.nextFloat(), 550 + rng.nextFloat(), playerPos, 30 + i));
                getStage().addActor(new PositionProjectile(new Texture("Images/WesternDentist_BossBurstL4P3.png"), 100, "Enemy", 50 + rng.nextFloat(), 550 + rng.nextFloat(), playerPos, 30 + i));
                getStage().addActor(new PositionProjectile(new Texture("Images/WesternDentist_BossBurstL4P3.png"), 100, "Enemy", 50 + rng.nextFloat(), 50 + rng.nextFloat(), playerPos, 30 + i));
                getStage().addActor(new PositionProjectile(new Texture("Images/WesternDentist_BossBurstL4P3.png"), 100, "Enemy", 500 + rng.nextFloat(), 50 + rng.nextFloat(), playerPos, 30 + i));
            }

            phase3Counter = 0;
        }
        else {
            phase3Counter += 1000 * delta;
        }
    }

    public int getPhase() {
        return phase;
    }
}
