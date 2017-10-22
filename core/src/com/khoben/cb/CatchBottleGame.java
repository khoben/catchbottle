package com.khoben.cb;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.khoben.cb.map.CustomGameMap;
import com.khoben.cb.map.GameMap;
import com.khoben.cb.screens.GameScreen;
import com.khoben.cb.screens.MenuScreen;


public class CatchBottleGame extends Game {

	public SpriteBatch batch;
	public MenuScreen menuScreen;
	public GameScreen gameScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		Gdx.graphics.setTitle("CatchBottle Game");
		this.setScreen(menuScreen);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
	}
}
