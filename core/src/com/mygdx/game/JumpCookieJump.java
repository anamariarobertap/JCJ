package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.LevelSelectScreen;
import com.mygdx.game.screen.MenuScreen;
import com.mygdx.game.screen.SettingsScreen;

public class JumpCookieJump extends Game {

	public static final int V_WIDTH = 1920;
	public static final int V_HEIGHT = 1080;
//	public static final int V_SCALE = 6;
	public static final float PPM = 100; // pixels per meter
	public static final short COOKIE_BIT =2;
	public static final short COCOA_BIT =4;
	public static final short DEFAUL_BIT =1;
	public static final short DESTROYED_BIT =16;



	/**
	 * Rendering utilities
	 */
	public SpriteBatch batch;

	// Screens
	public GameScreen gameScreen;
	public MenuScreen menuScreen;
	public LevelSelectScreen levelSelectScreen;
	public SettingsScreen settingsScreen;

	// TODO: Save - Class to store our progress in a json-File


	@Override
	public void create () {
		batch = new SpriteBatch();
		gameScreen = new GameScreen(this);
		settingsScreen = new SettingsScreen(this);
		levelSelectScreen = new LevelSelectScreen(this);
		menuScreen = new MenuScreen(this);
		this.setScreen(menuScreen);
	}



	@Override
	public void render () {
/*		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.end();*/
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		gameScreen.dispose();
		menuScreen.dispose();
		settingsScreen.dispose();
		levelSelectScreen.dispose();
	}
}
