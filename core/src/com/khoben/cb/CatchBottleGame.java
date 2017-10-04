package com.khoben.cb;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.khoben.cb.map.CustomGameMap;
import com.khoben.cb.map.GameMap;
import com.khoben.cb.screens.MenuScreen;


public class CatchBottleGame extends Game {

	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MenuScreen(this));
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
