package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Level1 extends Stage
{
    private WesternDentist game;

    private Sequencer sequencer;

    private boolean bossSpawned = false;

    private Image background = new Image(new Texture("Images/WesternDentist_Background.png")),
            background2 = new Image(new Texture("Images/WesternDentist_Background2.png"));

    private BossHikeMaze hikeMaze = new BossHikeMaze(new Texture("Images/Hike Maze zombie.png"), 7500, new Vector2(0, 400));

    private BossTrerryClews trerryClews = new BossTrerryClews(new Texture("Images/Scary Trews.png"), 15000, new Vector2(0, 400));


    /**
     * The constructor for the first level
     * @param game - The whole game is passed in which handles pause and many other functions
     */
    Level1(final WesternDentist game)
    {
        super(game.viewport);
        background.setPosition(0, 2500);
        background2.setPosition(0, -2500);

        addActor(background);
        addActor(background2);

        addActor(game.player);
        game.player.addAura();

        hikeMaze.addAction(Actions.moveTo(100, 300, 5));

        trerryClews.addAction(Actions.moveTo(150,365, 10));

        sequencer = new Sequencer();

        sequencer.addPhase(200);
        sequencer.addPhase(200);
        sequencer.addPhase(200);
        sequencer.addPhase(600);
        sequencer.addPhase(100000);

        sequencer.addActorToPhase(0, new EnemyLevel1(new Texture("Images/bubber ducky.png"), 220, 100, 3000, new Vector2(0, 0)));
        sequencer.addPhaseSpawnFrequency(0, 50);
        sequencer.addPhaseSpawnPosition(0, new Vector2(40, 700));
        sequencer.addActorToPhase(0, new EnemyLevel1(new Texture("Images/bubber ducky.png"), -220, 100, 3000, new Vector2(0, 0)));
        sequencer.addPhaseSpawnFrequency(0, 100);
        sequencer.addPhaseSpawnPosition(0, new Vector2(560, 700));

        sequencer.addActorToPhase(1, new EnemyLevel1(new Texture("Images/bubber ducky.png"), 220, 100, 3000, new Vector2(0, 0)));
        sequencer.addPhaseSpawnFrequency(1, 50);
        sequencer.addPhaseSpawnPosition(1, new Vector2(40, 700));
        sequencer.addActorToPhase(1, new EnemyLevel1(new Texture("Images/bubber ducky.png"), -220, 100, 3000, new Vector2(0, 0)));
        sequencer.addPhaseSpawnFrequency(1, 100);
        sequencer.addPhaseSpawnPosition(1, new Vector2(560, 700));
        sequencer.addActorToPhase(1, new EnemyLevel1(new Texture("Images/bubber ducky.png"), 220, 100, 3000, new Vector2(0, 0)));
        sequencer.addPhaseSpawnFrequency(1, 75);
        sequencer.addPhaseSpawnPosition(1, new Vector2(40, 800));
        sequencer.addActorToPhase(1, new EnemyLevel1(new Texture("Images/bubber ducky.png"), -220, 100, 3000, new Vector2(0, 0)));
        sequencer.addPhaseSpawnFrequency(1, 125);
        sequencer.addPhaseSpawnPosition(1, new Vector2(560, 800));

        sequencer.addActorToPhase(2, new EnemyLevel1(new Texture("Images/bubber ducky.png"), 220, 100, 3000, new Vector2(0, 0)));
        sequencer.addPhaseSpawnFrequency(2, 50);
        sequencer.addPhaseSpawnPosition(2, new Vector2(40, 700));
        sequencer.addActorToPhase(2, new EnemyLevel1(new Texture("Images/bubber ducky.png"), -220, 100, 3000, new Vector2(0, 0)));
        sequencer.addPhaseSpawnFrequency(2, 51);
        sequencer.addPhaseSpawnPosition(2, new Vector2(560, 700));
        sequencer.addActorToPhase(2, new EnemyLevel1(new Texture("Images/bubber ducky.png"), 220, 100, 3000, new Vector2(0, 0)));
        sequencer.addPhaseSpawnFrequency(2, 100);
        sequencer.addPhaseSpawnPosition(2, new Vector2(40, 800));
        sequencer.addActorToPhase(2, new EnemyLevel1(new Texture("Images/bubber ducky.png"), -220, 100, 3000, new Vector2(0, 0)));
        sequencer.addPhaseSpawnFrequency(2, 101);
        sequencer.addPhaseSpawnPosition(2, new Vector2(560, 800));
        sequencer.addActorToPhase(2, new EnemyLevel1(new Texture("Images/bubber ducky.png"), 220, 100, 3000, new Vector2(0, 0)));
        sequencer.addPhaseSpawnFrequency(2, 75);
        sequencer.addPhaseSpawnPosition(2, new Vector2(40, 700));
        sequencer.addActorToPhase(2, new EnemyLevel1(new Texture("Images/bubber ducky.png"), -220, 100, 3000, new Vector2(0, 0)));
        sequencer.addPhaseSpawnFrequency(2, 76);
        sequencer.addPhaseSpawnPosition(2, new Vector2(560, 700));
        sequencer.addActorToPhase(2, new EnemyLevel1(new Texture("Images/bubber ducky.png"), 220, 100, 3000, new Vector2(0, 0)));
        sequencer.addPhaseSpawnFrequency(2, 125);
        sequencer.addPhaseSpawnPosition(2, new Vector2(40, 800));
        sequencer.addActorToPhase(2, new EnemyLevel1(new Texture("Images/bubber ducky.png"), -220, 100, 3000, new Vector2(0, 0)));
        sequencer.addPhaseSpawnFrequency(2, 126);
        sequencer.addPhaseSpawnPosition(2, new Vector2(560, 800));

        sequencer.addActorToPhase(3, hikeMaze);
        sequencer.addPhaseSpawnFrequency(3, 20);
        sequencer.addPhaseSpawnPosition(3, new Vector2(0, 700));

        sequencer.addActorToPhase(4, trerryClews);
        sequencer.addPhaseSpawnFrequency(4, 180);
        sequencer.addPhaseSpawnPosition(4, new Vector2(0, 700));

        this.game = game;
    }

    @Override
    public void act(float delta)
    {
        checkBoss();
        backgroundScrolling(delta);
        sequencer.update(delta, this);
        super.act(delta);
    }

    @Override
    public void draw()
    {
        //sortActors();
        super.draw();
    }

    /**
     * This function will check if the boss has spawned from the list of actors and play the boss music
     * If the boss has died the game will load the next level
     */
    private void checkBoss()
    {
        if (!bossSpawned)
        {
            try
            {
                for (Actor actor: getActors())
                {
                    if (actor instanceof BossTrerryClews)
                    {
                        bossSpawned = true;
                        game.playMusic(true);
                    }
                }
            }
            catch (NullPointerException e)
            {
                Gdx.app.log("Level: ", "Level could not get the actor");
            }
        }

        if(trerryClews.health <= 0)
        {
            game.player.remove();
            trerryClews.health = 1;
            trerryClews.giveScore();
            trerryClews.remove();
            bossSpawned = false;
            Level2 level2 = new Level2(game);
            game.changeStage(level2);
        }

    }

    /**
     * This function will scroll the background from top to bottom
     * @param delta - time in seconds since the last frame
     */
    private void backgroundScrolling(float delta) {
        background.setY(background.getY() + 30 * delta);
        background2.setY(background2.getY() + 30 * delta);

        if (background.getY() >= 5000) {
            background.setY(-5000);
        }

        if (background2.getY() >= 5000) {
            background2.setY(-5000);
        }
    }

}
