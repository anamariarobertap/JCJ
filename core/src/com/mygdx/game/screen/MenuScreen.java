package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.JumpCookieJump;
import com.mygdx.game.screen.button.MenuButton;

public class MenuScreen extends AbstractScreen {

    private final Texture backgroundTexture;
    private final Music menuMusic;
    private final Sound buttonClick;

    public MenuScreen(final JumpCookieJump game) {
        super(game);

        // adding the background
        backgroundTexture = new Texture("background/MainWallpaper.png");
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(JumpCookieJump.V_WIDTH, JumpCookieJump.V_HEIGHT);
        stage.addActor(backgroundImage);

        // Creating a style for the Title // can be done in future with style.json
//        Label.LabelStyle label1Style = new Label.LabelStyle();
//        label1Style.font = new BitmapFont();
//        label1Style.fontColor = Color.RED;

//        Label title = new Label("Title Screen", label1Style);
//        title.setAlignment(Align.center);
//        title.setY((float) Gdx.graphics.getHeight() * 2 / 3);
//        title.setWidth(Gdx.graphics.getWidth());
//        stage.addActor(title);

        handleButton("Play Game", 0, game.gameScreen);
        handleButton("Settings", 1, game.settingsScreen);
        handleButton("Select Level", 2, game.levelSelectScreen);
        handleButton("Exit Game", 3, null);

        stage.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.ESCAPE)
                    Gdx.app.exit();
                return true;
            }
        });

        // music
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/menu/menu.mp3"));
        menuMusic.play();
        menuMusic.setLooping(true);
        menuMusic.setVolume(0.3f);

        buttonClick = Gdx.audio.newSound(Gdx.files.internal("sounds/menu/click.ogg"));
    }

    protected void stopMusic() {
        menuMusic.pause();
    }

    protected void startMusic() {
        menuMusic.play();
    }

    protected void switchMusic(GameScreen gameScreen) {
        stopMusic();
        gameScreen.startMusic();
    }

    protected void buttonClick() {
        buttonClick.play();
    }

    private void handleButton(String text, int number, final Screen screen) {
        MenuButton button = MenuButton.getMenuButton(text, 75, 300);
        button.setPosition((float) JumpCookieJump.V_WIDTH / 2 - button.getWidth() / 2, (float) JumpCookieJump.V_HEIGHT / 2 - button.getHeight() / 2 * (number*2 + 1) + button.getHeight());

        stage.addActor(button);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClick();
                System.out.println("MenuScreen");
                if(screen != null) {
//                    if (screen instanceof GameScreen) switchMusic((GameScreen) screen);
                    if(screen instanceof GameScreen) {
                        GameScreen gameScreen = new GameScreen(game, 1);
                        game.menuScreen.switchMusic(gameScreen);
                        game.setScreen(gameScreen);
                    }
                    else
                        game.setScreen(screen);
                }
                else {
                    Gdx.app.exit();
                }
            }
        });

//        return button;
    }

    @Override
    public void dispose() {
        super.dispose();
        backgroundTexture.dispose();
        menuMusic.dispose();
        buttonClick.dispose();
    }
}
