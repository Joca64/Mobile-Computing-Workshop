package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Player
{
    private Texture textureLeft, textureRight;
    private Rectangle collisionBox;
    private boolean facingLeft;

    public Player()
    {
        facingLeft = false;
        textureLeft = new Texture("images/robotleft.png");
        textureRight = new Texture("images/robotright.png");
        collisionBox = new Rectangle(0, 26, textureLeft.getWidth(), textureLeft.getHeight());
    }

    public void moveLeft()
    {
        collisionBox.x -= 3;
        facingLeft = true;
    }

    public void moveRight()
    {
        collisionBox.x += 3;
        facingLeft = false;
    }

    public void setFacingLeft(boolean direction)
    {
        facingLeft = direction;
    }

    public Texture getTexture()
    {
        if(facingLeft)
            return textureLeft;
        return textureRight;
    }

    public float getX()
    {
        return collisionBox.getX();
    }

    public float getY()
    {
        return collisionBox.getY();
    }

    public Rectangle getCollisionBox()
    {
        return collisionBox;
    }
}
