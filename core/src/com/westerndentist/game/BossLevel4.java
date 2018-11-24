package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BossLevel4 extends Boss {

    private float phase1damage = 2500, phase2damage = 5000,
                  phase3damage = 10000, phase4damage = 20000;

    private Texture texture = new Texture("Images/l4BossSprite.png");

    private int phase = 1;

    private float proj1xFactor = 1000, proj1yFactor = 0;

    private float rateCounter1 = 0;
    private float fireRate1 = 10;

    private float damage = 0;

    private boolean spawned = false;

    BossLevel4() {
        super();

        bounds = new Rectangle(getX(), getY(), texture.getWidth(), texture.getHeight());
    }

    @Override
    public void act(float delta) {
        if (!spawned) {
            spawned = true;
            // Play sound, do stuff
            Gdx.app.log("Boss 4", "Spawned");
        }
        super.act(delta);
        takeDamageFromProjectile();
        changePhase();
        phase(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

    private void changePhase() {
        if (damage > phase1damage) {
            phase = 2;
        }
        else if (damage > phase2damage) {
            phase = 3;
        }
        else if (damage > phase3damage) {
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

    private void phase(float delta) {
        switch (phase) {
            case 1:
                phase1(delta);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
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
                getStage().addActor(new NonVerticalProjectile(new Texture("Images/WesternDentist_BossBurst.png"), 100, getX() + texture.getWidth() / 2, getY() + texture.getHeight() / 2, "Enemy", proj1xFactor / 1000, proj1yFactor / 1000, false, false));
            } catch (NullPointerException e) {

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
}
