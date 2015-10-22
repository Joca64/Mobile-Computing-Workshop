package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
    float width, height;
    OrthographicCamera camera;
    Player player;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("images/background.png");

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);

        player = new Player();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateInput();


		batch.begin();
		    batch.draw(background, 0, 0);
            batch.draw(player.getTexture(), player.getCollisionBox().getX(), player.getCollisionBox().getY());
		batch.end();

        camera.update();
	}

    @Override
    public void resize(int width, int height)
    {
        camera.setToOrtho(false, width, height);
    }

    public void updateInput()
    {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.getX() > 0)
            player.moveLeft();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.getX() < width - player.getTexture().getWidth())
            player.moveRight();

        if(Gdx.input.isTouched())
        {
            float coordinateX = Gdx.input.getX();

            if(coordinateX < width / 2 && player.getX() > 0)
                player.moveLeft();
            else if(player.getX() < width - player.getTexture().getWidth())
                player.moveRight();
        }
    }
}
