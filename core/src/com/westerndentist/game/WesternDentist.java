package com.westerndentist.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.concurrent.TimeUnit;

/**
 * Game class, handles all game logic for selecting, rendering, changing, and restarting stages. Loads main assets.
 */
public class WesternDentist extends Game {
    public float masterVolume = 1.0f;
    public float musicVolume = 1.0f;
    public float soundEffectVolume = 1.0f;
    public static float musicVolumeActual;
    public static float soundEffectVolumeActual;
    public BitmapFont font;
    public TextButton.TextButtonStyle textButtonStyle;
    public Slider.SliderStyle sliderStyle;
    public Label.LabelStyle labelStyle;
    public FitViewport viewport;
    public Sound music;
    public long musicID;
    public Player player;
    public int hiScore;
    public Stage currentStage;
    private Stage userInterface;
    private Stage pauseMenu;
    private boolean paused = false;
    private Sound theme;
    private Sound level1;
    private Sound level2;
    private Sound level3;
    private Sound level4;
    private Sound bossWarning;
    private Sound level1Boss;
    private Sound level2Boss;
    private Sound level3Boss;
    private Sound level4Boss;
    private int level1Score;
    private int level2Score;
    private int level3Score;
    private int level4Score;
    private long startTime;
    private long level1Time;
    private long level2Time;
    private long level3Time;
    private long level4Time;
    public static boolean changing = false;
    private float volumeFade;

    /**
     * Implementation of abstract create() method, acts as game's "constructor", ran before anything else
     */
	@Override
	public void create () {
        theme = Gdx.audio.newSound(Gdx.files.internal("sounds/theme.mp3"));
        level1 = Gdx.audio.newSound(Gdx.files.internal("sounds/level1.mp3"));
        level2 = Gdx.audio.newSound(Gdx.files.internal("sounds/level2.mp3"));
        level3 = Gdx.audio.newSound(Gdx.files.internal("sounds/level3.mp3"));
        level4 = Gdx.audio.newSound(Gdx.files.internal("sounds/level4.mp3"));
        bossWarning = Gdx.audio.newSound(Gdx.files.internal("sounds/bosswarning.mp3"));
        level1Boss = Gdx.audio.newSound(Gdx.files.internal("sounds/level1Boss.mp3"));
        level2Boss = Gdx.audio.newSound(Gdx.files.internal("sounds/level2Boss.mp3"));
        level3Boss = Gdx.audio.newSound(Gdx.files.internal("sounds/level3Boss.mp3"));
        level4Boss = Gdx.audio.newSound(Gdx.files.internal("sounds/level4Boss.mp3"));
        font = new BitmapFont(Gdx.files.internal("fonts/touhoufont.fnt"), Gdx.files.internal("fonts/touhoufont.png"), false);
        textButtonStyle = new TextButton.TextButtonStyle(
                null,
                new TextureRegionDrawable(new TextureRegion(new Texture("images/buttonDown.png"))),
                null,
                font);
        textButtonStyle.over = new TextureRegionDrawable(new TextureRegion(new Texture("images/buttonOver.png")));
        sliderStyle = new Slider.SliderStyle(
                new TextureRegionDrawable(new TextureRegion(new Texture("images/sliderBackground.png"))),
                new TextureRegionDrawable(new TextureRegion(new Texture("images/sliderKnob.png")))
        );
        labelStyle = new Label.LabelStyle(font, null);
	    viewport = new FitViewport(800, 600);
        currentStage = new SplashScreen(this);
        player = new Player(260, 100, this);
        userInterface = new UserInterface(this);
        pauseMenu = new PauseMenu(this);
        Gdx.input.setInputProcessor(currentStage);
        musicVolumeActual = masterVolume*musicVolume;
        soundEffectVolumeActual = masterVolume*soundEffectVolume;
	}

    /**
     * Override of Game render method, handles drawing and acting each stage every frame, then passes off to super.render() to finish rendering
     */
	@Override
	public void render () {
        if (!(currentStage instanceof SplashScreen) && !(currentStage instanceof MainMenu) && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && !changing) {
            paused = !paused;
            if (paused) {
                music.pause();
                Gdx.input.setInputProcessor(pauseMenu);
            } else {
                music.resume();
                pauseMenu.clear();
                pauseMenu.dispose();
                pauseMenu = new PauseMenu(this);
                Gdx.input.setInputProcessor(currentStage);
            }
        }
	    Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (!paused) {
            currentStage.act(Gdx.graphics.getDeltaTime());
            currentStage.draw();
            if (hiScore < player.getScore()) {
                hiScore = player.getScore();
            }
            if (!(currentStage instanceof SplashScreen) && !(currentStage instanceof MainMenu)) {
                userInterface.act(Gdx.graphics.getDeltaTime());
                userInterface.draw();
            }
        } else {
            currentStage.draw();
            if (!(currentStage instanceof SplashScreen) && !(currentStage instanceof MainMenu)) {
                userInterface.draw();
            }
            pauseMenu.act(Gdx.graphics.getDeltaTime());
            pauseMenu.draw();
        }
        musicVolumeActual = masterVolume*musicVolume;
        soundEffectVolumeActual = masterVolume*soundEffectVolume;
		super.render();
	}

    /**
     * Override of Game resize method, called whenever the window size changes
     * @param width     new width of window
     * @param height    new height of window
     */
	@Override
    public void resize (int width, int height) {
        currentStage.getViewport().update(width, height, true);
        userInterface.getViewport().update(width, height, true);
        pauseMenu.getViewport().update(width, height, true);
        super.resize(width, height);
    }

    /**
     * changeStage method, handles changing one stage to another. Has special cases for levels changing to other levels, to display a special level end screen
     * @param newStage  new stage to change to
     */
    public void changeStage(final Stage newStage) {
	    if (!changing) {
            changing = true;
            paused = false;
            pauseMenu.clear();
            pauseMenu.dispose();
            pauseMenu = new PauseMenu(this);
            Table table;
            Image background;
            Label levelCompleteLabel;
            Label levelReadyLabel;
            Label levelScoreLabel;
            Label levelTimeLabel = null;
            long minutes;
            long seconds;
            if (!(currentStage instanceof SplashScreen) && player.getScore() > -1) {
                if (!(currentStage instanceof MainMenu)) {
                    volumeFade = musicVolumeActual;
                    final Timer timer = new Timer();
                    timer.scheduleTask(new Timer.Task() {
                        @Override
                        public void run() {
                            volumeFade -= 0.0025 * musicVolumeActual;
                            music.setVolume(musicID, volumeFade);
                            if (volumeFade <= 0) {
                                music.stop();
                                timer.stop();
                            }
                        }
                    }, 0, 0.01f);
                    timer.start();
                }
                if (currentStage instanceof Level1 || currentStage instanceof Level2 || currentStage instanceof Level3) {
                    table = new Table();
                    table.setSize(560, 600);
                    table.center();
                    background = new Image(new Texture("images/background.png"));
                    currentStage.addActor(background);
                    if (currentStage instanceof Level1) {
                        level1Score = player.getScore();
                        level1Time = TimeUtils.timeSinceMillis(startTime);
                        minutes = TimeUnit.MILLISECONDS.toMinutes(level1Time);
                        seconds = TimeUnit.MILLISECONDS.toSeconds(level1Time) - TimeUnit.MINUTES.toSeconds(minutes);
                        levelTimeLabel = new Label("Time: " + String.format("%01d\"%02d\'", minutes, seconds), labelStyle);
                    } else if (currentStage instanceof Level2) {
                        level2Score = player.getScore();
                        level2Time = TimeUtils.timeSinceMillis(startTime);
                        minutes = TimeUnit.MILLISECONDS.toMinutes(level2Time);
                        seconds = TimeUnit.MILLISECONDS.toSeconds(level2Time) - TimeUnit.MINUTES.toSeconds(minutes);
                        levelTimeLabel = new Label("Time: " + String.format("%01d\"%02d\'", minutes, seconds), labelStyle);
                    } else if (currentStage instanceof Level3) {
                        level3Score = player.getScore();
                        level3Time = TimeUtils.timeSinceMillis(startTime);
                        minutes = TimeUnit.MILLISECONDS.toMinutes(level3Time);
                        seconds = TimeUnit.MILLISECONDS.toSeconds(level3Time) - TimeUnit.MINUTES.toSeconds(minutes);
                        levelTimeLabel = new Label("Time: " + String.format("%01d\"%02d\'", minutes, seconds), labelStyle);
                    }
                    levelCompleteLabel = new Label("Complete!", labelStyle);
                    levelReadyLabel = new Label("Get ready for next level!", labelStyle);
                    levelScoreLabel = new Label("Score: " + String.format("%09d", player.getScore()), labelStyle);
                    table.add(levelCompleteLabel);
                    table.row();
                    table.add(levelReadyLabel);
                    table.row();
                    table.add(levelScoreLabel);
                    table.row();
                    table.add(levelTimeLabel);
                    currentStage.addActor(table);
                } else if (currentStage instanceof Level4) {
                    table = new Table();
                    table.setSize(560, 600);
                    table.center();
                    background = new Image(new Texture("images/background.png"));
                    currentStage.addActor(background);
                    level4Score = player.getScore();
                    level4Time = TimeUtils.timeSinceMillis(startTime);
                    levelCompleteLabel = new Label("Game Complete!", labelStyle);
                    levelReadyLabel = new Label("Congratulations!", labelStyle);
                    Label level1ScoreLabel = new Label("Level 1 Score: " + String.format("%09d", level1Score), labelStyle);
                    minutes = TimeUnit.MILLISECONDS.toMinutes(level1Time);
                    seconds = TimeUnit.MILLISECONDS.toSeconds(level1Time) - TimeUnit.MINUTES.toSeconds(minutes);
                    Label level1TimeLabel = new Label("Level 1 Time: " + String.format("%01d\"%02d\'", minutes, seconds), labelStyle);
                    Label level2ScoreLabel = new Label("Level 2 Score: " + String.format("%09d", level2Score), labelStyle);
                    minutes = TimeUnit.MILLISECONDS.toMinutes(level2Time);
                    seconds = TimeUnit.MILLISECONDS.toSeconds(level2Time) - TimeUnit.MINUTES.toSeconds(minutes);
                    Label level2TimeLabel = new Label("Level 2 Time: " + String.format("%01d\"%02d\'", minutes, seconds), labelStyle);
                    Label level3ScoreLabel = new Label("Level 3 Score: " + String.format("%09d", level3Score), labelStyle);
                    minutes = TimeUnit.MILLISECONDS.toMinutes(level3Time);
                    seconds = TimeUnit.MILLISECONDS.toSeconds(level3Time) - TimeUnit.MINUTES.toSeconds(minutes);
                    Label level3TimeLabel = new Label("Level 3 Time: " + String.format("%01d\"%02d\'", minutes, seconds), labelStyle);
                    Label level4ScoreLabel = new Label("Level 4Score: " + String.format("%09d", level4Score), labelStyle);
                    minutes = TimeUnit.MILLISECONDS.toMinutes(level4Time);
                    seconds = TimeUnit.MILLISECONDS.toSeconds(level4Time) - TimeUnit.MINUTES.toSeconds(minutes);
                    Label level4TimeLabel = new Label("Level 4 Time: " + String.format("%01d\"%02d\'", minutes, seconds), labelStyle);
                    levelScoreLabel = new Label("Total Score: " + String.format("%09d", level1Score + level2Score + level3Score + level4Score), labelStyle);
                    long totalTime = level1Time + level2Time + level3Time + level4Time;
                    minutes = TimeUnit.MILLISECONDS.toMinutes(totalTime);
                    seconds = TimeUnit.MILLISECONDS.toSeconds(totalTime) - TimeUnit.MINUTES.toSeconds(minutes);
                    levelTimeLabel = new Label("Total Time: " + String.format("%01d\"%02d\'", minutes, seconds), labelStyle);
                    table.add(levelCompleteLabel);
                    table.row();
                    table.add(levelReadyLabel);
                    table.row();
                    table.add(level1ScoreLabel);
                    table.row();
                    table.add(level1TimeLabel);
                    table.row();
                    table.add(level2ScoreLabel);
                    table.row();
                    table.add(level2TimeLabel);
                    table.row();
                    table.add(level3ScoreLabel);
                    table.row();
                    table.add(level3TimeLabel);
                    table.row();
                    table.add(level4ScoreLabel);
                    table.row();
                    table.add(level4TimeLabel);
                    table.row();
                    table.add(levelScoreLabel);
                    table.row();
                    table.add(levelTimeLabel);
                    currentStage.addActor(table);
                }
                if (currentStage instanceof MainMenu) {
                    player.setPower(0);
                }
                player.setHealth(5);
                player.setScore(0);
                player.setPosition(260, 100);
                hiScore = 0;
                SequenceAction sequence = Actions.sequence();
                if (!(currentStage instanceof MainMenu)) {
                    if (!(currentStage instanceof Level4)) {
                        sequence.addAction(Actions.delay(7.5f));
                    } else {
                        sequence.addAction(Actions.delay(15f));
                    }
                }
                sequence.addAction(Actions.fadeOut(1));
                sequence.addAction(Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        currentStage.clear();
                        currentStage.dispose();
                        currentStage = newStage;
                        userInterface.clear();
                        if (!(currentStage instanceof SplashScreen) && !(currentStage instanceof MainMenu)) {
                            userInterface.dispose();
                            userInterface = new UserInterface(WesternDentist.this);
                        }
                        Gdx.input.setInputProcessor(currentStage);
                        playMusic(false);
                        startTime = TimeUtils.millis();
                        changing = false;
                    }
                }));
                currentStage.addAction(sequence);
            } else {
                currentStage.clear();
                currentStage.dispose();
                currentStage = newStage;
                userInterface.clear();
                if (!(currentStage instanceof SplashScreen) && !(currentStage instanceof MainMenu)) {
                    userInterface.dispose();
                    userInterface = new UserInterface(WesternDentist.this);
                }
                Gdx.input.setInputProcessor(currentStage);
                playMusic(false);
                changing = false;
            }
        }
    }

    /**
     * restartStage method, allows for the current stage (1, 2, 3, or 4) to be manually restarted, or called when player dies
     */
    public void restartStage() {
	    paused = false;
        pauseMenu.clear();
        pauseMenu.dispose();
        pauseMenu = new PauseMenu(this);
        userInterface.clear();
        userInterface.dispose();
        userInterface = new UserInterface(this);
        player.setPower(0);
        player.setHealth(5);
        player.setScore(0);
        player.setPosition(300, 100);
        currentStage.clear();
        currentStage.dispose();
        if (currentStage instanceof Level1) {
            currentStage = new Level1(this);
        } else if (currentStage instanceof Level2) {
            currentStage = new Level2(this);
        } else if (currentStage instanceof Level3) {
            currentStage = new Level3(this);
        } else if (currentStage instanceof Level4) {
            currentStage = new Level4(this);
        }
        Gdx.input.setInputProcessor(currentStage);
        playMusic(false);
    }

    /**
     * playMusic method, handles playing music based on the current stage, and allows for stages to initiate a boss fight with appropriate music and warning screen
     * @param bossBattle    flag to determine if a boss battle is to begin
     */
    public void playMusic(boolean bossBattle) {
	    if (bossBattle) {
            final Image warningBG = new Image(new Texture("images/warning_background.png"));
            final Image warningFG = new Image(new Texture("images/warning_foreground.png"));
            warningBG.setPosition(-971, 150);
            warningBG.addAction(Actions.moveTo(0, 150, 8f));
            warningFG.setPosition(0, 229);
            warningFG.addAction(Actions.moveTo(-971, 229, 8f));
            SequenceAction sequence = Actions.sequence();
            sequence.addAction(Actions.delay(3.1f));
            sequence.addAction(Actions.run(new Runnable() {
                @Override
                public void run() {
                    music.stop();
                    if (currentStage instanceof Level1) {
                        music = level1Boss;
                    } else if (currentStage instanceof Level2) {
                        music = level2Boss;
                    } else if (currentStage instanceof Level3) {
                        music = level3Boss;
                    } else if (currentStage instanceof Level4) {
                        music = level4Boss;
                    }
                    musicID = music.loop(musicVolumeActual);
                    warningBG.remove();
                    warningFG.remove();
                }
            }));
            currentStage.addAction(sequence);
            currentStage.addActor(warningBG);
            currentStage.addActor(warningFG);
            bossWarning.play(soundEffectVolumeActual);
        } else {
            if (music != null) {
                music.stop();
            }
            if (currentStage instanceof MainMenu) {
                music = theme;
            } else if (currentStage instanceof Level1) {
                music = level1;
            } else if (currentStage instanceof Level2) {
                music = level2;
            } else if (currentStage instanceof Level3) {
                music = level3;
            } else if (currentStage instanceof Level4) {
                music = level4;
            }
            musicID = music.loop(musicVolumeActual);
        }
    }
}
