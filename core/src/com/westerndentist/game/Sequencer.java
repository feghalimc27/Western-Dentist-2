package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

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

    public void update(float delta, Stage stage) {
        if (spawnFrequency.size() > 0 && currentPhase == 0 && currentSpawnTime.size() == 0) {
            for (int i = 0; i < spawnFrequency.get(currentPhase).size(); ++i) {
                currentSpawnTime.add(0.0f);
            }
        }

        if (actors.get(currentPhase).size() > 0) {
            spawnEnemy(stage);
        }
        sequenceTime(delta);
    }

    private void sequenceTime(float delta) {
        phaseTime += delta;

        if (phaseTime > phaseTimes.get(currentPhase)) {
            advanceToNextPhase();
            Gdx.app.log("Sequencer updated phase ", "Phase: " + currentPhase);
        }
    }

    private void spawnEnemy(Stage stage) {
        // Iterate through list
        ArrayList<Integer> toDelete = new ArrayList<Integer>();

        for (int i = 0; i < actors.get(currentPhase).size(); ++i) {
            if (spawnFrequency.get(currentPhase).get(i) > phaseTime) {
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

    public void advanceToNextPhase() {
        currentPhase += 1;
        phaseTime = 0;

        currentSpawnTime.clear();

        for (int i = 0; i < spawnFrequency.get(currentPhase).size(); ++i) {
            currentSpawnTime.add(0.0f);
        }
    }

    public void addActorToPhase(int phase, Actor actor) {
        actors.get(phase).add(actor);
    }

    public void addPhaseSpawnPosition(int phase, Vector2 position) {
        spawnPositions.get(phase).add(position);
    }

    public void addPhaseSpawnFrequency(int phase, float frequency) {
        spawnFrequency.get(phase).add(frequency);
    }

    public void addPhase(float length) {
        phaseTimes.add(length);
        actors.add(new ArrayList<Actor>());
        spawnFrequency.add(new ArrayList<Float>());
        spawnPositions.add(new ArrayList<Vector2>());
    }
}
