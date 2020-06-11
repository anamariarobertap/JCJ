package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.JumpCookieJump;

public abstract class AbstractScreen implements Screen {
    /**
     * Reference to Main-Class
     */
    protected JumpCookieJump game;

    protected OrthographicCamera cam;

    protected Stage stage;
    protected Viewport viewport;

    public AbstractScreen(JumpCookieJump game) {
        this.game = game;

        // create viewport and the stage for the screen
        viewport = new FitViewport(JumpCookieJump.V_WIDTH, JumpCookieJump.V_HEIGHT);
        cam = new OrthographicCamera(JumpCookieJump.V_WIDTH, JumpCookieJump.V_HEIGHT);
        viewport = new FitViewport(JumpCookieJump.V_WIDTH, JumpCookieJump.V_HEIGHT, cam);
        stage = new Stage(viewport, game.batch);
    }

    @Override
    public void show() {
        // set the stage as the input processor
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
//    	viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
