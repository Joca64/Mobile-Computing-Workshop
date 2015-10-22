package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Number
{
    Texture texture;
    Rectangle collisionBox;
    int value;
    int speed;

    public Number(float width, float height)
    {
        value = getRandom(0,9);
        texture = new Texture("images/numbers/" +value +".png");
        speed = getRandom(1,4);
        collisionBox = new Rectangle(getRandom(0, (int)width - texture.getWidth()), height, texture.getWidth(), texture.getHeight());
    }

    public void remove()
    {
        texture.dispose();
    }

    public void move()
    {
        collisionBox.y -= speed;
    }

    private int getRandom(int min, int max)
    {
        Random rnd = new Random();
        return rnd.nextInt(max - min) +min;
    }

    public int getValue()
    {
        return value;
    }

    public Texture getTexture()
    {
        return texture;
    }

    public Rectangle getCollisionBox()
    {
        return collisionBox;
    }

    public float getX()
    {
        return collisionBox.x;
    }

    public float getY()
    {
        return collisionBox.y;
    }
}
