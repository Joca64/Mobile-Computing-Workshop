package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;
import java.util.Random;

public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
    private Texture background;
    private float width, height;
    private OrthographicCamera camera;
    private Player player;
    private Array<Number> numbers;
    private int dropDelay, firstNumber, secondNumber;
    private long lastNumberSpawn;
    private Operation operation;
    private String operationDisplay;
    private BitmapFont font;
    private Sound correct, incorrect, pickup;

    @Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("images/background.png");

        correct = Gdx.audio.newSound(Gdx.files.internal("sounds/correct.wav"));
        incorrect = Gdx.audio.newSound(Gdx.files.internal("sounds/incorrect.wav"));
        pickup = Gdx.audio.newSound(Gdx.files.internal("sounds/pickup.wav"));

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);

        player = new Player();
        numbers = new Array<Number>();
        dropDelay = getRandom(1000, 2500);
        lastNumberSpawn = TimeUtils.millis();
        generateOperation();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/kenpixel_high_square.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        parameter.borderColor = Color.BLACK;
        parameter.color = Color.NAVY;
        parameter.borderStraight = true;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    private void generateOperation()
    {
        firstNumber = -1;
        secondNumber = -1;
        operation = new Operation();
        operationDisplay = "_" +operation.getOperator() +"_=" +operation.getResult();
    }

    @Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateInput();
        updateObjects();

		batch.begin();
		    batch.draw(background, 0, 0);
            batch.draw(player.getTexture(), player.getX(), player.getY());
            for(Number number : numbers)
                batch.draw(number.getTexture(), number.getX(), number.getY());
            font.draw(batch, operationDisplay, 10, height - 20);
		batch.end();

        camera.update();
	}

    @Override
    public void resize(int width, int height)
    {
        camera.setToOrtho(false, width, height);
    }

    public void updateObjects()
    {
        if(TimeUtils.millis() - lastNumberSpawn > dropDelay)
        {
            dropDelay = getRandom(1000, 2500);
            generateNumber();
        }

        Iterator<Number> iter = numbers.iterator();
        while(iter.hasNext())
        {
            Number tempNumber = iter.next();
            tempNumber.move();

            if (tempNumber.getCollisionBox().overlaps(player.getCollisionBox()))
            {
                pickup.play();
                if(firstNumber == -1)
                {
                    firstNumber = tempNumber.getValue();
                    operationDisplay = firstNumber + operation.getOperator() + "_=" + operation.getResult();
                }
                else
                {
                    secondNumber = tempNumber.getValue();

                    if(operation.validateResult(firstNumber, secondNumber))
                        correct.play();

                    else
                        incorrect.play();
                    generateOperation();
                }
                System.out.println("Player collided with the number!"); //Stuff happens!
                iter.remove();
                tempNumber.remove();
            }

            if (tempNumber.getY() < 0 - tempNumber.getTexture().getHeight())
            {
                System.out.println("Number has reached the end of the screen!"); //Stuff happens!
                iter.remove();
                tempNumber.remove();
            }
        }
    }

    private void generateNumber()
    {
        numbers.add(new Number(width, height));
        lastNumberSpawn = TimeUtils.millis();
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

    private int getRandom(int min, int max)
    {
        Random rnd = new Random();
        return rnd.nextInt(max - min) + min;
    }
}
