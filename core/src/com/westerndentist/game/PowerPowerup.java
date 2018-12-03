package com.westerndentist.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PowerPowerup extends Powerup{

    private Texture texture = new Texture("Images/powerPickup.png");

    PowerPowerup(float lifetime, float value, Vector2 position) {
        this.lifetime = lifetime;
        this.value = value;

        setPosition(position.x, position.y);
        bounds = new Rectangle(getX(), getY(), texture.getWidth(), texture.getHeight());
        setName("Power");
    }

    PowerPowerup(float lifetime, float value, Vector2 position, boolean boss) {
        this.lifetime = lifetime;
        this.value = value;
        this.boss = boss;

        setPosition(position.x, position.y);
        bounds = new Rectangle(getX(), getY(), texture.getWidth(), texture.getHeight());
        setName("Power");
    }


    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        super.drawDebug(shapes);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }
}
