package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.sql.Time;
import java.util.ArrayList;

public class Level4 extends Stage {

    private WesternDentist game;

    private Sequencer sequencer;

    private Image background = new Image(new Texture("Images/WesternDentist_BackgroundL4.png")), background2 = new Image(new Texture("Images/WesternDentist_BackgroundL4.png"));

    private boolean restart = false;

    private boolean bossSpawned = false;
    private boolean bossThread = false;
    private boolean musicThread = false;
    private boolean bossMusicPlayed = false;
    private boolean bossDead = false;

    private Thread bossT;
    private Thread musicT;

    Level4(final WesternDentist game) {
        super(game.viewport);
        setDebugAll(false);
        try {
            Thread.sleep(500);
        }
        catch (InterruptedException e) {
            Gdx.app.log("Level 4", "Main thread interrupted");
        }

        bossSpawned = false;
        bossThread = false;
        musicThread = false;
        bossMusicPlayed = false;
        bossDead = false;

        background.setPosition(0, 2500);
        background2.setPosition(0, -2500);
        background.setName("BACKGROUND");
        background2.setName("BACKGROUND");

        addActor(background);
        addActor(background2);

        SprayEnemy testActor = new SprayEnemy(new Texture("Images/sprayEnemySprite.png"), 100, 300);
        testActor.setPosition(400, 300);
        testActor.addAction(Actions.moveTo(400, 550, 5));

        game.player.setPosition(300, 100);
        addActor(game.player);

        sequencer = new Sequencer();

        sequencer.addPhase(300);
        sequencer.addPhase(400);
        sequencer.addPhase(100000);

        sequencer.addActorToPhase(0, new DiagonalEnemy(new Texture("Images/basicEnemySprite.png"), 10, 100, -200));
        sequencer.addPhaseSpawnFrequency(0, 30);
        sequencer.addPhaseSpawnPosition(0, new Vector2(600, 700));

        sequencer.addActorToPhase(0, new DiagonalEnemy(new Texture("Images/basicEnemySprite.png"), 10, 100, 200));
        sequencer.addPhaseSpawnFrequency(0, 30);
        sequencer.addPhaseSpawnPosition(0, new Vector2(0, 700));

        sequencer.addActorToPhase(0, new DiagonalEnemy(new Texture("Images/basicEnemySprite.png"), 10, 100, -200));
        sequencer.addPhaseSpawnFrequency(0, 60);
        sequencer.addPhaseSpawnPosition(0, new Vector2(600, 700));

        sequencer.addActorToPhase(0, new DiagonalEnemy(new Texture("Images/basicEnemySprite.png"), 10, 100, 200));
        sequencer.addPhaseSpawnFrequency(0, 60);
        sequencer.addPhaseSpawnPosition(0, new Vector2(0, 700));

        sequencer.addActorToPhase(1, testActor);
        sequencer.addPhaseSpawnFrequency(1, 30);
        sequencer.addPhaseSpawnPosition(1, new Vector2(0, 1000));

        SprayEnemy nextActor = new SprayEnemy(new Texture("Images/sprayEnemySprite.png"), 100, 300);
        nextActor.addAction(Actions.moveTo(100, 550, 5));

        sequencer.addActorToPhase(1, nextActor);
        sequencer.addPhaseSpawnFrequency(1, 33);
        sequencer.addPhaseSpawnPosition(1, new Vector2(0, 1000));

        BossLevel4 boss = new BossLevel4();
        boss.addAction(Actions.moveTo(300, 400, 6));

        sequencer.addActorToPhase(2, boss);
        sequencer.addPhaseSpawnFrequency(2, 100);
        sequencer.addPhaseSpawnPosition(2, new Vector2(300, 1000));

        this.game = game;
    }

    @Override
    public void act(final float delta) {
        backgroundScrolling(delta);
        sequencer.update(delta, this);
        if (!bossMusicPlayed) {
            checkBoss();
        }
        if (!bossThread) {
            bossObserverDead(false);
        }
        if (bossDead) {
            endLevel();
            bossDead = false;
        }
        super.act(delta);
    }

    @Override
    public void draw() {
        sortActors();
        super.draw();
    }

    private void bossObserverDead(boolean force) {
        class Observer implements Runnable {
            boolean died = true;
            boolean interrupted = false;
            int phase = 0;

            @Override
            public void run() {
                Gdx.app.log("Boss Observer Thread 2", "Started");

                try {
                    Thread.sleep(10000);
                }
                catch (InterruptedException e) {
                    Gdx.app.log("Boss Observer Thread 2", "ERROR: Interrupted while sleeping");
                    Thread.currentThread().interrupt();
                }

                while (!interrupted && bossThread) {
                    died = true;

                    for (int i = 0; i < getActors().size; ++i) {
                        try {
                            if (BossLevel4.class.isInstance(getActors().items[i]) && phase < 4) {
                                phase = ((BossLevel4)getActors().items[i]).getPhase();
                                break;
                            }
                            else if (BossLevel4.class.isInstance(getActors().items[i]) && phase < 4) {
                                died = false;
                            }
                        }
                        catch (NullPointerException t) {
                            t.printStackTrace();
                            Gdx.app.log("Boss Observer Thread 2", "Object being checked was deleted.");
                        }
                    }

                    if (Thread.interrupted()) {
                        break;
                    }

                    if (phase < 4) {
                        try {
                            Thread.sleep(500);
                        }
                        catch (InterruptedException e) {
                            interrupted = true;
                            Gdx.app.log("Boss Observer Thread 2", "ERROR: Interrupted while sleeping");
                        }
                    }
                    else if (died) {
                        break;
                    }
                }

                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    Gdx.app.log("Boss Observer Thread 2", "ERROR: Interrupted while sleeping");
                    Thread.currentThread().interrupt();
                }

                if (bossThread && !interrupted) {
                    bossDead = true;
                    bossThread = false;
                    Gdx.app.log("Boss Observer Thread 2", "Boss dead");
                }
                else {
                    bossDead = false;
                }

                Thread.currentThread().interrupt();
            }
        }

        if (bossSpawned && !bossThread && !bossDead) {
            bossThread = true;
            Observer observer = new Observer();
            bossT = new Thread(observer);
            bossT.start();
        }

        if (force) {
            bossThread = true;
            Observer observer = new Observer();
            bossT = new Thread(observer);
            bossT.start();
        }
    }

    private void checkBoss() {
        class Observer implements Runnable {
            @Override
            public void run() {
                Gdx.app.log("Boss observer thread", "Started");

                try {
                    Thread.sleep(1500);
                }
                catch (InterruptedException e) {
                    Gdx.app.log("Boss Observer Thread", "ERROR: Interrupted while sleeping");
                    Thread.currentThread().interrupt();
                }

                if (!restart) {
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                        Gdx.app.log("Boss Observer Thread", "ERROR: Interrupted while sleeping");
                    }
                    restart = true;
                    bossSpawned = false;
                    bossThread = false;
                    musicThread = false;
                    bossMusicPlayed = false;
                    bossDead = false;
                }

                while (musicThread) {
                    boolean spawned = false;

                    for (int i = 0; i < getActors().size; ++i) {
                        if (BossLevel4.class.isInstance(getActors().items[i])) {
                            spawned = true;
                            break;
                        }
                    }

                    if (spawned) {
                        break;
                    }
                }

                if (musicThread) {
                    Gdx.app.log("Boss Observer Thread", "Music Played");
                    bossSpawned = true;
                }
                else {
                    bossSpawned = false;
                }
            }
        }

        if (!musicThread) {
            musicT = new Thread(new Observer());
            musicT.start();
            musicThread = true;
        }

        if (bossSpawned) {
            playMusic();
        }
    }

    private void endLevel() {
        game.changeStage(new MainMenu(game));
    }

    private void sortActors() {
        for (int i = 0; i < getActors().size; ++i) {
            int z = getActors().items[i].getZIndex();
            if (Image.class.isInstance(getActors().items[i]) && getActors().items[i].getName() == "UI_FRAME") {
                if (getActors().items[i].getZIndex() != 300000) {
                    getActors().items[i].setZIndex(300000);
                }
            }
            else if (Image.class.isInstance(getActors().items[i]) && getActors().items[i].getName() == "BACKGROUND") {
                getActors().items[i].setZIndex(0);
            }
            else if (Image.class.isInstance(getActors().items[i])) {
                getActors().items[i].setZIndex(0);
            }
            else if (Player.class.isInstance(getActors().items[i])) {
                getActors().items[i].setZIndex(3000);
            }
            else if (z > 3000) {
                getActors().items[i].setZIndex(z - 3000);
            }
            else {
                getActors().items[i].setZIndex(1);
            }
        }
    }

    private void backgroundScrolling(float delta) {
        background.setY(background.getY() - 30 * delta);
        background2.setY(background2.getY() - 30 * delta);

        if (background.getY() <= -5000) {
            background.setY(5000);
        }

        if (background2.getY() <= -5000) {
            background2.setY(5000);
        }
    }

    public void playMusic() {
        if (bossSpawned) {
            bossMusicPlayed = true;
            game.playMusic(true);
        }
        else {
            Gdx.app.log("Level 4", "Broken threads");
            bossMusicPlayed = true;
            game.playMusic(true);
            bossObserverDead(true);
        }
    }
}
