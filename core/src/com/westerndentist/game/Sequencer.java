package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

/*
*
* HOW TO USE SEQUENCER:
* 1. In Level constructor add phases with time lengths
* 2. a. Add actors to phase using addActorToPhase()
*    b. Add a time in the phase (less than phase time from addPhase) for the object to spawn using addPhaseSpawnFrequency
*    c. Add a position for the object to spawn at using addPhaseSpawnPosition()
*       View size from UI is from 20, 520 for x
* 3. When calling update in act() for Level pass in delta and this; this being the current level
*
* Level4 Class shows how to use the sequencer but this is just to make it easy to follow.
* Sequencer can be manually advanced to next phase using advanceToNextPhase(). Will automatically advance when all enemies
* are defeated or the length of the phase reaches the end. Will add a UI change indicating phase change on phase change.
*
* */

public class Sequencer {

    private int currentPhase;
    private float phaseTime;
    private ArrayList<Float> currentSpawnTime;

    private ArrayList<Float> phaseTimes;
    private ArrayList<ArrayList<Actor>> actors;
    private ArrayList<ArrayList<Vector2>> spawnPositions;
    private ArrayList<ArrayList<Float>> spawnFrequency;

    Sequencer() {
        actors = new ArrayList<ArrayList<Actor>>();
        phaseTimes = new ArrayList<Float>();
        spawnPositions = new ArrayList<ArrayList<Vector2>>();
        spawnFrequency = new ArrayList<ArrayList<Float>>();
        currentSpawnTime = new ArrayList<Float>();

        currentPhase = 0;
        phaseTime = 0;
    }

    // Must call update for sequencer in act function of level
    public void update(float delta, Stage stage) {

        if (spawnFrequency.size() > 0 && currentPhase == 0 && currentSpawnTime.size() == 0) {
            for (int i = 0; i < spawnFrequency.get(currentPhase).size(); ++i) {
                currentSpawnTime.add(0.0f);
            }
        }

        if (actors.get(currentPhase).size() == 0 && actors.size() > (currentPhase + 1)) {
            advanceToNextPhase();
        }

        if (actors.get(currentPhase).size() > 0) {
            spawnEnemy(stage);
        }
        sequenceTime(delta);
    }

    // Increases the timer for the sequencer
    private void sequenceTime(float delta) {
        phaseTime += delta * 10;

        if (phaseTime > phaseTimes.get(currentPhase)) {
            advanceToNextPhase();
        }
    }

    // Spawns enemies at the time given when adding to the level
    private void spawnEnemy(Stage stage) {
        // Iterate through list
        ArrayList<Integer> toDelete = new ArrayList<Integer>();

        for (int i = 0; i < actors.get(currentPhase).size(); ++i) {
            if (spawnFrequency.get(currentPhase).get(i) <= phaseTime) {
                toDelete.add(i);
                actors.get(currentPhase).get(i).setPosition(spawnPositions.get(currentPhase).get(i).x, spawnPositions.get(currentPhase).get(i).y);
                stage.addActor(actors.get(currentPhase).get(i));
            }
        }

        for (int delete : toDelete) {
            actors.get(currentPhase).remove(delete);
            spawnPositions.get(currentPhase).remove(delete);
            spawnFrequency.get(currentPhase).remove(delete);
        }
        // If time matches, delete time and spawn time from list
    }

    // Advances to next phase when
    public void advanceToNextPhase() {
        Gdx.app.log("Sequencer updated phase ", "Phase: " + currentPhase);

        currentPhase += 1;
        phaseTime = 0;

        currentSpawnTime.clear();

        for (int i = 0; i < spawnFrequency.get(currentPhase).size(); ++i) {
            currentSpawnTime.add(0.0f);
        }
    }

    // Adds an actor to the phase
    public void addActorToPhase(int phase, Actor actor) {
        actors.get(phase).add(actor);
    }

    // Adds a spawn position to the phase
    public void addPhaseSpawnPosition(int phase, Vector2 position) {
        spawnPositions.get(phase).add(position);
    }

    // Adds a spawn time for the actor
    public void addPhaseSpawnFrequency(int phase, float frequency) {
        spawnFrequency.get(phase).add(frequency);
    }

    // Adds a phase with the length of the phase
    public void addPhase(float length) {
        phaseTimes.add(length);
        actors.add(new ArrayList<Actor>());
        spawnFrequency.add(new ArrayList<Float>());
        spawnPositions.add(new ArrayList<Vector2>());
    }
}
